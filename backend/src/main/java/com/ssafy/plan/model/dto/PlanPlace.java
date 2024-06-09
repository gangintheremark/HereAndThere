package com.ssafy.plan.model.dto;

public class PlanPlace {
	private int planId;
	private int courseNum;
	private int contentId;

	public PlanPlace() {

	}

	public PlanPlace(int planId, int courseNum, int contentId) {
		super();
		this.planId = planId;
		this.courseNum = courseNum;
		this.contentId = contentId;
	}

	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public int getCourseNum() {
		return courseNum;
	}

	public void setCourseNum(int courseNum) {
		this.courseNum = courseNum;
	}

	public int getContentId() {
		return contentId;
	}

	public void setContentId(int contentId) {
		this.contentId = contentId;
	}

	@Override
	public String toString() {
		return "PlanPlace [planId=" + planId + ", courseNum=" + courseNum + ", contentId=" + contentId + "]";
	}
	
	
	
	

}
