package com.ssafy.review.model.dto;

public class ReviewLikeResponse {
	private Review review;
	private boolean isLiked;
	
	public ReviewLikeResponse() {}
	
	public ReviewLikeResponse(Review review, boolean isLiked) {
		super();
		this.review = review;
		this.isLiked = isLiked;
	}
	public Review getReview() {
		return review;
	}
	public void setReview(Review review) {
		this.review = review;
	}
	public boolean isLiked() {
		return isLiked;
	}
	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}
	
	

}
