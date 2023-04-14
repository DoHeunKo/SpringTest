package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.command.AddUserCommand;
import com.example.demo.command.LoginCommand;
import com.example.demo.dtos.MemberDto;
import com.example.demo.mapper.MemberMapper;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class MemberService {
	
	@Autowired
	private	MemberMapper memberMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public boolean addUser(AddUserCommand addUserCommand) {
		
		MemberDto mdto=new MemberDto();
		mdto.setId(addUserCommand.getId());
		mdto.setName(addUserCommand.getName());
		//
		mdto.setPassword(passwordEncoder.encode(addUserCommand.getPassword()));
		mdto.setEmail(addUserCommand.getEmail());
		mdto.setAddress(addUserCommand.getAddress());
		
		return memberMapper.addUser(mdto);
	}
	public String idChk(String id) {
		
		return memberMapper.idChk(id);
	}
	
	
	public String loginUser(LoginCommand loginCommand,
							HttpServletRequest request) {
		System.out.println("로그인");
		String path="thymeleaf/home";
		MemberDto mdto=memberMapper.loginUser(loginCommand.getId());
		if(mdto !=null) {//존재시
			//(앞,뒤)앞: 화면으로부터 입력 ,뒤: DB
			if(passwordEncoder.matches(loginCommand.getPassword(), mdto.getPassword())) {
				System.out.println("패스워드 비교: 일치");
				request.getSession().setAttribute("mdto", mdto);
				return path;
			}else {
				System.out.println("패스워드 틀림");
				path="thymeleaf/member/login";//로그인페이지 이동
			}
		}else {
			System.out.println("회원이 아님");
			path="thymeleaf/member/login";
		}
		return path;
	}
	
}
