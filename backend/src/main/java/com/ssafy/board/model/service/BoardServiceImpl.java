package com.ssafy.board.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.board.model.dao.BoardDAO;
import com.ssafy.board.model.dto.Board;
import com.ssafy.board.model.dto.BoardListInfoRequest;
import com.ssafy.board.model.dto.BoardListInfoResponse;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDAO boardDao;
	
	@Override
	public void write(Board board) throws SQLException {
		boardDao.write(board);
	}

	@Override
	public List<Board> searchAll() throws SQLException {
		return boardDao.searchAll();
	}

	@Override
	public Board searchByBoardno(int BoardNo) throws SQLException {
		return boardDao.searchByBoardno(BoardNo);
	}

	@Override
	public void updateHit(int BoardNo) throws SQLException {
		boardDao.updateHit(BoardNo);
	}

	@Override
	public void modify(Board board) throws SQLException {
		boardDao.modify(board);
	}

	@Override
	public void delete(int BoardNo) throws SQLException {
		boardDao.delete(BoardNo);
	}

	@Override
	public void deleteComment(int BoardNo) throws SQLException {
		boardDao.deleteComment(BoardNo);
	}
	
	@Override
	public BoardListInfoResponse searchByKeyword(BoardListInfoRequest request) throws SQLException {
		int allPageCnt = boardDao.getBoardCnt(request.getKeyword());
		request.setPageNum((request.getPageNum()-1)*request.getPageCnt());
		List<Board> boardList = boardDao.searchByKeyword(request);
		
		return new BoardListInfoResponse(boardList, allPageCnt);
	}

}
