package com.ssafy.comment.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.board.model.dto.Board;
import com.ssafy.board.model.service.BoardService;
import com.ssafy.comment.model.dto.Comment;
import com.ssafy.comment.model.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/comment")
@CrossOrigin("*")
@Tag(name = "댓글 컨트롤러", description = "댓글 조회/삭제하는 클래스")
public class CommentController {
	private final CommentService commentService;
	
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@Operation(summary = "댓글 목록", description = "댓글 목록 가져오기")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
			})
	@GetMapping("/{boardNo}")
	public ResponseEntity<?> view(@Parameter(required = true, description = "검색할 게시물 번호") @PathVariable("boardNo") int boardNo) {
	    try {
	        List<Comment> comments = commentService.searchByBoardNo(boardNo);
	        if(comments != null) {
	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
	            return ResponseEntity.ok().headers(headers).body(comments);
	        } else {
	            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	        }
	    } catch (Exception e) {
	        return exceptionHandling(e);
	    }
	}
	
	@Operation(summary = "댓글 작성", description = "새로운 뎃글을 작성")
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK"),
	        @ApiResponse(responseCode = "400", description = "Bad Request"),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error")
	        })
	@PostMapping("/")
	public ResponseEntity<?> write(@RequestBody(description = "작성할 댓글 정보", required = true, content = @Content(schema = @Schema(implementation = Comment.class))) @org.springframework.web.bind.annotation.RequestBody Comment comment) {

	    try {
	    	commentService.write(comment);
	        return ResponseEntity.ok("댓글이 작성되었습니다.");
	       
	    } catch (Exception e) {
	        return exceptionHandling(e);
	    }
	}
	
	@Operation(summary = "댓글 삭제", description = "댓글 삭제하기")
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK"),
	        @ApiResponse(responseCode = "400", description = "Bad Request"),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error")
	        })
	@DeleteMapping("/{commentNo}")
    public ResponseEntity<?> delete(@Parameter(required = true, description = "삭제 댓글 번호") @PathVariable("commentNo") int commentNo) {
        try {
            commentService.delete(commentNo);
            return ResponseEntity.ok("댓글이 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시물 삭제에 실패했습니다.");
        }
    }
	
    private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error : " + e.getMessage());
	}
}
