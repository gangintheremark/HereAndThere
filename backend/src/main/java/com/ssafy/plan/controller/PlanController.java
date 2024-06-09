package com.ssafy.plan.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.plan.model.dto.Plan;
import com.ssafy.plan.model.dto.PlanDetail;
import com.ssafy.plan.model.dto.PlanLikeRequest;
import com.ssafy.plan.model.dto.PlanPlace;
import com.ssafy.plan.model.dto.PlanWriteRequest;
import com.ssafy.plan.model.service.PlanService;
import com.ssafy.review.model.dto.ReviewLikeRequest;
import com.ssafy.trip.model.dto.Attraction;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/plan")
@CrossOrigin("*")
@Tag(name = "여행코스 컨트롤러", description = "여행코스 등록,조회,삭제,좋아요 기능을 구현한 클래스")
public class PlanController {
	private final PlanService planService;

	public PlanController(PlanService planService) {
		this.planService = planService;
	}

	@Operation(summary = "여행코스 목록 검색", description = "도시 태그별 여행코스 목록 가져오기")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping("/search/tag/{sidoCode}")
	public ResponseEntity<?> searchPlanByTag(
			@Parameter(required = true, description = "검색할 목록 태그") @PathVariable("sidoCode") int sidoCode) {
		try {
			List<PlanDetail> response = planService.searchByTag(sidoCode);
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

	@Operation(summary = "베스트 여행코스 검색", description = "좋아요가 가장 많은 여행코스 3개 가져오기")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping("/search/best")
	public ResponseEntity<?> searchBestPlans() {
		try {
			List<PlanDetail> response = planService.getBestPlans();
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

	@Operation(summary = "여행코스 인기순 검색", description = "좋아요가 많은 순서대로 여행코스 목록 가져오기")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping("/search/{sidoCode}/likes")
	public ResponseEntity<?> searchPlansByLikes(
			@Parameter(required = true, description = "검색할 지역 태그") @PathVariable("sidoCode") int sidoCode) {
		try {
			List<PlanDetail> response = planService.getPlansByLikes(sidoCode);
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

	@Operation(summary = "여행코스 최신순 검색", description = "생성일 기준 최신 순서대로 여행코스 목록 가져오기")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping("/search/{sidoCode}/recent")
	public ResponseEntity<?> searchRecentPlans(
			@Parameter(required = true, description = "검색할 지역 태그") @PathVariable("sidoCode") int sidoCode) {
		try {
			List<PlanDetail> response = planService.getRecentPlans(sidoCode);
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

	@Operation(summary = "여행코스 상세 검색", description = "여행코스 id로 여행코스 정보 가져오기")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping("/search/{planId}")
	public ResponseEntity<?> searchPlanByPlanId(
			@Parameter(required = true, description = "검색할 여행코스 id") @PathVariable("planId") int planId,
			@Parameter(required = true, description = "사용자 id") @RequestParam("userId") String userId) {
		try {

			PlanDetail planDetail = planService.searchByPlanId(planId);
			List<Attraction> attractions = planService.searchCourseByPlanId(planId);
			PlanLikeRequest request = new PlanLikeRequest(planId, userId);
			boolean isLiked = planService.checkPlanLike(request);
			Plan response = new Plan(planDetail, attractions, isLiked);

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

	@Operation(summary = "여행코스 등록", description = "새로운 여행코스 작성")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@PostMapping(value = "/")
	public ResponseEntity<?> writePlan(
			@RequestBody(description = "작성할 여행코스 정보", required = true, content = @Content(schema = @Schema(implementation = PlanWriteRequest.class))) @org.springframework.web.bind.annotation.RequestBody PlanWriteRequest request) {
		try {
			planService.writePlan(request.getPlan());
			int planId = planService.getPlanId();
			List<PlanPlace> places = request.getPlaces();
			for (int i = 0; i < places.size(); i++) {
				places.get(i).setPlanId(planId);
				places.get(i).setCourseNum(i + 1);
				System.out.println(places.get(i));
				planService.writePlanCourse(places.get(i));
			}
			return ResponseEntity.ok("여행코스가 등록되었습니다.");
		} catch (Exception e) {
			return exceptionHandling(e);
		}

	}
	
	@Operation(summary = "여행코스 삭제", description = "플랜 id로 해당 플랜 삭제")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@DeleteMapping("/{planId}")
	public ResponseEntity<?> deletePlan(
			@Parameter(required = true, description = "삭제할 플랜 id") @PathVariable("planId") int planId) {
		try {
			planService.deletePlan(planId);
			return ResponseEntity.ok("여행코스가 삭제되었습니다.");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// TODO : 토큰 사용시, userId body로 받는 대신 토큰으로 사용자인증하고, 해당 userId로 처리할 것
	@Operation(summary = "여행코스 좋아요", description = "여행코스 좋아요")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@PostMapping("/like")
	public ResponseEntity<?> likePlan(
			@RequestBody(required = true, description = "좋아요할 플랜 아이디", content = @Content(schema = @Schema(implementation = PlanLikeRequest.class))) @org.springframework.web.bind.annotation.RequestBody PlanLikeRequest planLike) {
		try {
			planService.likePlan(planLike);
			return ResponseEntity.ok("여행코스 좋아요를 성공했습니다.");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// TODO : 토큰 사용시, userId 쿼리로 받는 대신 토큰으로 사용자인증하고, 해당 userId로 처리할 것
	@Operation(summary = "여행코스 좋아요 취소", description = "해당 여행코스에 한 좋아요를 취소")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@DeleteMapping("/like/{planId}")
	public ResponseEntity<?> cancelLikePlan(
			@Parameter(required = true, description = "좋아요 취소할 플랜 id") @PathVariable("planId") int planId,
			@Parameter(required = true, description = "사용자 id") @RequestParam("userId") String userId) {
		try {
			PlanLikeRequest planLike = new PlanLikeRequest(planId, userId);
			planService.cancelLikePlan(planLike);
			return ResponseEntity.ok("여행코스 좋아요 취소를 성공했습니다.");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error : " + e.getMessage());
	}
}
