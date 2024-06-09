package com.ssafy.review.model.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.board.model.dao.BoardDAO;
import com.ssafy.board.model.dto.Board;
import com.ssafy.board.model.dto.BoardListInfoRequest;
import com.ssafy.board.model.dto.BoardListInfoResponse;
import com.ssafy.review.model.dao.ReviewDAO;
import com.ssafy.review.model.dto.Review;
import com.ssafy.review.model.dto.ReviewLikeRequest;
import com.ssafy.review.model.dto.ReviewLikeResponse;
import com.ssafy.s3.ProductAwsS3Service;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewDAO reviewDao;

	@Autowired
	private ProductAwsS3Service s3Service;

	@Override
	public List<Review> getReviewsByTag(int sidoCode) throws SQLException {
		return reviewDao.searchByTag(sidoCode);
	}

	@Override
	public List<Review> getBestReviews() throws SQLException {

		return reviewDao.getBestReviews();
	}

	@Override
	public List<Review> getReviewsByLikes(int sidoCode) throws SQLException {
		return reviewDao.getReviewsByLikes(sidoCode);
	}

	@Override
	public List<Review> getRecentReviews(int sidoCode) throws SQLException {
		return reviewDao.getRecentReviews(sidoCode);
	}

	@Override
	public ReviewLikeResponse getReviewByReviewId(ReviewLikeRequest reviewLike) throws SQLException {
		Review review = reviewDao.searchByReviewId(reviewLike.getReviewId());
		boolean isLiked = reviewDao.checkReviewLike(reviewLike);
		ReviewLikeResponse response = new ReviewLikeResponse(review, isLiked);
		return response;
	}

	@Override
	public void wirteReview(Review review, MultipartFile uploadFile) throws SQLException, IOException {
		if (uploadFile != null) {
			String imgUrl = s3Service.fileUpload(null, uploadFile);
			review.setImgUrl(imgUrl);
		}
		reviewDao.writeReview(review);
	}

	@Override
	public void deleteReview(int reviewId) throws SQLException {
		reviewDao.deleteReview(reviewId);

	}

	@Override
	public void likeReview(ReviewLikeRequest reviewLike) throws SQLException {
		reviewDao.likeReview(reviewLike);
	}

	@Override
	public void cancelLikeReview(ReviewLikeRequest reviewLike) throws SQLException {
		reviewDao.cancelLikeReview(reviewLike);
	}

}
