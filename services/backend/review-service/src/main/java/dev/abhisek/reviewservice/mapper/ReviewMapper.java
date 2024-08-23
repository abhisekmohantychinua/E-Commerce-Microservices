package dev.abhisek.reviewservice.mapper;

import dev.abhisek.reviewservice.dto.ProductResponse;
import dev.abhisek.reviewservice.dto.ReviewResponse;
import dev.abhisek.reviewservice.dto.UserResponse;
import dev.abhisek.reviewservice.entity.Review;
import org.springframework.stereotype.Service;

@Service
public class ReviewMapper {

    public ReviewResponse toResponse(Review review, ProductResponse product, UserResponse user) {
        return new ReviewResponse(
                review.getId(),
                review.getRating(),
                review.getFeedback(),
                product,
                user,
                review.getCreatedAt().toString()
        );
    }
}
