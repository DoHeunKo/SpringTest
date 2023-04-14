package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.command.AddUserCommand;
import com.example.demo.command.LoginCommand;
import com.example.demo.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value="/user")
public class MemberController {
		
	@Autowired
	private MemberService MemberService;
	
	@GetMapping(value="/addUserForm")
	public String addUserForm(Model model) {
		System.out.println("회원가입 폼이동");
		model.addAttribute("addUserCommand",new AddUserCommand());
		return "thymeleaf/member/addUserForm";
	}
	
	@PostMapping(value="/addUser")
	public String addUser(@Validated AddUserCommand command,
			BindingResult result,Model model) {
		
		if(result.hasErrors()) {
			return "thymeleaf/member/addUserForm";
		}
		
		try {
			boolean isS=MemberService.addUser(command);
			System.out.println("회원가입성공");
			return "redirect:/home";
		} catch (Exception e) {
			System.out.println("회원가입실패");
			e.printStackTrace();
			return "redirect:addUserForm";
		}
		
	}
	
	@GetMapping("/idChk")
	@ResponseBody
	public Map<String,String> idChk(Model model,String id){
		System.out.println("id중복체크");
		String resultId=MemberService.idChk(id);
		
		Map<String,String> map=new HashMap<>();
		map.put("id", resultId);
		
		return map;
	}
	
	@GetMapping("/loginForm")
	public String loginForm(Model model) {
		model.addAttribute("loginCommand",new LoginCommand());
		return "thymeleaf/member/login";
	}
	
	@PostMapping("/login")
	public String login(@Validated LoginCommand loginCommand,
						BindingResult result,
						HttpServletRequest request, Model model) {
		
		//command파라미터 다음에 BindResult선언
		//command유효값처리 결과가 BindingResult에서 받아짐
		if(result.hasErrors()) {
			return "thymeleaf/member/login";
		}
		String path=MemberService.loginUser(loginCommand, request);
		return path;
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		System.out.println("로그아웃");
		request.getSession().invalidate();
		return "redirect:/home";
	}
	
	//나의 정보조회
	//나의 정보수정
	//탈퇴
}
