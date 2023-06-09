package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dtos.BoardDto;
import com.example.demo.dtos.FileBoardDto;

@Mapper
public interface BoardMapper {
	//글목록
	public List<BoardDto> getAllList();
	
	//글조회
	public BoardDto getBoard(int board_seq);
	
	//글추가
	public boolean insertBoard(BoardDto dto);
	
	//글삭제
	public boolean mulDel(String[] seqs);
	
	//파일정보추가
	public boolean insertFileBoard(FileBoardDto dto);
	
	//파일정보가지고오기
	
	public FileBoardDto getFileInfo(int file_seq);
	
	
}
