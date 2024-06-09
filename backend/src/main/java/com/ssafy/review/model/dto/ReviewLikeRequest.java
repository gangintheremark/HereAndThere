package com.ssafy.review.model.dto;

public class ReviewLikeRequest {
	private int reviewId;
	private String userId;

	public ReviewLikeRequest() {
	}

	public ReviewLikeRequest(int reviewId, String userId) {
		super();
		this.reviewId = reviewId;
		this.userId = userId;
	}

	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
