package org.roof.fileupload.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import org.apache.log4j.Logger;
import org.roof.fileupload.api.FileInfo;
import org.roof.fileupload.api.FileInfoService;
import org.roof.fileupload.api.FileManager;
import org.roof.fileupload.api.FileService;
import org.roof.fileupload.exception.FileException;
import org.roof.fileupload.exception.FileInfoNotFoundException;
import org.roof.fileupload.utils.FileUtils;

public class SimpleFileManager implements FileManager {
	private Logger LOGGER = Logger.getLogger(FileManager.class);

	private FileService fileService;
	private FileInfoService fileInfoService;

	@Override
	public FileInfo saveFile(InputStream in, Map<String, Object> xdata) throws FileException {
		FileInfo fileInfo = null;
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			FileUtils.copy(in, out);
			out.flush();
			fileInfo = fileInfoService.createFileInfo(xdata);
			fileInfo = fileService.saveByFileInfo(out.toByteArray(), fileInfo, xdata);
			fileInfoService.saveFileInfo(fileInfo);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return fileInfo;
	}

	@Override
	public InputStream getFile(String name) {
		FileInfo fileInfo = null;
		try {
			fileInfo = fileInfoService.loadByName(name);
		} catch (FileInfoNotFoundException e) {
			return null;
		}
		byte[] bs = fileService.loadDataByFileInfo(fileInfo);
		return new ByteArrayInputStream(bs);
	}

	@Override
	public void deleteFile(String name) throws FileException, FileInfoNotFoundException, FileNotFoundException {
		FileInfo fileInfo = fileInfoService.loadByName(name);
		if(fileInfo!=null){
			fileService.deleteByFileInfo(fileInfo);
		}
		fileInfoService.deleteByName(name);
	}

	@Override
	public FileService getFileService() {
		return fileService;
	}

	@Override
	public FileInfoService getFileInfoService() {
		return fileInfoService;
	}

	@Override
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}

	@Override
	public void setFileInfoService(FileInfoService fileInfoService) {
		this.fileInfoService = fileInfoService;
	}

}
