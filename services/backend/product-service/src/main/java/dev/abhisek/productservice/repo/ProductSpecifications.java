package dev.abhisek.productservice.repo;

import dev.abhisek.productservice.dto.DetailDto;
import dev.abhisek.productservice.dto.ProductCriteria;
import dev.abhisek.productservice.entity.Product;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;


public class ProductSpecifications {
    public static Specification<Product> productInCriteria(ProductCriteria request) {
        return (root, query, criteriaBuilder) -> {

            // Quantity
            Predicate quantityPredicate;
            if (request.showOutOfStock() != null && request.showOutOfStock()) {
                quantityPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("quantity"), 0);
            } else {
                quantityPredicate = criteriaBuilder.greaterThan(root.get("quantity"), 0);
            }


            // Price
            Predicate pricePredicate;
            if (request.maxPrice() != null) {
                if (request.minPrice() != null) {
                    pricePredicate = criteriaBuilder.between(root.get("price"), request.minPrice(), request.maxPrice());
                } else {
                    pricePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("price"), request.maxPrice());
                }
            } else if (request.minPrice() != null) {
                pricePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("price"), request.minPrice());
            } else {
                pricePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("price"), 0);
            }


            // Title
            Predicate titlePredicate = criteriaBuilder.like(root.get("title"), format("%%%s%%", request.query()));

            // Category
            Predicate categoryPredicate = criteriaBuilder.conjunction();
            if (request.categories() != null && !request.categories().isEmpty()) {
                Join<Object, Object> categoryJoin = root.join("categories");
                categoryPredicate = categoryJoin.get("name").in(request.categories());
            }

            // Details
            Predicate detailPredicate = criteriaBuilder.conjunction();
            if (request.details() != null && !request.details().isEmpty()) {
                Join<Object, Object> detailJoin = root.join("details");
                List<Predicate> detailPredicates = new ArrayList<>();
                for (DetailDto dto : request.details()) {
                    Predicate detailTitlePredicate = criteriaBuilder.equal(detailJoin.get("title"), dto.title());
                    Predicate detailBodyPredicate = criteriaBuilder.equal(detailJoin.get("body"), dto.body());
                    detailPredicates.add(criteriaBuilder.and(detailTitlePredicate, detailBodyPredicate));
                }
                detailPredicate = criteriaBuilder.or(detailPredicates.toArray(new Predicate[0]));
            }

            return criteriaBuilder.and(quantityPredicate, pricePredicate, titlePredicate, categoryPredicate, detailPredicate);
        };
    }
}
