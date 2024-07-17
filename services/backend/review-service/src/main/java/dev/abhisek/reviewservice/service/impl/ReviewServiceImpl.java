package dev.abhisek.reviewservice.service.impl;

import dev.abhisek.reviewservice.dto.ProductResponse;
import dev.abhisek.reviewservice.dto.ReviewRequest;
import dev.abhisek.reviewservice.dto.ReviewResponse;
import dev.abhisek.reviewservice.dto.UserResponse;
import dev.abhisek.reviewservice.entity.Review;
import dev.abhisek.reviewservice.mapper.ReviewMapper;
import dev.abhisek.reviewservice.repo.ReviewRepository;
import dev.abhisek.reviewservice.service.ReviewService;
import dev.abhisek.reviewservice.service.external.ExternalService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository repository;
    private final ExternalService externalService;
    private final ReviewMapper mapper;

    @Override
    public ReviewResponse createReview(ReviewRequest request, String userId) {
        UserResponse user = externalService.getUserByUseId(userId)
                .orElseThrow();// todo - exception
        ProductResponse product = externalService.getProductByProductId(request.productId())
                .orElseThrow();// todo - exception

        Review review = Review.builder()
                .id(UUID.randomUUID().toString())
                .rating(request.rating())
                .feedback(request.feedback())
                .productId(product.id())
                .userId(userId)
                .build();
        review = repository.save(review);
        return mapper.toResponse(review, product, user);
    }

    @Override
    public List<ReviewResponse> getAllUserReview(String userId) {
        UserResponse user = externalService.getUserByUseId(userId)
                .orElseThrow();// todo - exception
        return repository.findAllByUserId(userId)
                .stream().map(r -> {
                    ProductResponse product = externalService.getProductByProductId(r.getProductId())
                            .orElseThrow();// todo - exception
                    return mapper.toResponse(r, product, user);
                })
                .toList();
    }

    @Override
    public List<ReviewResponse> getAllProductReview(String productId) {
        ProductResponse product = externalService.getProductByProductId(productId)
                .orElseThrow();// todo - exception
        return repository.findAllByProductId(productId)
                .stream().map(r -> {
                    UserResponse user = externalService.getUserByUseId(r.getUserId())
                            .orElseThrow();// todo - exception
                    return mapper.toResponse(r, product, user);
                })
                .toList();
    }

    @Override
    public ReviewResponse editReview(ReviewRequest request, String id, String userId) {
        Review review = repository.findById(id)
                .orElseThrow();// todo- exception

        if (review.getUserId().equals(userId)) {
            throw new RuntimeException();// todo-exception
        }

        if (request.rating() != null) {
            review.setRating(request.rating());
        }

        if (request.feedback() != null) {
            review.setFeedback(request.feedback());
        }

        review = repository.save(review);
        UserResponse user = externalService.getUserByUseId(userId)
                .orElseThrow();// todo - exception
        ProductResponse product = externalService.getProductByProductId(request.productId())
                .orElseThrow();// todo - exception

        return mapper.toResponse(review, product, user);
    }

    @Override
    public ReviewResponse getReviewById(String id, String userId) {
        Review review = repository.findById(id)
                .orElseThrow();// todo- exception
        UserResponse user = externalService.getUserByUseId(userId)
                .orElseThrow();// todo - exception
        ProductResponse product = externalService.getProductByProductId(review.getProductId())
                .orElseThrow();// todo - exception
        if (!review.getUserId().equals(userId)) {
            throw new RuntimeException();// todo - exception
        }
        return mapper.toResponse(review, product, user);
    }

    @Override
    @Transactional
    public void deleteReviewById(String id, String userId) {
        repository.deleteReviewByIdAndUserId(id, userId);
    }


}
