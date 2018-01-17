package org.roof.fileupload.api;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import org.roof.fileupload.exception.FileException;

public interface FileService {
	/**
	 * 加载文件
	 * 
	 * @param fileInfo
	 *            文件描述
	 */
	InputStream loadByFileInfo(FileInfo fileInfo);

	byte[] loadDataByFileInfo(FileInfo fileInfo);

	/**
	 * 删除文件
	 * 
	 * @param fileInfo
	 * @exception java.io.FileNotFoundException
	 * @throws FileException
	 */
	void deleteByFileInfo(FileInfo fileInfo) throws FileNotFoundException, FileException;

	/**
	 * 保存文件
	 * 
	 * @param file
	 *            文件
	 * @param fileInfo
	 *            文件描述
	 * @param xdata
	 *            扩展属性
	 * @throws FileException
	 */
	FileInfo saveByFileInfo(InputStream in, FileInfo fileInfo, Map<String, Object> xdata) throws FileException;

	/**
	 * 保存文件
	 * 
	 * @param file
	 *            文件
	 * @param fileInfo
	 *            文件描述
	 * @param xdata
	 *            扩展属性
	 * @throws FileException
	 */
	FileInfo saveByFileInfo(byte[] data, FileInfo fileInfo, Map<String, Object> xdata) throws FileException;

}
