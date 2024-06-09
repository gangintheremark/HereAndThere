package com.ssafy.member.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.member.model.dao.MemberDAO;
import com.ssafy.member.model.dto.Login;
import com.ssafy.member.model.dto.Member;
import com.ssafy.s3.ProductAwsS3Service;

@Service
public class MemberServiceImpl implements MemberService {
	
	private MemberDAO memberDao;
	
	@Autowired
	private ProductAwsS3Service s3Service;
	
	private MemberServiceImpl(MemberDAO memberDao) {
		this.memberDao = memberDao;
	}


	@Override
	public void join(Member memberDto) throws Exception {
		memberDao.join(memberDto);
		
	}

	@Override
	public Member login(Login login) throws Exception {
		return memberDao.login(login);
	}

	@Override
	public Member modify(Member memberDto, MultipartFile imageFile) throws Exception {
		if (imageFile != null) {
			String imgUrl = s3Service.fileUpload(null, imageFile);
			memberDto.setProfileImg(imgUrl);
		}
		System.out.println(memberDto.getProfileImg());
		memberDao.modify(memberDto);
		return searchByUserId(memberDto.getUserId());
	}

	@Override
	public void delete(String userid) throws Exception {
		memberDao.delete(userid);
		
	}
	
	@Override
	public Member searchByUserId(String userId) throws Exception {
		return memberDao.searchByUserId(userId);
	}

}
