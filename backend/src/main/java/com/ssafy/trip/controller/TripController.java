package com.ssafy.trip.controller;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.member.model.dto.Login;
import com.ssafy.trip.model.dto.Attraction;
import com.ssafy.trip.model.dto.Gugun;
import com.ssafy.trip.model.dto.Search;
import com.ssafy.trip.model.dto.Sido;
import com.ssafy.trip.model.service.TripService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/trip")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@Tag(name = "트립 컨트롤러", description = "관광지 검색, 시도검색, 구군검색하는 클래스")
public class TripController {
	
	@Autowired
	private TripService tripService;
		
	// 관광지 검색
	@Operation(summary = "관광지 검색", description = "시도, 군구, 컨텐츠 정보를 받아 데이터베이스에 등록된 정보 가져오기")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@PostMapping("/search")
	public ResponseEntity<?> search(@RequestBody(description = "검색할 관광지정보.", required = true, content = @Content(schema = @Schema(implementation = Search.class))) @org.springframework.web.bind.annotation.RequestBody Search info) {
		
		try {
			List<Attraction> attractions = tripService.getAttListByParams(info);
			if(attractions.isEmpty()) {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			} else {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
				return ResponseEntity.ok().headers(headers).body(attractions);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@Operation(summary = "시/도 검색", description = "데이터베이스에 등록 시/도 정보 가져오기")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping("/sido")
	public ResponseEntity<?> getSido() {
		try {
			List<Sido> sidoList = tripService.getSido();
			if(sidoList.isEmpty()) {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			} else {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
				return ResponseEntity.ok().headers(headers).body(sidoList);
			}
		} catch (SQLException e) {
			return exceptionHandling(e);
		}
	}
	
	@Operation(summary = "구/군 검색", description = "시/도 코드 정보를 받아 데이터베이스에 등록된 구/군 정보 가져오기")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping("/gugun")
	public ResponseEntity<?> getGugun(@RequestParam int sido_code) {
		try {
			List<Gugun> gugunList = tripService.getGugun(sido_code);
			if(gugunList.isEmpty()) {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			} else {
				return ResponseEntity.ok().body(gugunList);
			}
		} catch (SQLException e) {
			return exceptionHandling(e);
		}
	}
	
	@Operation(summary = "컨텐츠 검색", description = "데이터베이스에 등록된 컨텐츠 가져오기")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping("/content")
	public ResponseEntity<?> getContent() {
		try {
			List<com.ssafy.trip.model.dto.Content> contentList = tripService.getContent();
			if(contentList.isEmpty()) {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			} else {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
				return ResponseEntity.ok().headers(headers).body(contentList);
			}
		} catch (SQLException e) {
			return exceptionHandling(e);
		}
	}
	
	@Operation(summary = "컨텐츠 이름 검색", description = "컨텐츠 코드 정보를 받아 컨텐츠명 가져오기")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping("/search/content")
	public ResponseEntity<?> getContentName(@RequestParam int contentTypeId) {
		try {
			String contentName = tripService.getContentName(contentTypeId);
			if(contentName.length() == 0) {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			} else {
				return ResponseEntity.ok().body(contentName);
			}
		} catch (SQLException e) {
			return exceptionHandling(e);
		}
	}
	
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
