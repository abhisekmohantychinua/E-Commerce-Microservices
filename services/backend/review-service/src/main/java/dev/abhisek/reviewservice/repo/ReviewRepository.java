package dev.abhisek.reviewservice.repo;

import dev.abhisek.reviewservice.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, String> {
    List<Review> findAllByUserId(String userId);

    List<Review> findAllByProductId(String productId);

    void deleteReviewByIdAndUserId(String id, String userId);
}
