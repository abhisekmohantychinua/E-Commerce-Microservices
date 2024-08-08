package dev.abhisek.reviewservice.service.impl;

import dev.abhisek.reviewservice.dto.ProductResponse;
import dev.abhisek.reviewservice.dto.ReviewRequest;
import dev.abhisek.reviewservice.dto.ReviewResponse;
import dev.abhisek.reviewservice.dto.UserResponse;
import dev.abhisek.reviewservice.entity.Review;
import dev.abhisek.reviewservice.exceptions.models.PermissionException;
import dev.abhisek.reviewservice.exceptions.models.ProductNotFoundException;
import dev.abhisek.reviewservice.exceptions.models.ReviewNotFoundException;
import dev.abhisek.reviewservice.exceptions.models.UserNotFoundException;
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
                .orElseThrow(() -> new UserNotFoundException("No user found on server."));
        ProductResponse product = externalService.getProductByProductId(request.productId())
                .orElseThrow(() -> new ProductNotFoundException("No product found on server with product id: " + request.productId()));

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
                .orElseThrow(() -> new UserNotFoundException("No user found on server."));
        return repository.findAllByUserId(userId)
                .stream().map(r -> {
                    ProductResponse product = externalService.getProductByProductId(r.getProductId())
                            .orElseThrow(() -> new ProductNotFoundException("No product found on server with product id: " + r.getProductId()));
                    return mapper.toResponse(r, product, user);
                })
                .toList();
    }

    @Override
    public List<ReviewResponse> getAllProductReview(String productId) {
        ProductResponse product = externalService.getProductByProductId(productId)
                .orElseThrow(() -> new ProductNotFoundException("No product found on server with product id: " + productId));
        return repository.findAllByProductId(productId)
                .stream().map(r -> {
                    UserResponse user = externalService.getUserByUseId(r.getUserId())
                            .orElseThrow(() -> new UserNotFoundException("No user found on server."));
                    return mapper.toResponse(r, product, user);
                })
                .toList();
    }

    @Override
    public ReviewResponse editReview(ReviewRequest request, String id, String userId) {
        Review review = repository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("No Review found on server with id: " + id));

        if (!review.getUserId().equals(userId)) {
            throw new PermissionException("Can't edit other user's review.");
        }

        if (request.rating() != null) {
            review.setRating(request.rating());
        }

        if (request.feedback() != null) {
            review.setFeedback(request.feedback());
        }

        review = repository.save(review);
        UserResponse user = externalService.getUserByUseId(userId)
                .orElseThrow(() -> new UserNotFoundException("No user found on server."));
        ProductResponse product = externalService.getProductByProductId(request.productId())
                .orElseThrow(() -> new ProductNotFoundException("No product found on server with product id: " + request.productId()));

        return mapper.toResponse(review, product, user);
    }

    @Override
    public ReviewResponse getReviewById(String id, String userId) {
        Review review = repository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("No review found on server with id: " + id));
        UserResponse user = externalService.getUserByUseId(userId)
                .orElseThrow(() -> new UserNotFoundException("No user found on server."));
        ProductResponse product = externalService.getProductByProductId(review.getProductId())
                .orElseThrow(() -> new UserNotFoundException("No user found on server."));
        if (!review.getUserId().equals(userId)) {
            throw new PermissionException("Can't access other user review.");
        }
        return mapper.toResponse(review, product, user);
    }

    @Override
    @Transactional
    public void deleteReviewById(String id, String userId) {
        repository.deleteReviewByIdAndUserId(id, userId);
    }


}
