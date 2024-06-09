package com.ssafy.comment.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.comment.model.dto.Comment;

public interface CommentService {
	List<Comment> searchByBoardNo(int BoardNo) throws SQLException;
	void write(Comment comment) throws SQLException;
	void delete(int commentNo) throws SQLException;
}
