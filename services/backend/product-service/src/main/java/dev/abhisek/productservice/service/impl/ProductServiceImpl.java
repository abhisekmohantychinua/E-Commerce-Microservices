package dev.abhisek.productservice.service.impl;

import dev.abhisek.productservice.dto.ProductRequest;
import dev.abhisek.productservice.dto.ProductResponse;
import dev.abhisek.productservice.dto.ProductUpdateRequest;
import dev.abhisek.productservice.entity.Category;
import dev.abhisek.productservice.entity.Detail;
import dev.abhisek.productservice.entity.Picture;
import dev.abhisek.productservice.entity.Product;
import dev.abhisek.productservice.mapper.ProductMapper;
import dev.abhisek.productservice.repo.CategoryRepository;
import dev.abhisek.productservice.repo.ProductRepository;
import dev.abhisek.productservice.service.ImageService;
import dev.abhisek.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final ImageService imageService;
    private final ProductMapper mapper;

    @Override
    public String addProduct(ProductRequest request) {
        return repository.save(mapper.toProduct(request))
                .getId();
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
    public List<byte[]> getProductImages(String id) {
        Product product = findProductByProductId(id);
        List<Picture> pictures = product.getPictures();
        List<byte[]> pictureList = new ArrayList<>();
        for (Picture picture : pictures) {
            pictureList.add(imageService.fetchImage(picture.getPath(), product.getId()));
        }
        return pictureList;
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
}
