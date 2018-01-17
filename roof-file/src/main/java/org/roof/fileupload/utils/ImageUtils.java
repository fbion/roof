package org.roof.fileupload.utils;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

/**
 * 图片工具
 * 
 * @author liuxin
 * 
 */
public class ImageUtils {

	private static final Logger logger = Logger.getLogger(ImageUtils.class);

	/**
	 * 压缩图片为指定的高宽
	 * 
	 * @param source
	 *            源图像
	 * @param width
	 *            需要的宽度
	 * @param height
	 *            需要的高度
	 * @return 压缩后的图像
	 */
	public static BufferedImage compress(BufferedImage source, int width,
			int height) {
		BufferedImage tag = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		tag.getGraphics().drawImage(source, 0, 0, width, height, null);
		return tag;
	}

	public static BufferedImage compress(BufferedImage source, double width,
			double height) {
		return compress(source, (int) width, (int) height);
	}

	/**
	 * 压缩图片可以最大限度的放入制定的容器大小内
	 * 
	 * @param source
	 *            源图像
	 * @param boxWidth
	 *            容器的宽度
	 * @param boxHeight
	 *            容器的高度
	 * @return
	 */
	public static BufferedImage compressToBox(BufferedImage source,
			int boxWidth, int boxHeight) {
		double widthProp = 0;
		double heightProp = 0;
		double zoom = 0;
		int width = source.getWidth();
		int height = source.getHeight();
		widthProp = boxWidth / (double) width;
		heightProp = boxHeight / (double) height;
		zoom = widthProp;
		if (zoom > heightProp) {
			zoom = heightProp;
		}
		return compress(source, width * zoom, height * zoom);
	}

	/**
	 * 根据起始坐标和结束坐标截取图片
	 * 
	 * @param source
	 *            原始图片
	 * @param xfrom
	 *            起始x坐标
	 * @param yfrom
	 *            起始y坐标
	 * @param xto
	 *            结束x坐标
	 * @param yto
	 *            结束y坐标
	 * @return 截取后的图片
	 */
	public static BufferedImage cut(BufferedImage source, int xfrom, int yfrom,
			int xto, int yto) {
		int width = xto - xfrom;
		int height = yto - yfrom;
		return cutBySize(source, xfrom, yfrom, width, height);
	}

	/**
	 * 根据起始坐标和需要的尺寸截取图片
	 * 
	 * @param source原始图片
	 * @param x
	 *            起始x坐标
	 * @param y
	 *            起始y坐标
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @return
	 */
	public static BufferedImage cutBySize(BufferedImage source, int x, int y,
			int width, int height) {
		CropImageFilter cropImageFilter = new CropImageFilter(x, y, width,
				height);
		Image image = Toolkit.getDefaultToolkit().createImage(
				new FilteredImageSource(source.getSource(), cropImageFilter));
		BufferedImage tag = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		tag.getGraphics().drawImage(image, 0, 0, null);
		return tag;
	}

	/**
	 * 写出图片
	 * 
	 * @param target
	 *            需要写出的图片
	 * @param out
	 *            输出流
	 * @throws IOException
	 */
	public static void write(BufferedImage source, OutputStream out)
			throws IOException {
		ImageIO.write(source, "JPEG", out);
	}

	public static void write(BufferedImage source, File out) throws IOException {
		OutputStream outputStream = new FileOutputStream(out);
		try {
			write(source, outputStream);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			outputStream.close();
		}
	}

	/**
	 * 从输入流读入图片
	 * 
	 * @param in
	 *            输入流
	 * @return BufferedImage 对象
	 * @throws IOException
	 */
	public static BufferedImage read(InputStream in) throws IOException {
		BufferedImage bufferedImage = ImageIO.read(in);
		return bufferedImage;
	}

	public static BufferedImage read(File in) throws IOException {
		InputStream inputStream = new FileInputStream(in);
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = read(inputStream);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			inputStream.close();
		}
		return bufferedImage;
	}

}
