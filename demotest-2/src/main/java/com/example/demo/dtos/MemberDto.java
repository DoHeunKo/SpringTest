package com.example.demo.dtos;

import org.apache.ibatis.type.Alias;
import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Alias(value="MemberDto")
public class MemberDto {
	private int memberId;
	private String id;
	private String name;
	private String password;
	private String email;
	private String address;
	private String role;
}
