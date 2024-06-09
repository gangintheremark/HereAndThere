package com.ssafy.board.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.board.model.dto.Board;
import com.ssafy.board.model.dto.BoardListInfoRequest;
import com.ssafy.board.model.dto.BoardListInfoResponse;
import com.ssafy.board.model.service.BoardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/board")
@CrossOrigin("*")
@Tag(name = "보드 컨트롤러", description = "게시글 조회/등록/수정/삭제하는 클래스")
public class BoardController {
	private final BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@Operation(summary = "게시판 목록", description = "게시판 전체 목록 가져오기")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
			})
	@GetMapping("/list")
    public ResponseEntity<?> searchAll() {

        try {
            List<Board> list = boardService.searchAll();
            if(list!=null && !list.isEmpty()) {
            	HttpHeaders headers = new HttpHeaders();
            	headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
				return ResponseEntity.ok().headers(headers).body(list);
            } else {
            	return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
        	return exceptionHandling(e);
        }
    }
	
	@Operation(summary = "게시글 작성", description = "새로운 게시글을 작성")
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK"),
	        @ApiResponse(responseCode = "400", description = "Bad Request"),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error")
	        })
	@PostMapping("/")
	public ResponseEntity<?> write(@RequestBody(description = "작성할 게시글 정보", required = true, content = @Content(schema = @Schema(implementation = Board.class))) @org.springframework.web.bind.annotation.RequestBody Board board) {

	    try {
	    	boardService.write(board);
	        return ResponseEntity.ok("게시물이 작성되었습니다.");
	       
	    } catch (Exception e) {
	        return exceptionHandling(e);
	    }
	}
	
	@Operation(summary = "게시글 상세 조회", description = "게시글 번호를 받아 해당 게시글을 가져오기")
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK"),
	        @ApiResponse(responseCode = "404", description = "Page Not Found"),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error")
	        })
	@GetMapping("/{boardNo}")
	public ResponseEntity<?> view(	@Parameter(required = true, description = "검색할 게시물 번호") @PathVariable("boardNo") int boardNo) {
		
	    
	    try {
	        Board board = boardService.searchByBoardno(boardNo);
	        boardService.updateHit(boardNo);
	        if(board != null) {
	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
	            return ResponseEntity.ok().headers(headers).body(board);
	        } else {
	            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	        }
	    } catch (Exception e) {
	        return exceptionHandling(e);
	    }
	}
		
	@Operation(summary = "게시글 수정", description = "게시글 수정")
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK"),
	        @ApiResponse(responseCode = "400", description = "Bad Request"),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error")
	        })
	@PutMapping("/{boardNo}")
	public ResponseEntity<?> modify(@RequestBody(description = "수정할 게시글", required = true, content = @Content(schema = @Schema(implementation = Board.class))) @org.springframework.web.bind.annotation.RequestBody Board board, @PathVariable("boardNo") int boardNo) {
	    try {
	    	board.setBoardNo(boardNo);
	    	boardService.modify(board);
	        Board uploadedBoard = boardService.searchByBoardno(board.getBoardNo());
	        if(uploadedBoard != null) {
	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
	            return ResponseEntity.ok().headers(headers).body(uploadedBoard);
	        } else {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    } catch (Exception e) {
	        return exceptionHandling(e);
	    }
	}

	@Operation(summary = "게시글 삭제", description = "게시글 삭제하기")
	@ApiResponses(value = { 
	        @ApiResponse(responseCode = "200", description = "OK"),
	        @ApiResponse(responseCode = "400", description = "Bad Request"),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error")
	        })
	@DeleteMapping("/{boardNo}")
    public ResponseEntity<?> delete(@Parameter(required = true, description = "삭제 게시물 번호") @PathVariable("boardNo") int boardNo) {
        try {
        	boardService.deleteComment(boardNo);
            boardService.delete(boardNo);
            return ResponseEntity.ok("게시물이 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시물 삭제에 실패했습니다.");
        }
    }
	
	@Operation(summary = "게시글 검색", description = "검색한 게시글 목록 가져오기")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "404", description = "Page Not Found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
			})
	@GetMapping("/list/search")
    public ResponseEntity<?> searchByKeyword(
    		@Parameter(required = false, description = "검색할 게시글 키워드") @RequestParam(name = "keyword", defaultValue = "") String keyword,
    		@Parameter(required = true, description = "검색할 게시글의 개수") @RequestParam("pageCnt") int pageCnt,
    		@Parameter(required = true, description = "검색할 게시글의 페이지 번호") @RequestParam("pageNum") int pageNum) {

        try {
        	BoardListInfoRequest request = new BoardListInfoRequest(keyword, pageNum, pageCnt);
            BoardListInfoResponse response = boardService.searchByKeyword(request);
            if(response!=null && response.getBoardList() != null) {
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
	
    private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error : " + e.getMessage());
	}
}
