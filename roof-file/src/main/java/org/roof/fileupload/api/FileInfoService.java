package org.roof.fileupload.api;

import java.util.Map;

import org.roof.fileupload.exception.FileException;
import org.roof.fileupload.exception.FileInfoNotFoundException;

/**
 * 
 * @author liuxin
 *
 */

public interface FileInfoService {
	/**
	 * 根据名称加载文件描述
	 * 
	 * @exception FileInfoNotFoundException
	 */
	FileInfo loadByName(String name) throws FileInfoNotFoundException;

	/**
	 * 根据名称删除文件描述
	 * 
	 * @exception FileInfoNotFoundException
	 */
	int deleteByName(String name) throws FileInfoNotFoundException;

	/**
	 * 创建文件描述
	 * 
	 * @param file
	 *            文件
	 * @param xdata
	 *            文件扩展属性
	 * @return
	 */
	FileInfo createFileInfo(Map<String, Object> xdata);

	/**
	 * 保存文件描述
	 * 
	 * @param fileInfo
	 *            文件描述
	 */
	FileInfo saveFileInfo(FileInfo fileInfo) throws FileException;

}
