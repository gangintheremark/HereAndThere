package com.ssafy.plan.model.dto;

public class PlanLikeRequest {
	private int planId;
	private String userId;

	public PlanLikeRequest() {
	}

	public PlanLikeRequest(int planId, String userId) {
		super();
		this.planId = planId;
		this.userId = userId;
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

}
