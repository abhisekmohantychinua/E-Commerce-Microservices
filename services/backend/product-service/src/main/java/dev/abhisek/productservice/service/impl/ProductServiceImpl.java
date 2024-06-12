package dev.abhisek.productservice.service.impl;

import dev.abhisek.productservice.dto.ProductRequest;
import dev.abhisek.productservice.dto.ProductResponse;
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
}
