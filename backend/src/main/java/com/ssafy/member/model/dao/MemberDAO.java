package com.ssafy.member.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.member.model.dto.Login;
import com.ssafy.member.model.dto.Member;

@Mapper
public interface MemberDAO {
	void join(Member memberDto) throws Exception;
	Member login(Login login) throws Exception;
	void modify(Member memberDto)throws Exception;
	void delete(String userid) throws Exception;
	Member searchByUserId(String userId) throws Exception;
}
