package com.ssafy.trip.model.dto;

public class Content {
	private int contentTypeId;
	private String contentType;
	
	
	public Content() {
	}
	
	public Content(int contentTypeId, String contentType) {
		this.contentTypeId = contentTypeId;
		this.contentType = contentType;
	}

	public int getContentTypeId() {
		return contentTypeId;
	}
	public void setContentTypeId(int contentTypeId) {
		this.contentTypeId = contentTypeId;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	
}
