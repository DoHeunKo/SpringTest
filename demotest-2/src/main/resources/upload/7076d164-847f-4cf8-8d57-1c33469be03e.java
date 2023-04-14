package com.hk.fileboard.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hk.fileboard.dtos.FileDto;

public interface IFileService {

	public boolean insertFileInfo(HttpServletRequest request);
	
	public boolean multiInsertFileInfo(HttpServletRequest request);
	
	public List<FileDto> getFileList();
	
	public FileDto getFileInfo(int seq);
}
