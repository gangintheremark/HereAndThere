package com.ssafy.member.controller;

import java.nio.charset.StandardCharsets;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.member.model.dto.Login;
import com.ssafy.member.model.dto.Member;
import com.ssafy.member.model.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
@Tag(name = "멤버 컨트롤러", description = "회원 가입, 로그인, 로그아운, 회원수정, 회원삭제, 회원조회하는 클래스")

public class MemberController {
	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	// POST 회원가입
	@Operation(summary = "회원 등록", description = "회원의 정보를 받아 데이터베이스에 등록.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@PostMapping(value = "/")
	public ResponseEntity<?> userRegister(
			@RequestBody(description = "등록할 회원정보.", required = true, content = @Content(schema = @Schema(implementation = Member.class))) @org.springframework.web.bind.annotation.RequestBody Member memberDto) {
		try {
			memberService.join(memberDto);
			Member dto = memberService.searchByUserId(memberDto.getUserId());
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body(dto);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// POST 로그인

	@Operation(summary = "로그인", description = "로그인.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@PostMapping(value = "/login")
	public ResponseEntity<?> login(
			@RequestBody(description = "로그인할 회원정보.", required = true, content = @Content(schema = @Schema(implementation = Login.class))) @org.springframework.web.bind.annotation.RequestBody Login loginDto,
			HttpSession session) {
		try {
			Member memberDto = memberService.login(loginDto);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body(memberDto);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// GET 로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	// GET 회원조회
	@Operation(summary = "회원정보", description = "입력한 회원 아이디를 이용하여 회원에 대한 정보를 조회.")
	@Parameters({
			@Parameter(name = "userid", description = "회원아이디", required = true, in = ParameterIn.PATH, example = "hissam") })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping(value = "/{userid}")
	public ResponseEntity<?> userInfo(
			@Parameter(required = true, description = "검색할 사용자의 아이디") @PathVariable("userid") String userId) {
		try {
			Member memberDto = memberService.searchByUserId(userId);
			if (memberDto != null) {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
				return ResponseEntity.ok().headers(headers).body(memberDto);
			} else
				return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// POST 회원수정
	@Operation(summary = "회원정보 수정", description = "회원정보를 수정.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@PutMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> userModify(
			@Valid @RequestPart(value = "member", required = true) Member memberDto,
			@RequestPart(value = "image", required = false) MultipartFile imageFile) {
		try {
			Member dto = memberService.modify(memberDto, imageFile);
			if(dto != null) {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
				return ResponseEntity.ok().headers(headers).body(dto);
			} else
				return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// DELETE 회원삭제
	@Operation(summary = "회원정보 삭제", description = "회원정보를 삭제.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@DeleteMapping(value = "/{userid}")
	public ResponseEntity<?> userDelete(
			@Parameter(required = true, description = "삭제할 사용자의 아이디") @PathVariable("userid") String userId) {
		try {
			memberService.delete(userId);

			String msg = "삭제되었습니다!";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body(msg);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
//		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error : " + e.getMessage());
	}

}
