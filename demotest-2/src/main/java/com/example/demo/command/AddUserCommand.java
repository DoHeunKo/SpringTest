package com.example.demo.command;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddUserCommand {
	@NotBlank(message = "ID입력 필수")
	private String id;
	@NotBlank(message = "이름입력 필수")
	private String name;
	@NotBlank(message = "비밀번호 입력 필수")
	@Length(min = 8,max =16,message = "비밀번호는 8~16이하로 입력")
	private String password;
	@NotBlank(message = "이메일 입력 필수")
	private String email;
	@NotBlank(message = "주소 입력 필수")
	private String address;
}
