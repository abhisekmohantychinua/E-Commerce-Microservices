package dev.abhisek.reviewservice.service;

import dev.abhisek.reviewservice.dto.ReviewRequest;
import dev.abhisek.reviewservice.dto.ReviewResponse;

import java.util.List;

public interface ReviewService {
    ReviewResponse createReview(ReviewRequest request, String userId);

    List<ReviewResponse> getAllUserReview(String userId);

    List<ReviewResponse> getAllProductReview(String productId);

    ReviewResponse editReview(ReviewRequest request, String id, String userId);

    ReviewResponse getReviewById(String id, String userId);

    void deleteReviewById(String id, String userId);
}
