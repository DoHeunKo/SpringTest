package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dtos.MemberDto;

@Mapper
public interface MemberMapper {
	//추가
	public boolean addUser(MemberDto dto);
	//로그인
	public MemberDto loginUser(String id);
	//아이디 체크
	public String idChk(String id);
	
}
