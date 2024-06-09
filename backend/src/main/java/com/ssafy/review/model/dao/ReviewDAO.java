package com.ssafy.review.model.dao;

import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ssafy.review.model.dto.Review;
import com.ssafy.review.model.dto.ReviewLikeRequest;

@Mapper
public interface ReviewDAO {
	List<Review> searchByTag(int sidoCode) throws SQLException;

	List<Review> getBestReviews() throws SQLException;

	List<Review> getReviewsByLikes(int sidoCode) throws SQLException;

	List<Review> getRecentReviews(int sidoCode) throws SQLException;

	Review searchByReviewId(int reviewId) throws SQLException;
	
	boolean checkReviewLike(ReviewLikeRequest reviewLike) throws SQLException;

	void writeReview(Review review) throws SQLException;

	void deleteReview(int reviewId) throws SQLException;

	void likeReview(ReviewLikeRequest reviewLike) throws SQLException;

	void cancelLikeReview(ReviewLikeRequest reviewLike) throws SQLException;
}
