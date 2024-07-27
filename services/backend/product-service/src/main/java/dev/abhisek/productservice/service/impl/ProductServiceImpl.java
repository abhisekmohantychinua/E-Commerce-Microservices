package dev.abhisek.productservice.service.impl;

import dev.abhisek.productservice.dto.*;
import dev.abhisek.productservice.entity.Category;
import dev.abhisek.productservice.entity.Detail;
import dev.abhisek.productservice.entity.Picture;
import dev.abhisek.productservice.entity.Product;
import dev.abhisek.productservice.mapper.ProductMapper;
import dev.abhisek.productservice.repo.ProductRepository;
import dev.abhisek.productservice.service.ImageService;
import dev.abhisek.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static dev.abhisek.productservice.repo.ProductSpecifications.productInCriteria;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final ImageService imageService;
    private final ProductMapper mapper;

    @Override
    public String addProduct(ProductRequest request) {
        return repository.save(mapper.toProduct(request))
                .getId();
    }

    @Override
    public List<ProductResponse> getProductDetails(List<String> productIds) {
        List<Product> products = repository.findAllById(productIds);
        return products.stream()
                .map(mapper::fromProduct)
                .toList();
    }

    public List<String> addProductImages(MultipartFile[] files, String productId) {
        Product product = findProductByProductId(productId);
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile file : files) {
            fileNames.add(imageService.saveImage(file, productId));
        }
        List<Picture> pictures = fileNames.stream().map(Picture::new).toList();
        product.getPictures().addAll(pictures);
        repository.save(product);
        return fileNames;
    }

    @Override
    public ProductResponse getProductDetails(String id) {
        Product product = findProductByProductId(id);
        return mapper.fromProduct(product);
    }

    private Product findProductByProductId(String productId) {
        return repository.findById(productId)
                .orElseThrow();//todo- exception
    }

    @Override
    public List<String> getProductImages(String id) {
        Product product = findProductByProductId(id);
        List<Picture> pictures = product.getPictures();
        List<String> pictureList = new ArrayList<>();
        for (Picture picture : pictures) {
            byte[] pictureAsByte = imageService.fetchImage(picture.getPath(), product.getId());
            String mimeType = extractMimeType(picture.getPath());
            String url = getImageUrl(pictureAsByte, mimeType);
            pictureList.add(url);
        }
        return pictureList;
    }

    @Override
    public void patchProductQuantity(List<ProductPatchRequest> requests) {
        List<Product> products = repository.findAllById(
                requests.stream()
                        .map(ProductPatchRequest::id)
                        .toList());
        products.forEach(product -> requests.stream()
                .filter(r -> r.id().equals(product.getId()))
                .findFirst()
                .ifPresent(r -> product.setQuantity(product.getQuantity() + r.quantity()))); // r.quantity() follows sign representation. -ve means deduction and +ve means addition

        repository.saveAll(products);
    }

    @Override
    public void updateProduct(String id, ProductUpdateRequest request) {
        Product product = findProductByProductId(id);
        if (request.title() != null) {
            product.setTitle(request.title());
        }
        if (request.description() != null) {
            product.setDescription(request.description());
        }
        if (request.price() != null) {
            product.setPrice(request.price());
        }
        if (request.quantity() != null) {
            product.setQuantity(request.quantity());
        }
        if (request.details() != null) {
            product.getDetails().clear();
            product.getDetails().addAll(
                    request.details().stream()
                            .map(d -> Detail.builder().title(d.title()).body(d.body()).build())
                            .toList());
        }
        if (request.categories() != null) {
            product.getCategories().clear();
            product.getCategories().addAll(
                    request.categories().stream()
                            .map(s -> Category.builder()
                                    .name(s)
                                    .product(Product.builder().id(id).build())
                                    .build())
                            .toList());
        }
        repository.save(product);
    }

    @Override
    public void removeProductImage(String id, String filename) {
        Product product = findProductByProductId(id);
        Optional<Picture> optionalPicture = product.getPictures().stream()
                .filter(p -> Objects.equals(p.getPath(), filename))
                .findFirst();
        optionalPicture.ifPresent(picture -> product.getPictures()
                .remove(picture));

        imageService.removeImage(filename, id);
        repository.save(product);
    }

    @Override
    public void toggleVisibility(String id) {
        Product product = findProductByProductId(id);
        product.setNotHidden(!product.getNotHidden());
        repository.save(product);
    }

    @Override
    public List<ProductResponse> getAllHiddenProducts() {
        return repository.findAllByNotHiddenFalse()
                .stream().map(mapper::fromProduct)
                .toList();
    }

    @Override
    public void deleteProduct(String id) {
        repository.deleteById(id);
    }

    @Override
    public ProductPage searchProduct(ProductCriteria criteria, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 10);
        Specification<Product> specification = productInCriteria(criteria);
        Page<Product> productPage = repository.findAll(specification, pageable);
        return mapper.toProductPage(productPage, pageNo);
    }

    private String extractMimeType(String picture) {
        String extension = picture.split("[.]")[1];
        String mimeType;
        if (extension.equalsIgnoreCase("png"))
            mimeType = MediaType.IMAGE_PNG_VALUE;
        else if (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg"))
            mimeType = MediaType.IMAGE_JPEG_VALUE;
        else
            throw new RuntimeException(); // todo-exception
        return mimeType;
    }

    private String getImageUrl(byte[] picture, String mimeType) {
        final String prefix = "data:";
        final String mime = mimeType + ";";
        final String encodingIndicator = "base64,";
        final String data = Base64.getEncoder().encodeToString(picture);
        final String url = prefix + mime + encodingIndicator + data;
        return url;
    }
}
