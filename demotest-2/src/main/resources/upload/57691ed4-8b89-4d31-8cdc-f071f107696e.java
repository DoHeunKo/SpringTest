package com.hk.fileboard.daos;

import java.util.List;

import com.hk.fileboard.dtos.FileDto;

public interface IFileDao {

	public boolean insertFileInfo(FileDto dto);
	
	public List<FileDto> getFileList();
	
	public FileDto getFileInfo(int seq);
}
