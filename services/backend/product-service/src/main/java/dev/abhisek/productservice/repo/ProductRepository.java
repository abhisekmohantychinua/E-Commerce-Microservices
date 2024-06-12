package dev.abhisek.productservice.repo;

import dev.abhisek.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findAllByNotHiddenFalse();
}
