package com.ssafy.board.model.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.board.model.dto.Board;
import com.ssafy.board.model.dto.BoardListInfoRequest;

@Mapper
public interface BoardDAO {
	void write(Board board) throws SQLException;
	List<Board> searchAll() throws SQLException;
	Board searchByBoardno(int BoardNo) throws SQLException;
	void updateHit(int BoardNo) throws SQLException;
	void modify(Board board) throws SQLException;
	void delete(int BoardNo) throws SQLException;
	void deleteComment(int BoardNo) throws SQLException;
	int getBoardCnt (String keyword) throws SQLException;
	List<Board> searchByKeyword(BoardListInfoRequest boardListInforequest) throws SQLException;
}
