package org.roof.fileupload.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.log4j.Logger;
import org.roof.commons.PropertiesUtil;
import org.roof.fileupload.api.FileInfo;
import org.roof.fileupload.api.FileService;
import org.roof.fileupload.api.PathGenerator;
import org.roof.fileupload.exception.FileException;
import org.roof.fileupload.utils.FileUtils;
import org.springframework.beans.factory.InitializingBean;

public class PathFileService implements FileService, InitializingBean {
	private Logger LOGGER = Logger.getLogger(DefaultFileService.class);
	private PathGenerator pathGenerator = new DatePathGenerator();
	private String rootDir = PropertiesUtil.getPorpertyString("rootDir");

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	@Override
	public InputStream loadByFileInfo(FileInfo fileInfo) {
		try {
			return new FileInputStream(fileInfo.getRealPath());
		} catch (FileNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public byte[] loadDataByFileInfo(FileInfo fileInfo) {
		try (InputStream in = loadByFileInfo(fileInfo); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			FileUtils.copy(in, out);
			out.flush();
			return out.toByteArray();
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public void deleteByFileInfo(FileInfo fileInfo) throws FileNotFoundException, FileException {
		File file = new File(fileInfo.getRealPath());
		file.delete();
	}

	@Override
	public FileInfo saveByFileInfo(byte[] data, FileInfo fileInfo, Map<String, Object> xdata) throws FileException {
		try (InputStream in = new ByteArrayInputStream(data)) {
			saveByFileInfo(in, fileInfo, xdata);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return fileInfo;
	}

	@Override
	public FileInfo saveByFileInfo(InputStream in, FileInfo fileInfo, Map<String, Object> xdata) {
		String dir = rootDir + "/" + pathGenerator.getPath();
		String filePath = dir + "/" + fileInfo.getName();
		FileUtils.mkDirs(filePath);
		fileInfo.setWebPath(fileInfo.getName());
		fileInfo.setRealPath(filePath);
		try (FileOutputStream fout = new FileOutputStream(filePath)) {
			FileUtils.copy(in, fout);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return fileInfo;
	}

	public PathGenerator getPathGenerator() {
		return pathGenerator;
	}

	public void setPathGenerator(PathGenerator pathGenerator) {
		this.pathGenerator = pathGenerator;
	}

}
