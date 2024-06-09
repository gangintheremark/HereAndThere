package com.ssafy.plan.model.dto;

import java.util.List;

import com.ssafy.trip.model.dto.Attraction;

public class Plan {
	private PlanDetail plan;
	private List<Attraction> places;
	private boolean isLiked;
	
	public Plan() {
	}

	public Plan(PlanDetail plan, List<Attraction> places, boolean isLiked) {
		super();
		this.plan = plan;
		this.places = places;
		this.isLiked = isLiked;
	}

	public PlanDetail getPlan() {
		return plan;
	}

	public void setPlan(PlanDetail plan) {
		this.plan = plan;
	}

	public List<Attraction> getPlaces() {
		return places;
	}

	public void setPlaces(List<Attraction> places) {
		this.places = places;
	}

	public boolean isLiked() {
		return isLiked;
	}

	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}
	
	

	
}
