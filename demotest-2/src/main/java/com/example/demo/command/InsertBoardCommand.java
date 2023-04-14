package com.example.demo.command;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InsertBoardCommand {
	private int board_seq;
//	@NotBlank(message="아이디를 입력하세요")
	private String id;
	@NotBlank(message="제목를 입력하세요")
	private String title;
	@NotBlank(message="내용를 입력하세요")
	private String content;
}
