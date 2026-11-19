package com.skilllance.controller;

import com.skilllance.entity.Review;
import com.skilllance.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reviewService.createReview(review));
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(reviewService.getReviewById(id));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/reviewer/{reviewerId}")
    public ResponseEntity<List<Review>> getReviewsByReviewer(
            @PathVariable Long reviewerId) {
        return ResponseEntity.ok(
                reviewService.getReviewsByReviewer(reviewerId)
        );
    }

    @GetMapping("/reviewee/{revieweeId}")
    public ResponseEntity<List<Review>> getReviewsByReviewee(
            @PathVariable Long revieweeId) {
        return ResponseEntity.ok(
                reviewService.getReviewsByReviewee(revieweeId)
        );
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Review>> getReviewsByProject(
            @PathVariable Long projectId) {
        return ResponseEntity.ok(
                reviewService.getReviewsByProject(projectId)
        );
    }

    @GetMapping("/contract/{contractId}")
    public ResponseEntity<List<Review>> getReviewsByContract(
            @PathVariable Long contractId) {
        return ResponseEntity.ok(
                reviewService.getReviewsByContract(contractId)
        );
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<List<Review>> getReviewsByRating(
            @PathVariable Integer rating) {
        return ResponseEntity.ok(
                reviewService.getReviewsByRating(rating)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(
            @PathVariable Long id,
            @RequestBody Review review) {

        try {
            return ResponseEntity.ok(
                    reviewService.updateReview(id, review)
            );
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        try {
            reviewService.deleteReview(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
