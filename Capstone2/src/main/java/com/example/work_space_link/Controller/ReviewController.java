package com.example.work_space_link.Controller;

import com.example.work_space_link.ApiResponse.ApiResponse;
import com.example.work_space_link.Model.Review;
import com.example.work_space_link.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/work-spase-link/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllReview() {
        List<Review> reviewList = reviewService.getAllReview();
        return ResponseEntity.status(200).body(reviewList);
    }

    //endpoint
    @PostMapping("/add")
    public ResponseEntity<?> addReview(@RequestBody Review review) {
        reviewService.addReview(review);
        return ResponseEntity.status(200).body(new ApiResponse("Your review added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Integer id, @RequestBody Review review) {
        reviewService.updateReview(id, review);
        return ResponseEntity.status(200).body(new ApiResponse("Your review updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Integer id) {
        reviewService.deleteReview(id);
        return ResponseEntity.status(200).body(new ApiResponse("your review deleted successfully"));
    }

    //endpoint
    @GetMapping("/workspace-review/workspace-id/{workspaceid}")
    public ResponseEntity<?> getReviewByWorkspaceId(@PathVariable Integer workspaceid) {
        List<Review> reviews=reviewService.getReviewForWorkspace(workspaceid);
        return ResponseEntity.status(200).body(reviews);
    }
}
