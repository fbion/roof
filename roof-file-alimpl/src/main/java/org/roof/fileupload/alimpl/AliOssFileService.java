package org.roof.fileupload.alimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.roof.fileupload.api.FileInfo;
import org.roof.fileupload.api.FileService;
import org.roof.fileupload.api.PathGenerator;
import org.roof.fileupload.exception.FileException;
import org.roof.fileupload.utils.FileUtils;
import org.springframework.beans.factory.InitializingBean;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.internal.OSSConstants;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

public class AliOssFileService implements FileService, InitializingBean {

	protected String accessKey;
	protected String accessKeySecret;
	protected String ossEndpoint = OSSConstants.DEFAULT_OSS_ENDPOINT;
	protected String bucketName;
	protected static final Logger LOG = Logger.getLogger(AliOssFileService.class);
	protected OSSClient client;
	protected PathGenerator webPathGenerator;
	protected long expiration = 3600 * 1000;

	@Override
	public void afterPropertiesSet() throws Exception {
		client = new OSSClient(ossEndpoint, accessKey, accessKeySecret);
	}

	@Override
	public InputStream loadByFileInfo(FileInfo fileInfo) {
		OSSObject ossObject = client.getObject(bucketName, fileInfo.getRealPath());
		return ossObject.getObjectContent();
	}

	@Override
	public byte[] loadDataByFileInfo(FileInfo fileInfo) {
		try (InputStream in = loadByFileInfo(fileInfo); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			FileUtils.copy(in, out);
			out.flush();
			return out.toByteArray();
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public void deleteByFileInfo(FileInfo fileInfo) throws FileNotFoundException, FileException {
		client.deleteObject(bucketName, fileInfo.getWebPath());
	}

	@Override
	public FileInfo saveByFileInfo(InputStream in, FileInfo fileInfo, Map<String, Object> xdata) throws FileException {
		try {
			fileInfo.setWebPath(
					"http://" + bucketName + "." + ossEndpoint + "/" + webPathGenerator.getPath() + fileInfo.getName());
			fileInfo.setRealPath(webPathGenerator.getPath() + fileInfo.getName());
			fileInfo.setDisplayName(ObjectUtils.toString(xdata.get("displayName"), ""));
			uploadFile(in, fileInfo);
		} catch (OSSException | ClientException | FileNotFoundException e) {
			throw new FileException(e);
		}
		return fileInfo;
	}

	@Override
	public FileInfo saveByFileInfo(byte[] data, FileInfo fileInfo, Map<String, Object> xdata) throws FileException {
		try (InputStream in = new ByteArrayInputStream(data)) {
			saveByFileInfo(in, fileInfo, xdata);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		return fileInfo;
	}

	/**
	 * 获取文件的授权链接
	 * 
	 * @param fileInfo
	 * @return
	 */
	public URL generatePresignedUrl(FileInfo fileInfo) {
		URL url = client.generatePresignedUrl(bucketName, fileInfo.getRealPath(),
				new Date(System.currentTimeMillis() + expiration));
		fileInfo.setRealPath(url.toString());
		return url;
	}

	// 上传文件
	protected void uploadFile(InputStream in, FileInfo fileInfo)
			throws OSSException, ClientException, FileNotFoundException {
		uploadFile(in, fileInfo.getType(), fileInfo.getRealPath());
	}

	protected void uploadFile(InputStream in, String type, String realPath)
			throws OSSException, ClientException, FileNotFoundException {
		ObjectMetadata objectMeta = new ObjectMetadata();
		// objectMeta.setContentLength(file.length());
		// 可以在metadata中标记文件类型
		objectMeta.setContentType(type);
		PutObjectResult objectResult = client.putObject(bucketName, realPath, in, objectMeta);
		if (LOG.isDebugEnabled()) {
			LOG.debug(objectResult.getETag());
		}
	}

	public String getOssEndpoint() {
		return ossEndpoint;
	}

	public String getBucketName() {
		return bucketName;
	}

	public OSSClient getClient() {
		return client;
	}

	public PathGenerator getWebPathGenerator() {
		return webPathGenerator;
	}

	public void setOssEndpoint(String ossEndpoint) {
		this.ossEndpoint = ossEndpoint;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public void setClient(OSSClient client) {
		this.client = client;
	}

	public void setWebPathGenerator(PathGenerator webPathGenerator) {
		this.webPathGenerator = webPathGenerator;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public long getExpiration() {
		return expiration;
	}

	public void setExpiration(long expiration) {
		this.expiration = expiration;
	}

}
