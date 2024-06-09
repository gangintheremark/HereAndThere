package com.ssafy.board.model.dto;

import java.util.List;

// 게시물 페이지네이션을 위한 response dto
public class BoardListInfoResponse {
	private List<Board> boardList;
	private int allPageCnt;

	public BoardListInfoResponse(List<Board> boardList, int allPageCnt) {
		this.boardList = boardList;
		this.allPageCnt = allPageCnt;
	}

	public List<Board> getBoardList() {
		return boardList;
	}

	public void setBoardList(List<Board> boardList) {
		this.boardList = boardList;
	}

	public int getAllPageCnt() {
		return allPageCnt;
	}

	public void setAllPageCnt(int allPageCnt) {
		this.allPageCnt = allPageCnt;
	}
	
}
