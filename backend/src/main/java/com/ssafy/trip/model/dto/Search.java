package com.ssafy.trip.model.dto;

public class Search {
	int sido;
	int gugun;
	int type;
	String keyword;
	
	public Search() {
	}

	public Search(int sido, int gugun, int type, String keyword) {
		this.sido = sido;
		this.gugun = gugun;
		this.type = type;
		this.keyword = keyword;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getSido() {
		return sido;
	}

	public void setSido(int sido) {
		this.sido = sido;
	}

	public int getGugun() {
		return gugun;
	}

	public void setGugun(int gugun) {
		this.gugun = gugun;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Search [sido=" + sido + ", gugun=" + gugun + ", type=" + type + "]";
	}
	
	

}
