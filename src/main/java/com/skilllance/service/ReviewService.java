package com.skilllance.service;

import com.skilllance.entity.Review;
import com.skilllance.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review createReview(Review review) {
        validateRating(review.getRating());

        return reviewRepository.save(review);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));
    }

    public List<Review> getReviewsByReviewer(Long reviewerId) {
        return reviewRepository.findByReviewerId(reviewerId);
    }

    public List<Review> getReviewsByReviewee(Long revieweeId) {
        return reviewRepository.findByRevieweeId(revieweeId);
    }

    public List<Review> getReviewsByProject(Long projectId) {
        return reviewRepository.findByProjectId(projectId);
    }

    public List<Review> getReviewsByContract(Long contractId) {
        return reviewRepository.findByContractId(contractId);
    }

    public List<Review> getReviewsByRating(Integer rating) {
        validateRating(rating);

        return reviewRepository.findByRating(rating);
    }

    public Review updateReview(Long id, Review updatedReview) {
        validateRating(updatedReview.getRating());

        Review existingReview = getReviewById(id);

        existingReview.setReviewerId(updatedReview.getReviewerId());
        existingReview.setRevieweeId(updatedReview.getRevieweeId());
        existingReview.setProjectId(updatedReview.getProjectId());
        existingReview.setContractId(updatedReview.getContractId());
        existingReview.setRating(updatedReview.getRating());
        existingReview.setComment(updatedReview.getComment());

        return reviewRepository.save(existingReview);
    }

    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new IllegalArgumentException("Review not found");
        }

        reviewRepository.deleteById(id);
    }

    private void validateRating(Integer rating) {
        if (rating == null || rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
    }
}
