package com.ssafy.review.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.board.model.dto.Board;
import com.ssafy.board.model.dto.BoardListInfoRequest;
import com.ssafy.board.model.dto.BoardListInfoResponse;
import com.ssafy.board.model.service.BoardService;
import com.ssafy.review.model.dto.Review;
import com.ssafy.review.model.dto.ReviewLikeRequest;
import com.ssafy.review.model.dto.ReviewLikeResponse;
import com.ssafy.review.model.dto.ReviewLikeRequest;
import com.ssafy.review.model.service.ReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/review")
@CrossOrigin("*")
@Tag(name = "리뷰 컨트롤러", description = "리뷰 등록,삭제,좋아요 기능을 구현한 클래스")
public class ReviewController {
	private final ReviewService reviewService;

	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@Operation(summary = "리뷰 검색", description = "도시 태그별 리뷰 목록 가져오기")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping("/search/tag/{sidoCode}")
	public ResponseEntity<?> searchReviewByTag(
			@Parameter(required = true, description = "검색할 리뷰 태그") @PathVariable("sidoCode") int sidoCode) {
		try {
			List<Review> response = reviewService.getReviewsByTag(sidoCode);
			if (response != null) {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
				return ResponseEntity.ok().headers(headers).body(response);
			} else {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "베스트 리뷰 검색", description = "좋아요가 가장 많은 리뷰 3개 가져오기")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping("/search/best")
	public ResponseEntity<?> searchBestReviews() {
		try {
			List<Review> response = reviewService.getBestReviews();
			if (response != null) {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
				return ResponseEntity.ok().headers(headers).body(response);
			} else {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "리뷰 인기순 검색", description = "좋아요가 많은 순서대로 리뷰 목록 가져오기")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping("/search/{sidoCode}/likes")
	public ResponseEntity<?> searchReviewsByLikes(
			@Parameter(required = true, description = "검색할 리뷰 태그") @PathVariable("sidoCode") int sidoCode) {
		try {
			List<Review> response = reviewService.getReviewsByLikes(sidoCode);
			if (response != null) {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
				return ResponseEntity.ok().headers(headers).body(response);
			} else {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "리뷰 최신순 검색", description = "생성일 기준 최신 순서대로 리뷰 목록 가져오기")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping("/search/{sidoCode}/recent")
	public ResponseEntity<?> searchRecentReviews(
			@Parameter(required = true, description = "검색할 리뷰 태그") @PathVariable("sidoCode") int sidoCode) {
		try {
			List<Review> response = reviewService.getRecentReviews(sidoCode);
			if (response != null) {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
				return ResponseEntity.ok().headers(headers).body(response);
			} else {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "리뷰 상세 검색", description = "리뷰 id로 리뷰 정보 가져오기")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping("/search/{reviewId}")
	public ResponseEntity<?> searchReviewByReviewId(
			@Parameter(required = true, description = "검색할 리뷰 id") @PathVariable("reviewId") int reviewId,
			@Parameter(required = true, description = "사용자 id") @RequestParam("userId") String userId) {
		try {
			ReviewLikeRequest request = new ReviewLikeRequest(reviewId, userId);
			ReviewLikeResponse response = reviewService.getReviewByReviewId(request);
			if (response != null) {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
				return ResponseEntity.ok().headers(headers).body(response);
			} else {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "리뷰 등록", description = "새로운 리뷰 작성")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> writeReview(
			@Valid @RequestPart("review")  Review review,
            @RequestPart(value ="image", required= false) MultipartFile imageFile) {
		try {
			reviewService.wirteReview(review, imageFile);
			return ResponseEntity.ok("게시물이 등록되었습니다.");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "리뷰 삭제", description = "리뷰 id로 해당 리뷰 삭제")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@DeleteMapping("/{reviewId}")
	public ResponseEntity<?> deleteReview(
			@Parameter(required = true, description = "삭제할 리뷰 id") @PathVariable("reviewId") int reviewId) {
		try {
			reviewService.deleteReview(reviewId);
			return ResponseEntity.ok("게시물이 삭제되었습니다.");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// TODO : 토큰 사용시, userId body로 받는 대신 토큰으로 사용자인증하고, 해당 userId로 처리할 것
	@Operation(summary = "리뷰 좋아요", description = "리뷰 좋아요")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@PostMapping("/like")
	public ResponseEntity<?> likeReview(
			@RequestBody(required = true, description = "좋아요할 리뷰 아이디", content = @Content(schema = @Schema(implementation = ReviewLikeRequest.class))) @org.springframework.web.bind.annotation.RequestBody ReviewLikeRequest reviewLike) {
		try {
			reviewService.likeReview(reviewLike);
			return ResponseEntity.ok("리뷰에 좋아요를 성공했습니다.");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// TODO : 토큰 사용시, userId 쿼리로 받는 대신 토큰으로 사용자인증하고, 해당 userId로 처리할 것
	@Operation(summary = "리뷰 좋아요 취소", description = "해당 리뷰에 한 좋아요를 취소")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@DeleteMapping("/like/{reviewId}")
	public ResponseEntity<?> cancelLikeReview(
			@Parameter(required = true, description = "좋아요 취소할 리뷰 id") @PathVariable("reviewId") int reviewId,
			@Parameter(required = true, description = "사용자 id") @RequestParam("userId") String userId) {
		try {
			ReviewLikeRequest reviewLike = new ReviewLikeRequest(reviewId, userId);
			reviewService.cancelLikeReview(reviewLike);
			return ResponseEntity.ok("리뷰 좋아요 취소를 성공했습니다.");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error : " + e.getMessage());
	}
}
