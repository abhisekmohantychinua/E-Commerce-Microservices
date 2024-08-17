package dev.abhisek.orderservice.order.services.impl;

import dev.abhisek.orderservice.exceptions.models.AddressNotFoundException;
import dev.abhisek.orderservice.exceptions.models.OrderNotFoundException;
import dev.abhisek.orderservice.exceptions.models.OutOfStockException;
import dev.abhisek.orderservice.exceptions.models.ProductNotFoundException;
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
import java.util.stream.Collectors;

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
            throw new AddressNotFoundException("Provided address not found on your account, address id: " + request.addressId());
        }
        order.setAddressId(address.id());


        // verify all products and set in order
        List<String> productIds = request.products().stream()
                .map(ProductRequest::productId)
                .collect(Collectors.toList());
        List<ProductResponse> availableProduct = productService.findProductByIdForOrder(productIds);

        if (availableProduct.size() != request.products().size()) {
            StringBuilder messageBuilder = new StringBuilder("Some of requested products are not found on server or out of stock. Those are [ ");
            List<String> availableProductIds = availableProduct.stream()
                    .map(ProductResponse::id)
                    .toList();
            productIds.forEach(id -> {
                if (!availableProductIds.contains(id))
                    messageBuilder.append(id).append(", ");
            });
            messageBuilder.append("]");
            throw new ProductNotFoundException(messageBuilder.toString());
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

                // If requested quantity is more than products quantity thr
                if (productRequest.quantity() > product.quantity()) {
                    throw new OutOfStockException("Product " + product.id() + " is out of stock for quantity " + productRequest.quantity() + ". Available quantity: " + product.quantity());
                }

                // Update total amount
                totalAmount += productRequest.quantity() * product.price();

                ProductPatchRequest patchRequest = new ProductPatchRequest(
                        product.id(),
                        -productRequest.quantity()
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
        PaymentRequest paymentRequest = new PaymentRequest(totalAmount, request.paymentMethod(), order.getId(), userId);
        PaymentResponse payment = paymentService.createPayment(paymentRequest);

        order.setPaymentId(payment.id());

        // save and map to order response then return
        order = repository.save(order);

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
                .orElseThrow(() -> new OrderNotFoundException("Requested order not found on server with id: " + id));

        if (!order.getUserId().equals(userId)) {
            throw new OrderNotFoundException("Requested order not found on server with id: " + id);
        }

        if (order.getStatus() == Status.DELIVERED) {
            throw new UnsupportedOperationException("Cannot cancel an order if it is DELIVERED.");
        }

        if (order.getPaymentStatus() == COMPLETED) {
            order.setPaymentStatus(REFUND);
            throw new UnsupportedOperationException("Cannot cancel an order if the payment is completed. Payment status is set for refund.");
        } else if (order.getPaymentStatus() == PENDING) {
            paymentService.deletePayment(order.getPaymentId());
            repository.delete(order);
        }
    }

    @Override
    public void completeOrder(String id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Requested order not found on server with id: " + id));
        order.setPaymentStatus(COMPLETED);
        repository.save(order);
    }

    @Override
    public OrderResponse getOrderById(String id, String userId) {
        Order order = repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new OrderNotFoundException("Requested product not found on server with id: " + id));
        AddressResponse address = userService.getAddressById(order.getAddressId(), userId);
        if (address == null) {
            throw new AddressNotFoundException("Provided address not found on your account, address id: " + order.getAddressId());
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

    @Override// todo- To be fixed
    public OrderResponse updateOrder(OrderRequest request, String id, String userId) {
        // extract order from db
        Order order = repository.findByIdAndUserId(id, userId)
                .orElseThrow();// todo - exception

        // If address present in request then verify the address or keep existing address
        AddressResponse address;
        if (request.addressId() != null) {
            address = userService.getAddressById(request.addressId(), userId);
            if (address == null) {
                throw new RuntimeException(); // todo - exception
            }
            order.setAddressId(address.id());
        } else {
            address = userService.getAddressById(order.getAddressId(), order.getUserId());
        }

        // if products are there inside request then update the order line else keep existing
        List<OrderLine> orderLines = order.getOrderLines();
        List<OrderLine> orderLinesToBeRemoved = new ArrayList<>();
        if (request.products() != null) {
            List<ProductPatchRequest> patchRequests = new ArrayList<>();// for update the product quantity from product-service

            // iterate and update order lines which are in new products
            int i;
            for (i = 0; i < orderLines.size(); i++) {

                OrderLine orderLine = orderLines.get(i);
                Optional<ProductRequest> optionalProduct = request.products().stream()
                        .filter(p -> p.productId().equals(orderLine.getProductId()))
                        .findFirst();

                if (optionalProduct.isPresent()) { // products to be updated, i.e. products both in existing order list and request list
                    ProductRequest product = optionalProduct.get();
                    ProductPatchRequest patchRequest = new ProductPatchRequest(product.productId(), orderLine.getQuantity() - product.quantity());
                    orderLine.setQuantity(product.quantity());
                    patchRequests.add(patchRequest);
                    request.products().remove(product); // remove updated product from request list
                } else { // products to be removed, i.e. products only in existing order not in request list
                    ProductPatchRequest patchRequest = new ProductPatchRequest(orderLine.getProductId(), orderLine.getQuantity());
                    patchRequests.add(patchRequest);
                    orderLinesToBeRemoved.add(orderLine);
                }
            }
            orderLines.removeAll(orderLinesToBeRemoved);
            orderLines.addAll(
                    request.products().stream() // new products to be added, i.e. products are not in existing order but in request list
                            .map(p -> {
                                ProductPatchRequest patchRequest = new ProductPatchRequest(p.productId(), -p.quantity());
                                patchRequests.add(patchRequest);
                                return OrderLine.builder()  // new order line for each product to be added
                                        .productId(p.productId())
                                        .quantity(p.quantity())
                                        .order(order)
                                        .build();
                            }).toList()
            );
            productService.patchProductQuantity(patchRequests);// send patch request to product service
        }

        // get all product ids and fetch products and exception for invalid products
        List<String> productIds = orderLines.stream()
                .map(OrderLine::getProductId)
                .toList();
        List<ProductResponse> products = productService.findProductByIdForOrder(productIds);
        if (products.size() != productIds.size()) {
            throw new RuntimeException();// todo - exception
        }

        // generate order line response from order lines
        List<OrderLineResponse> orderLineResponses = new ArrayList<>();
        double totalAmount = 0; // not required when no change done with products, but not so effective
        for (OrderLine ol : orderLines) {
            Optional<ProductResponse> optionalProduct = products.stream().filter(p -> p.id().equals(ol.getProductId())).findFirst();
            if (optionalProduct.isEmpty()) {
                throw new RuntimeException();// todo - exception
            }
            ProductResponse product = optionalProduct.get();
            totalAmount += product.price() * ol.getQuantity();
            OrderLineResponse orderLineResponse = new OrderLineResponse(product, ol.getQuantity());
            orderLineResponses.add(orderLineResponse);
        }

        // update payment if there is any change in products
        PaymentResponse payment;
        if (request.products() != null) {
            order.setTotalAmount(totalAmount);
            payment = paymentService.updatePaymentAmount(order.getPaymentId(), order.getTotalAmount());
        } else {
            payment = paymentService.getPaymentById(order.getPaymentId(), order.getUserId());
        }
        repository.save(order);
        return mapper.fromOrder(order, address, payment, orderLineResponses);
    }

}
