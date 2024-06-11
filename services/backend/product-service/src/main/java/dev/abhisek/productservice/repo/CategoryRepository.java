package dev.abhisek.productservice.repo;

import dev.abhisek.productservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByNameIn(List<String> names);
}
