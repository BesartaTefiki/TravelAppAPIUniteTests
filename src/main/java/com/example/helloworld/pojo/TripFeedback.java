package com.example.helloworld.pojo;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class TripFeedback {
    @Id
    private String id;
    private Long tripId;
    private Long userId;
    private int rating;
    private String comment;
    private Date reviewDate;

    public TripFeedback(String id, Long tripId, Long userId, int rating, String comment, Date reviewDate) {
        this.id = id;
        this.tripId = tripId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public TripFeedback() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }


    @Override
    public String toString() {
        return "TripFeedback{" +
                "id='" + id + '\'' +
                ", tripId=" + tripId +
                ", userId=" + userId +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", reviewDate=" + reviewDate +
                '}';
    }
}
