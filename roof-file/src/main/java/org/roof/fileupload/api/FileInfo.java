package org.roof.fileupload.api;

import java.io.Serializable;

/**
 * 文件描述
 * 
 * @author liuxin
 *
 */
public interface FileInfo extends Serializable {
	/**
	 * 文件名称，应用内必须是唯一
	 * 
	 * @return
	 */
	String getName();

	/**
	 * @param name
	 *            文件名称
	 */
	void setName(String name);

	String getDisplayName();

	/**
	 * @param displayName
	 *            文件显示名称
	 */
	void setDisplayName(String displayName);

	String getRealPath();

	/**
	 * @param realPath
	 *            物理路径
	 */
	void setRealPath(String realPath);

	String getWebPath();

	/**
	 * @param webPath
	 *            访问Web路径
	 */
	void setWebPath(String webPath);

	long getFileSize();

	/**
	 * @param fileSize
	 *            文件长度
	 */
	void setFileSize(long fileSize);

	/**
	 * 获得文件类型
	 * 
	 * @return
	 */
	String getType();

	/**
	 * 设置文件类型
	 * 
	 * @param type
	 *            文件类型
	 */
	void setType(String type);

}
