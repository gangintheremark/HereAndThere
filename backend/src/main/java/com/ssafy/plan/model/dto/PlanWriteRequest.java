package com.ssafy.plan.model.dto;

import java.util.List;

import com.ssafy.trip.model.dto.Attraction;

public class PlanWriteRequest {
	private PlanDetail plan;
	private List<PlanPlace> places;
	
	public PlanWriteRequest() {
	}


	public PlanWriteRequest(PlanDetail plan, List<PlanPlace> places) {
		super();
		this.plan = plan;
		this.places = places;
	}


	public PlanDetail getPlan() {
		return plan;
	}
	public void setPlan(PlanDetail plan) {
		this.plan = plan;
	}


	public List<PlanPlace> getPlaces() {
		return places;
	}


	public void setPlaces(List<PlanPlace> places) {
		this.places = places;
	}
	
}
