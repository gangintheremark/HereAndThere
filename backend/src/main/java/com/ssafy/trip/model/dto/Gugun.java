package com.ssafy.trip.model.dto;

public class Gugun {
	private int code;
	private String name;
	
	public Gugun() {

	}
	public Gugun(int code, String name) {
		this.code = code;
		this.name = name;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "SidoDto [code=" + code + ", name=" + name + "]";
	}
	
	
}
