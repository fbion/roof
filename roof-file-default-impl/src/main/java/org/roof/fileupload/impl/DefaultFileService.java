package org.roof.fileupload.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.roof.commons.PropertiesUtil;
import org.roof.commons.RoofDateUtils;
import org.roof.fileupload.api.FileInfo;
import org.roof.fileupload.api.FileService;
import org.roof.fileupload.exception.FileException;
import org.roof.fileupload.utils.FileUtils;
import org.springframework.beans.factory.InitializingBean;

public class DefaultFileService implements FileService, InitializingBean {
	private Logger LOGGER = Logger.getLogger(DefaultFileService.class);

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
	public void deleteByFileInfo(FileInfo fileInfo) throws FileNotFoundException, FileException {
		File file = new File(fileInfo.getRealPath());
		if(file!=null){
			file.delete();
		}
	}

	@Override
	public byte[] loadDataByFileInfo(FileInfo fileInfo) {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream(); InputStream in = loadByFileInfo(fileInfo);) {
			FileUtils.copy(in, out);
			out.flush();
			return out.toByteArray();
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public FileInfo saveByFileInfo(byte[] data, FileInfo fileInfo, Map<String, Object> xdata) throws FileException {
		try (InputStream in = new ByteArrayInputStream(data);) {
			saveByFileInfo(in, fileInfo, xdata);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return fileInfo;
	}

	@Override
	public FileInfo saveByFileInfo(InputStream in, FileInfo fileInfo, Map<String, Object> xdata) {
		String rootDir = PropertiesUtil.getPorpertyString("rootDir");
		String upFile = rootDir + "/" + RoofDateUtils.dateToString(new Date(), "yyyy-MM-dd");
		String newPath = upFile + "/" + fileInfo.getName();

		fileInfo.setWebPath("http://" + xdata.get("ip") + ":" + xdata.get("port") + "/" + xdata.get("web") + "/"
				+ fileInfo.getName());
		fileInfo.setRealPath(newPath);
		this.upload(in, fileInfo);
		return fileInfo;
	}

	private void upload(InputStream in, FileInfo fileInfo) {
		String newPath = fileInfo.getRealPath();
		try (FileOutputStream fnew = new FileOutputStream(newPath);) {
			File dst = new File(newPath);
			File dir = new File(dst.getParent());
			// 如果目录不存在,则创建目录;
			if (!dir.exists()) {
				dir.mkdirs();
			}
			if (!dst.exists()) {
				dst.createNewFile();
			}
			FileUtils.copy(dir, dst);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

}
