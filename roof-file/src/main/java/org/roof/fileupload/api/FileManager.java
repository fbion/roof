package org.roof.fileupload.api;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import org.roof.fileupload.exception.FileException;
import org.roof.fileupload.exception.FileInfoNotFoundException;

public interface FileManager {
	/**
	 * 保存文件
	 * 
	 * @param file
	 *            文件
	 * @param xdata
	 *            文件扩展属性
	 * @throws FileException
	 */
	FileInfo saveFile(InputStream in, Map<String, Object> xdata) throws FileException;

	/**
	 * 获取文件
	 * 
	 * @param fileInfo
	 * @return
	 */
	InputStream getFile(String name);

	/**
	 * 删除文件
	 * 
	 * @exception FileException
	 */
	void deleteFile(String name) throws FileException, FileInfoNotFoundException, FileNotFoundException;

	FileInfoService getFileInfoService();

	/**
	 * @param fileInfoService
	 */
	void setFileInfoService(FileInfoService fileInfoService);

	FileService getFileService();

	/**
	 * @param fileService
	 */
	void setFileService(FileService fileService);

}
