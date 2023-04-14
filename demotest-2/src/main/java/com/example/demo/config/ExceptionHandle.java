package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandle {
	private Logger logger=LoggerFactory.getLogger(ExceptionHandle.class);
		
		@ExceptionHandler(Exception.class)
		public String handleException(Exception e, Model model) {
			logger.error("Exception 발생:{}",e.getMessage());
			model.addAttribute("msgs", "오류가 발생하여 확인중입니다.");
			return "thymeleaf/error";
		}
}
