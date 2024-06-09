package com.ssafy.member.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.member.model.dto.Login;
import com.ssafy.member.model.dto.Member;

public interface MemberService {
	void join(Member memberDto) throws Exception;
	Member login(Login login) throws Exception;
	Member modify(Member memberDto, MultipartFile imageFile)throws Exception;
	void delete(String userid) throws Exception;
	Member searchByUserId(String userId) throws Exception;
}
