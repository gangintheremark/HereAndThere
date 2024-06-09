package com.ssafy.trip.model.dto;

public class Sido {
	private int sido_code;
	private String sido_name;
	
	public Sido() {

	}

	public Sido(int sido_code, String sido_name) {
		super();
		this.sido_code = sido_code;
		this.sido_name = sido_name;
	}

	public int getSido_code() {
		return sido_code;
	}

	public void setSido_code(int sido_code) {
		this.sido_code = sido_code;
	}

	public String getSido_name() {
		return sido_name;
	}

	public void setSido_name(String sido_name) {
		this.sido_name = sido_name;
	}
	
}
