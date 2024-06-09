package com.ssafy.comment.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.comment.model.dao.CommentDAO;
import com.ssafy.comment.model.dto.Comment;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDAO commentDao;
	
	@Override
	public List<Comment> searchByBoardNo(int BoardNo) throws SQLException {
		return commentDao.searchByBoardNo(BoardNo);
	}

	@Override
	public void write(Comment comment) throws SQLException {
		commentDao.write(comment);
	}

	@Override
	public void delete(int commentNo) throws SQLException {
		commentDao.delete(commentNo);
	}

}
