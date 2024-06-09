package com.ssafy.review.model.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.board.model.dto.Board;
import com.ssafy.board.model.dto.BoardListInfoRequest;
import com.ssafy.board.model.dto.BoardListInfoResponse;
import com.ssafy.review.model.dto.Review;
import com.ssafy.review.model.dto.ReviewLikeRequest;
import com.ssafy.review.model.dto.ReviewLikeResponse;

public interface ReviewService {
	List<Review> getReviewsByTag(int sidoCode) throws SQLException;
	
	List<Review> getBestReviews() throws SQLException;
	
	List<Review> getReviewsByLikes(int sidoCode) throws SQLException;
	
	List<Review> getRecentReviews(int sidoCode) throws SQLException;
	
	ReviewLikeResponse getReviewByReviewId(ReviewLikeRequest reviewLike) throws SQLException;

	void wirteReview(Review review, MultipartFile uploadFile) throws SQLException, IOException;

	void deleteReview(int reviewId) throws SQLException;

	void likeReview(ReviewLikeRequest reviewLike) throws SQLException;

	void cancelLikeReview(ReviewLikeRequest reviewLike) throws SQLException;
}
