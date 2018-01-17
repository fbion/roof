package org.roof.fileupload.alimpl;

import static com.aliyun.oss.internal.OSSConstants.DEFAULT_BUFFER_SIZE;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.roof.fileupload.api.FileInfo;
import org.roof.fileupload.api.FileManager;
import org.roof.fileupload.exception.FileException;
import org.roof.fileupload.exception.FileInfoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring-*.xml" })
public class FileManageTest extends AbstractJUnit4SpringContextTests {
	private FileManager fileManager;
	private AliOssFileService aliOssFileService;

	@Test
	public void testSaveFile() throws FileException {
		File file = new File("E:/excel/IMAG1224.jpg");
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			Map<String, Object> xdata = new HashMap<String, Object>();
			xdata.put("displayName", "IMAG122-14.jpg");
			xdata.put("fileSize", file.getTotalSpace());
			fileManager.saveFile(in, xdata);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void testDeleteFile() throws FileException, FileNotFoundException, FileInfoNotFoundException {
		fileManager.deleteFile("dad875d7-e958-4465-ad04-e5c8f28bfa22.jpg");
	}

	@Test
	public void testGetFile() {
		InputStream in = null;
		OutputStream os = null;
		try {
			in = fileManager.getFile("879fa862-771a-4cdd-be5e-4e7e85d7516e.jpg");
			File file = new File("E:/excel/879fa862-771a-4cdd-be5e-4e7e85d7516e.jpg");
			if (!file.exists()) {
				file.createNewFile();
			}
			os = new BufferedOutputStream(new FileOutputStream(file));
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int bytesRead;
			while ((bytesRead = in.read(buffer)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void testGeneratePresignedUrl() throws FileInfoNotFoundException {
		FileInfo fileInfo = fileManager.getFileInfoService().loadByName("9d609a37-4db7-4efd-8c15-9cd6a7743ae4.jpg");
		URL url = aliOssFileService.generatePresignedUrl(fileInfo);
		System.out.println(url.toString());
	}

	@Autowired(required = true)
	public void setFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}

	@Autowired(required = true)
	public void setAliOssFileService(AliOssFileService aliOssFileService) {
		this.aliOssFileService = aliOssFileService;
	}

}
