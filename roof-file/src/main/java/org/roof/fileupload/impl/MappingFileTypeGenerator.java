package org.roof.fileupload.impl;

import java.util.Map;

import org.roof.fileupload.api.FileTypeGenerator;
import org.roof.fileupload.utils.FileUtils;

public class MappingFileTypeGenerator implements FileTypeGenerator {
	private Map<String, String> fileTypeMapping;

	@Override
	public String getFileType(String name) {
		String e = FileUtils.getExtention(name).toLowerCase();
		return fileTypeMapping.get(e);
	}

	public Map<String, String> getFileTypeMapping() {
		return fileTypeMapping;
	}

	public void setFileTypeMapping(Map<String, String> fileTypeMapping) {
		this.fileTypeMapping = fileTypeMapping;
	}

}
