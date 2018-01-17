package org.roof.fileupload.alimpl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.roof.fileupload.api.FileInfo;
import org.roof.fileupload.api.FileInfoService;
import org.roof.fileupload.api.FileNameGenerator;
import org.roof.fileupload.api.FileTypeGenerator;
import org.roof.fileupload.exception.FileException;
import org.roof.fileupload.exception.FileInfoNotFoundException;
import org.roof.fileupload.impl.SimpleFileInfo;
import org.roof.fileupload.utils.FileUtils;
import org.roof.roof.dataaccess.api.IDaoSupport;

public class MybatisFileInfoService implements FileInfoService {
	private IDaoSupport daoSupport;
	private FileNameGenerator fileNameGenerator;
	private FileTypeGenerator fileTypeGenerator;

	@Override
	public FileInfo loadByName(String name) throws FileInfoNotFoundException {
		FileInfo fileInfo = new SimpleFileInfo();
		fileInfo.setName(name);

		@SuppressWarnings("unchecked")
		List<FileInfo> fileInfos = (List<FileInfo>) daoSupport.selectForList(
				"org.roof.fileupload.impl.dao.selectSimpleFileInfo", fileInfo);
		if (CollectionUtils.isEmpty(fileInfos)) {
			throw new FileInfoNotFoundException("fileinfo name:["
					+ fileInfo.getName() + "] not found");
		}
		if (fileInfos.size() > 1) {
			throw new FileInfoNotFoundException("fileinfo name:["
					+ fileInfo.getName() + "] 大于1条数据");
		}
		return fileInfos.get(0);
	}

	@Override
	public int deleteByName(String name) throws FileInfoNotFoundException {
		FileInfo fileInfo = new SimpleFileInfo();
		fileInfo.setName(name);
		daoSupport.deleteByExample(fileInfo);
		return 0;
	}

	@Override
	public FileInfo createFileInfo(Map<String, Object> xdata) {
		String displayName = ObjectUtils.toString(xdata.get("displayName"), "");
		FileInfo fileInfo = new SimpleFileInfo();
		fileInfo.setName(fileNameGenerator.getFileName()
				+ FileUtils.getExtention(displayName));
		fileInfo.setDisplayName(displayName);
		fileInfo.setType(fileTypeGenerator.getFileType(displayName));
		fileInfo.setFileSize((long) xdata.get("fileSize"));
		return fileInfo;
	}

	@Override
	public FileInfo saveFileInfo(FileInfo fileInfo) throws FileException {
		daoSupport.save(fileInfo);
		return fileInfo;
	}

	public IDaoSupport getDaoSupport() {
		return daoSupport;
	}

	public void setDaoSupport(IDaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}

	public FileNameGenerator getFileNameGenerator() {
		return fileNameGenerator;
	}

	public FileTypeGenerator getFileTypeGenerator() {
		return fileTypeGenerator;
	}

	public void setFileNameGenerator(FileNameGenerator fileNameGenerator) {
		this.fileNameGenerator = fileNameGenerator;
	}

	public void setFileTypeGenerator(FileTypeGenerator fileTypeGenerator) {
		this.fileTypeGenerator = fileTypeGenerator;
	}

}
