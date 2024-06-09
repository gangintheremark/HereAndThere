package com.ssafy.review.model.dto;

public class Review {
	private int reviewId;
	private String userId;
	private String title;
	private String content;
	private int sidoCode;
	private String createAt;
	private String imgUrl;
	private int likes;
	private int starRating;

	public Review() {
	}

	public Review(int reviewId, String userId, String title, String content, int sidoCode, String createAt,
			String imgUrl, int likes, int starRating) {
		super();
		this.reviewId = reviewId;
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.sidoCode = sidoCode;
		this.createAt = createAt;
		this.imgUrl = imgUrl;
		this.likes = likes;
		this.starRating = starRating;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getSidoCode() {
		return sidoCode;
	}

	public void setSidoCode(int sidoCode) {
		this.sidoCode = sidoCode;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getStarRating() {
		return starRating;
	}

	public void setStarRating(int starRating) {
		this.starRating = starRating;
	}
}
