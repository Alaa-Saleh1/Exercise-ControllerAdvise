package com.example.work_space_link.Service;

import com.example.work_space_link.ApiResponse.ApiException;
import com.example.work_space_link.Model.Review;
import com.example.work_space_link.Repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;


    public List<Review> getAllReview() {
        return reviewRepository.findAll();
    }

    public void addReview(Review review) {
        String isCompleted = reviewRepository.getStatusByWorkspaceIdAndUserId(review.getWorkspaceId(), review.getUserId());
        if (!isCompleted.equals("Completed")) {
            throw new ApiException("*User has not booking this workspace or the booking is not completed");
        }
        review.setCreatedAt(LocalDateTime.now());

        reviewRepository.save(review);
    }

    public void updateReview(Integer feedbackId, Review review) {
        Review oldReview = reviewRepository.findReviewById(feedbackId);
        if (oldReview == null) {
            throw new ApiException("Review not found");
        }
        oldReview.setRating(review.getRating());
        oldReview.setComment(review.getComment());
        reviewRepository.save(oldReview);
    }

    public void deleteReview(Integer feedbackId) {
        reviewRepository.deleteById(feedbackId);
    }

    public List<Review> getReviewForWorkspace(Integer workspaceId) {
        List<Review> reviews = reviewRepository.getReviewByWorkspaceId(workspaceId);
        return reviews;
    }
}
