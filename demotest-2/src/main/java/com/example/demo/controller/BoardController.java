package com.example.demo.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.example.demo.command.DelBoardCommand;
import com.example.demo.command.InsertBoardCommand;
import com.example.demo.dtos.BoardDto;
import com.example.demo.dtos.FileBoardDto;
import com.example.demo.dtos.MemberDto;
import com.example.demo.service.BoardService;
import com.example.demo.service.FileService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
@RequestMapping(value="/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private FileService fileService;
	
	@GetMapping("/boardList")
	public String boardList(Model model) {
		List<BoardDto> list=boardService.getAllList();
		model.addAttribute("list",list);
		model.addAttribute("delBoardCommand",new DelBoardCommand());
		return "thymeleaf/board/boardList";
	}
	
	@GetMapping("/boardDetail")
	public String boardDetail(int board_seq,Model model) {
		BoardDto dto=boardService.getBoard(board_seq);
		model.addAttribute("dto",dto);
		return "thymeleaf/board/boardDetail";
	}
	
	@GetMapping("/boardInsertForm")
	public String boardInsertForm(Model model) {
		model.addAttribute("insertBoardCommand",new InsertBoardCommand());
		return "thymeleaf/board/boardInsertForm";
	}
	
	@PostMapping("/boardInsert")
	public String boardInsert(@Validated InsertBoardCommand insertBoardCommand,
			BindingResult result
//			@RequestParam("filename") MultipartFile multiFile
			,MultipartRequest multipartRequest
			,Model model
			,HttpServletRequest request) {
		if(result.hasErrors()) {
			System.out.println("글모두 입력 필요");
			return "thymeleaf/board/boardInsertForm";
		}
		try {
			//글추가 폼에서 input에서 session이 안되서 가져와서 출력
			
			MemberDto mdto=(MemberDto)request.getSession().getAttribute("mdto");
			insertBoardCommand.setId(mdto.getId());
			
//			boardService.insertBoard(insertBoardCommand, multiFile);
			boardService.insertBoard(insertBoardCommand, multipartRequest);
			System.out.println("글추가 완료");
		} catch (Exception e) {
			e.printStackTrace();
			return "thymeleaf/board/boardInsertForm";
		}
		
		return "redirect:/board/boardList";
	}
	
	@PostMapping("/mulDel")
	public String mulDel(@Validated DelBoardCommand delBoardCommand
						,BindingResult result
						,Model model) {
		if(result.hasErrors()) {
			System.out.println("최소 하나 체크");
			List<BoardDto> list=boardService.getAllList();
			model.addAttribute("list",list);
			return "thymeleaf/board/boardlist";
		}
		boardService.mulDel(delBoardCommand.getChk());
		return "redirect:/board/boardList";
	}
	
	
	
	
	@GetMapping("/download")
	public void download(int file_seq,
						HttpServletRequest request,
						HttpServletResponse response) {
		
		System.out.println("다운로드 : "+file_seq);
		//실제 업로드할 Path
		//다운로드 준비
		String filePath="C:/workspace_backend/demotest-2/src/main/resources/upload";
		FileBoardDto fdto=boardService.getFileInfo(file_seq);
		
		try {
			fileService.fileDownload(filePath,
					fdto.getOrigin_filename(),
					fdto.getStored_filename(),
					request,response);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
