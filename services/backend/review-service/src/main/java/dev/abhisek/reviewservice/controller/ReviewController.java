package dev.abhisek.reviewservice.controller;

import dev.abhisek.reviewservice.dto.ReviewRequest;
import dev.abhisek.reviewservice.dto.ReviewResponse;
import dev.abhisek.reviewservice.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService service;

    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(
            @RequestBody @Valid ReviewRequest request,
            @RequestHeader(name = "user_id") String userId
    ) {
        return ResponseEntity.ok(service.createReview(request, userId));
    }

    @GetMapping("{id}")
    public ResponseEntity<ReviewResponse> getReview(@PathVariable String id, @RequestHeader(name = "user_id") String userId) {
        return ResponseEntity.ok(service.getReviewById(id, userId));
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getAllUserReview(@RequestHeader(name = "user_id") String userId) {
        return ResponseEntity.ok(service.getAllUserReview(userId));
    }

    @GetMapping("products/{productId}")
    public ResponseEntity<List<ReviewResponse>> getAllProductReview(@PathVariable String productId) {
        return ResponseEntity.ok(service.getAllProductReview(productId));
    }

    @PatchMapping("{id}")
    public ResponseEntity<ReviewResponse> editReview(
            @RequestBody ReviewRequest request,
            @PathVariable String id,
            @RequestHeader(name = "user_id") String userId
    ) {
        return ResponseEntity.ok(service.editReview(request, id, userId));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable String id, @RequestHeader(name = "user_id") String userId) {
        service.deleteReviewById(id, userId);
        return ResponseEntity.noContent().build();
    }
}
