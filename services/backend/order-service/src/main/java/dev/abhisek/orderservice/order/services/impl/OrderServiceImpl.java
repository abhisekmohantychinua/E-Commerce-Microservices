package dev.abhisek.orderservice.order.services.impl;

import dev.abhisek.orderservice.order.dto.*;
import dev.abhisek.orderservice.order.entity.Order;
import dev.abhisek.orderservice.order.entity.OrderLine;
import dev.abhisek.orderservice.order.entity.Status;
import dev.abhisek.orderservice.order.mapper.OrderMapper;
import dev.abhisek.orderservice.order.repo.OrderRepository;
import dev.abhisek.orderservice.order.services.OrderService;
import dev.abhisek.orderservice.order.services.external.PaymentService;
import dev.abhisek.orderservice.order.services.external.ProductService;
import dev.abhisek.orderservice.order.services.external.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static dev.abhisek.orderservice.order.entity.PaymentStatus.*;
import static dev.abhisek.orderservice.order.entity.Status.YET_TO_DELIVER;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final ProductService productService;
    private final UserService userService;
    private final PaymentService paymentService;
    private final OrderMapper mapper;

    @Override
    public OrderResponse createOrder(OrderRequest request, String userId) {

        // create an order
        Order order = Order.builder()
                .id(UUID.randomUUID().toString())
                .status(YET_TO_DELIVER)
                .paymentStatus(PENDING)
                .userId(userId)
                .build();
        List<OrderLine> orderLines = new ArrayList<>();

        // verify user address and set in order
        AddressResponse address = userService.getAddressById(request.addressId(), userId);
        if (address == null) {
            throw new RuntimeException(); // todo - exception
        }
        order.setAddressId(address.id());


        // verify all products and set in order
        List<ProductResponse> availableProduct = productService.findProductByIdForOrder(
                request.products().stream()
                        .map(ProductRequest::productId)
                        .toList());

        if (availableProduct.size() != request.products().size()) {
            throw new RuntimeException(); // todo - exception
        }


        List<ProductPatchRequest> patchRequests = new ArrayList<>(); // For updating products in product service
        List<OrderLineResponse> orderLineResponses = new ArrayList<>(); // For mapping order response
        double totalAmount = 0.0;

        // Visit all the product
        for (ProductResponse product : availableProduct) {
            // Find the respective request
            Optional<ProductRequest> requestOptional = request.products().stream()
                    .filter(p -> p.productId().equals(product.id()))
                    .findFirst();
            if (requestOptional.isPresent()) {
                ProductRequest productRequest = requestOptional.get();

                // If requested quantity is more that products quantity thr
                if (productRequest.quantity() > product.quantity()) {
                    throw new RuntimeException();// todo -exception
                }

                // Update total amount
                totalAmount += productRequest.quantity() * product.price();

                ProductPatchRequest patchRequest = new ProductPatchRequest(
                        product.id(),
                        product.quantity() - productRequest.quantity()
                );
                patchRequests.add(patchRequest);

                OrderLine orderLine = OrderLine.builder()
                        .productId(product.id())
                        .quantity(productRequest.quantity())
                        .order(order)
                        .build();
                orderLines.add(orderLine);

                OrderLineResponse orderLineResponse = new OrderLineResponse(product, productRequest.quantity());
                orderLineResponses.add(orderLineResponse);
            }
        }

        productService.patchProductQuantity(patchRequests);

        order.setOrderLines(orderLines);
        order.setTotalAmount(totalAmount);

        // create payment and set in order
        PaymentRequest paymentRequest = new PaymentRequest(totalAmount, request.paymentMethod(), userId);
        PaymentResponse payment = paymentService.createPayment(paymentRequest);

        order.setPaymentId(payment.id());

        // save and map to order response then return
        repository.save(order);

        return mapper.fromOrder(order, address, payment, orderLineResponses);
    }

    @Override
    public List<OrderResponse> getAllUserOrder(String userId) {
        List<Order> orders = repository.findAllByUserId(userId);
        List<String> allProductIds = orders.stream()
                .flatMap(order -> order.getOrderLines().stream())
                .map(OrderLine::getProductId)
                .toList();

        List<AddressResponse> allUserAddress = userService.getAllUserAddress(userId);
        List<ProductResponse> allProducts = productService.findProductByIdForOrder(allProductIds);
        List<PaymentResponse> allPayments = paymentService.getPaymentByUserId(userId);

        return mapper.fromOrders(orders, allUserAddress, allPayments, allProducts);
    }

    @Override
    public void cancelOrder(String id, String userId) {
        Order order = repository.findById(id)
                .orElseThrow();//todo - exception

        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException(); // todo - exception
        }

        if (order.getStatus() == Status.DELIVERED) {
            throw new RuntimeException(); // todo - exception
        }

        if (order.getPaymentStatus() == COMPLETED) {
            order.setPaymentStatus(REFUND);
            throw new RuntimeException(); // todo - exception
        } else if (order.getPaymentStatus() == PENDING) {
            paymentService.deletePayment(order.getPaymentId());
            repository.delete(order);
        }
    }

    @Override
    public void completeOrder(String id) {
        Order order = repository.findById(id)
                .orElseThrow();// todo - exception
        order.setPaymentStatus(COMPLETED);
        repository.save(order);
    }

    @Override
    public OrderResponse getOrderById(String id, String userId) {
        Order order = repository.findByIdAndUserId(id, userId)
                .orElseThrow();// todo - exception
        AddressResponse address = userService.getAddressById(order.getAddressId(), userId);
        if (address == null) {
            throw new RuntimeException(); // todo - exception
        }
        order.setAddressId(address.id());

        PaymentResponse payment = paymentService.getPaymentById(order.getPaymentId(), userId);
        List<OrderLineResponse> orderLines = new ArrayList<>();
        List<String> productIds = order.getOrderLines()
                .stream().map(OrderLine::getProductId)
                .collect(Collectors.toList());
        List<ProductResponse> products = productService.findProductByIdForOrder(productIds);

        for (OrderLine orderLine : order.getOrderLines()) {
            ProductResponse product = products
                    .stream().filter(p -> p.id().equals(orderLine.getProductId())
                    ).findFirst().get();
            OrderLineResponse response = new OrderLineResponse(product, orderLine.getQuantity());
            orderLines.add(response);
        }
        return mapper.fromOrder(order, address, payment, orderLines);
    }

//    @Override
//    public OrderResponse updateOrder(OrderRequest request, String id, String userId) {
//        Order order = repository.findByIdAndUserId(id, userId)
//                .orElseThrow();// todo - exception
//
//        AddressResponse address = userService.getAddressById(order.getAddressId(), userId);
//        if (address == null) {
//            throw new RuntimeException(); // todo - exception
//        }
//        order.setAddressId(address.id());
//
//        List<OrderLine> orderLines = order.getOrderLines();
//        List<ProductPatchRequest> patchRequests = new ArrayList<>();
//        // iterate and update order lines which are in new products
//        int i;
//        for (i = 0; i < orderLines.size(); i++) {
//            OrderLine orderLine = orderLines.get(i);
//            Optional<ProductRequest> optionalProduct = request.products().stream()
//                    .filter(p -> p.productId().equals(orderLine.getProductId()))
//                    .findFirst();
//            if (optionalProduct.isPresent()) {
//                ProductRequest product = optionalProduct.get();
//                orderLine.setQuantity(product.quantity());
//            }
//        }
//
//        // iterate new products add those products to order line if not there
//
//        return null;
//    }

}
