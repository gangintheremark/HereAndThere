package com.ssafy.comment.model.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.comment.model.dto.Comment;

@Mapper
public interface CommentDAO {
	List<Comment> searchByBoardNo(int BoardNo) throws SQLException;
	void write(Comment comment) throws SQLException;
	void delete(int commentNo) throws SQLException;
}
