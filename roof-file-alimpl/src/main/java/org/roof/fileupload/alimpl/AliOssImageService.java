package org.roof.fileupload.alimpl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.imageio.ImageIO;

import org.roof.fileupload.api.FileInfo;
import org.roof.fileupload.exception.FileException;
import org.roof.fileupload.utils.ImageUtils;
import org.springframework.util.Assert;

public class AliOssImageService extends AliOssFileService {

	private int boxWidth = 300;
	private int boxHeight = 300;

	@Override
	public FileInfo saveByFileInfo(InputStream in, FileInfo fileInfo,
			Map<String, Object> xdata) throws FileException {
		super.saveByFileInfo(in, fileInfo, xdata);
		in = loadByFileInfo(fileInfo);

		BufferedImage thumbSource = null;
		InputStream thumbIn = null;
		ByteArrayOutputStream os = null;
		try {
			thumbSource = ImageUtils.read(in);
			BufferedImage target = ImageUtils.compressToBox(thumbSource,
					boxWidth, boxHeight);

			os = new ByteArrayOutputStream();
			ImageIO.write(target, "jpg", os);
			thumbIn = new ByteArrayInputStream(os.toByteArray());
			uploadFile(thumbIn, fileInfo.getType(),
					createThumbRealPath(fileInfo.getRealPath()));
		} catch (IOException e) {
			throw new FileException(e);
		}
		return fileInfo;
	}

	public String createThumbRealPath(String realPath) {
		Assert.notNull(realPath);
		int pos = realPath.lastIndexOf(".");
		return realPath.substring(0, pos).concat("-thumb")
				.concat(realPath.substring(pos, realPath.length()));

	}

	@Override
	public void deleteByFileInfo(FileInfo fileInfo)
			throws FileNotFoundException, FileException {
		String thumbRealPath = createThumbRealPath(fileInfo.getRealPath());
		client.deleteObject(bucketName, thumbRealPath);
		client.deleteObject(bucketName, fileInfo.getRealPath());
	}

	public int getBoxWidth() {
		return boxWidth;
	}

	public int getBoxHeight() {
		return boxHeight;
	}

	public void setBoxWidth(int boxWidth) {
		this.boxWidth = boxWidth;
	}

	public void setBoxHeight(int boxHeight) {
		this.boxHeight = boxHeight;
	}

}
