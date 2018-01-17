package org.roof.fileupload.impl;

import org.roof.fileupload.api.FileInfo;

public class SimpleFileInfo implements FileInfo {

	private static final long serialVersionUID = 991062904994454712L;

	private long id;
	private String displayName;// 显示的文件名
	private String name; // 存放在服务器端的文件名
	private long fileSize; // 文件大小
	private String realPath; // 服务器物理地址
	private String webPath; // web相对路径
	private String type; // 文件类型(后缀)

	public long getId() {
		return id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getName() {
		return name;
	}

	public String getRealPath() {
		return realPath;
	}

	public String getWebPath() {
		return webPath;
	}

	public String getType() {
		return type;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	public void setWebPath(String webPath) {
		this.webPath = webPath;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

}
