package com.example.work_space_link.Repository;

import com.example.work_space_link.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Review findReviewById(int id);


    @Query("SELECT b.bookingStatus FROM BookingRequest b WHERE b.workspaceId =?1 AND b.userId =?2")
    String getStatusByWorkspaceIdAndUserId(Integer workspaceId, Integer userId);

    @Query("select r from Review r where r.workspaceId=?1 order by r.createdAt desc")
    List<Review> getReviewByWorkspaceId(Integer workspaceId);




}
