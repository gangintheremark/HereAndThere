package com.ssafy.board.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.board.model.dto.Board;
import com.ssafy.board.model.dto.BoardListInfoRequest;
import com.ssafy.board.model.dto.BoardListInfoResponse;

public interface BoardService {
	void write(Board board) throws SQLException;
	List<Board> searchAll() throws SQLException;
	Board searchByBoardno(int BoardNo) throws SQLException;
	void updateHit(int BoardNo) throws SQLException;
	void modify(Board board) throws SQLException;
	void delete(int BoardNo) throws SQLException;
	void deleteComment(int BoardNo) throws SQLException;
	BoardListInfoResponse searchByKeyword(BoardListInfoRequest request) throws SQLException;
}
