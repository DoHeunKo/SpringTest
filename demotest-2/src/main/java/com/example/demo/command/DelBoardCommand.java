package com.example.demo.command;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DelBoardCommand {
	@NotEmpty(message = "최소 하나이상 체크")
	private String[] chk;
}
