package com.ssafy.plan.model.dto;

public class PlanDetail {
	private int planId;
	private String userId;
	private String title;
	private String distance;
	private int sidoCode;
	private String createAt;
	private int likes;
	public PlanDetail() {
	}
	public PlanDetail(int planId, String userId, String title, String distance, int sidoCode, String createAt,
			int likes) {
		this.planId = planId;
		this.userId = userId;
		this.title = title;
		this.distance = distance;
		this.sidoCode = sidoCode;
		this.createAt = createAt;
		this.likes = likes;
	}
	public int getPlanId() {
		return planId;
	}
	public void setPlanId(int planId) {
		this.planId = planId;
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
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
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
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	@Override
	public String toString() {
		return "PlanDetail [planId=" + planId + ", userId=" + userId + ", title=" + title + ", distance=" + distance
				+ ", sidoCode=" + sidoCode + ", createAt=" + createAt + ", likes=" + likes + "]";
	}
	
	
	
}
