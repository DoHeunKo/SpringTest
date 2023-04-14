package com.example.demo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.example.demo.dtos.FileBoardDto;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class FileService {
	
	//파일 하나 업로드 하기
	public FileBoardDto uploadFile(String uploadPath,MultipartFile multipartFile) throws IllegalStateException, IOException {
		String origin_filename=multipartFile.getOriginalFilename();
		String stored_filename=UUID.randomUUID()
						+origin_filename.substring(origin_filename.indexOf("."));
		String fileuploadUrl=uploadPath+"/"+stored_filename;
		
		multipartFile.transferTo(new File(fileuploadUrl));//upload가 실행
		return new FileBoardDto(0, 0, origin_filename, stored_filename);
	}
	//파일 여러개 업로드 하기
	
	public List<FileBoardDto> uploadFiles(String uploadPath,MultipartRequest multipartRequest) throws IllegalStateException, IOException {
		
		List<MultipartFile> multipartFiles=multipartRequest.getFiles("filename");
		//DB에 저장할 파일 List를 만들어서 전달
		List<FileBoardDto> uploadFileList=new ArrayList<>();
		for(MultipartFile multipartFile:multipartFiles) {
			//원본파일명, 저장파일명,파일저장경로 
			//업로드
			String origin_filename=multipartFile.getOriginalFilename();
			String stored_filename=UUID.randomUUID()
							+origin_filename.substring(origin_filename.indexOf("."));
			String fileuploadUrl=uploadPath+"/"+stored_filename;
			
			multipartFile.transferTo(new File(fileuploadUrl));//upload가 실행
			uploadFileList.add(new FileBoardDto(0, 0, origin_filename, stored_filename));
		}
		
		return uploadFileList;
	}

	public void fileDownload(String filePath, String origin_filename, String stored_filename,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		
		//다운로드를 위한 준비 작업
		
		//읽어들인 파일 정보를 화면에 다운할수 있게 설정
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition",
                "attachment; filename="+URLEncoder.encode(origin_filename, "UTF-8"));
		response.setHeader("Content-transfer", "binary");
		
		File file=new File(filePath+"/"+stored_filename);
		
		//시스템에 있을 파일을 가져오기 위한 객체 
		FileInputStream fs=null;
		//웹 브라우저로 내보내기 위한 객체 
		ServletOutputStream out=null;
		try {
			
			fs=new FileInputStream(file);
			out=response.getOutputStream();
			
			//전송
			FileCopyUtils.copy(fs, out);
			response.flushBuffer();
			out.flush();
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(fs!=null) {
				try {
					fs.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
