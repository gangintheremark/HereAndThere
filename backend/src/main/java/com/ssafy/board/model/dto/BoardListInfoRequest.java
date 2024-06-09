package com.ssafy.board.model.dto;

public class BoardListInfoRequest {
	private String keyword;
	private int pageNum;
	private int pageCnt;
	
	public BoardListInfoRequest() {
	}
	
	public BoardListInfoRequest(String keyword, int pageNum, int pageCnt) {
		this.keyword = keyword;
		this.pageNum = pageNum;
		this.pageCnt = pageCnt;
	}
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageCnt() {
		return pageCnt;
	}
	public void setPageCnt(int pageCnt) {
		this.pageCnt = pageCnt;
	}

}
