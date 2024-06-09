package com.ssafy.comment.model.dto;

public class Comment {
	private int boardNo;
	private int commentNo;
	private String userId;
	private String userName;
	private String profileImg;
	private String content;
	private String createAt;
	public Comment() {
	}

	public Comment(int boardNo, int commentNo, String userId, String userName, String profileImg, String content,
			String createAt) {
		super();
		this.boardNo = boardNo;
		this.commentNo = commentNo;
		this.userId = userId;
		this.userName = userName;
		this.profileImg = profileImg;
		this.content = content;
		this.createAt = createAt;
	}

	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public int getCommentNo() {
		return commentNo;
	}
	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateAt() {
		return createAt;
	}
	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public String getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}
	
	                             
}                                    
