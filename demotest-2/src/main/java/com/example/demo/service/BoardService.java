package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.example.demo.command.InsertBoardCommand;
import com.example.demo.dtos.BoardDto;
import com.example.demo.dtos.FileBoardDto;
import com.example.demo.mapper.BoardMapper;

@Service
public class BoardService {
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private BoardDto boardDto;
	
	@Autowired
	private FileService fileService;
	
	public List<BoardDto> getAllList(){
		return boardMapper.getAllList();
	}
	
	public BoardDto getBoard(int board_seq) {
		return boardMapper.getBoard(board_seq);
	}
	
	public boolean mulDel(String[] seqs) {
		return boardMapper.mulDel(seqs);
	}
	
	@Transactional
	public boolean insertBoard(InsertBoardCommand insertBoardCommand,
//			MultipartFile multiFile ) throws IllegalStateException, IOException {
			MultipartRequest multipartRequest ) throws IllegalStateException, IOException {
		boardDto.setId(insertBoardCommand.getId());
		boardDto.setTitle(insertBoardCommand.getTitle());
		boardDto.setContent(insertBoardCommand.getContent());
		boolean isS=boardMapper.insertBoard(boardDto);
		//첨부가 되었다면
//		if(!multiFile.isEmpty()) {
//			//게시글의 첨부파일 경로[절대경로 설정]
//			String filePath="C:/workspace_backend/demotest-2/src/main/resources/upload";
//			//upload
//			FileBoardDto fdto=fileService.uploadFile(filePath, multiFile);
//			//DB에 저장
//			boardMapper.insertFileBoard(fdto);
//		}
		
		System.out.println("멀티파일:"+multipartRequest.getFiles("filename").size());
		if(!multipartRequest.getFiles("filename").isEmpty()) {
			String filePath="C:/workspace_backend/demotest-2/src/main/resources/upload";
			
			//업로드 후에, 업로드한 파일을 만든기능으로부터 가져온다
			List<FileBoardDto> uploadfileList=fileService.uploadFiles(filePath, multipartRequest);
			
			//mapper.xml의 useGe..를 통해 board_seq를 담아서 다시 가져온다
			for(FileBoardDto fdto: uploadfileList) {
				boardMapper.insertFileBoard(
						new FileBoardDto(0,boardDto.getBoard_seq(),fdto.getOrigin_filename(),fdto.getStored_filename()));
			}
		}
		return isS;
	}
	
	public FileBoardDto getFileInfo(int file_seq) {
		
		return boardMapper.getFileInfo(file_seq);
	}
	
}
