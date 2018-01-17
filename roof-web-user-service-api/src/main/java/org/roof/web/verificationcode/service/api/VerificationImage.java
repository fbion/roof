package org.roof.web.verificationcode.service.api;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

public class VerificationImage {
	private static final Logger LOGGER = Logger.getLogger(VerificationImage.class);

	/**
	 * 生成验证码图片
	 * 
	 * @param securityCode
	 *            验证码字符
	 * @return BufferedImage 图片
	 */
	public static BufferedImage createImage(String securityCode) {

		// 验证码长度
		int codeLength = securityCode.length();
		// 字体大小
		int fSize = 16;
		// 图片宽度
		int width = fSize * 5;
		// 图片高度
		int height = fSize * 2;

		// 图片
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.createGraphics();

		// 设置背景色
		g.setColor(Color.WHITE);
		// 填充背景
		g.fillRect(0, 0, width, height);

		// 设置边框颜色
		g.setColor(Color.LIGHT_GRAY);
		// 边框字体样式
		g.setFont(new Font("Arial", Font.BOLD, height - 2));
		// 绘制边框
		g.drawRect(0, 0, width - 1, height - 1);

		Random rand = new Random();
		// 设置噪点颜色
		g.setColor(getRandColor(150, 200));
		for (int i = 0; i < codeLength * 6; i++) {
			int x = rand.nextInt(width);
			int y = rand.nextInt(height);
			// 绘制1*1大小的矩形
			g.drawRect(x, y, 1, 1);
		}

		// 随机产生干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(getRandColor(120, 180));
		for (int i = 0; i < codeLength * 12; i++) {
			int x = rand.nextInt(width);
			int y = rand.nextInt(height);
			int xl = rand.nextInt(20);
			int yl = rand.nextInt(20);
			g.drawLine(x, y, x + xl, y + yl);
		}

		// 绘制验证码
		int codeY = height - 10;
		// 设置字体颜色和样式
		// g.setColor(new Color(60, 50, 210));
		g.setColor(getRandColor(30, 80));
		// g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, fSize));
		// g.setFont(new Font("Georgia", Font.BOLD, fSize));
		for (int i = 0; i < codeLength; i++) {
			g.drawString(String.valueOf(securityCode.charAt(i)), i * (width / codeLength) + 5, codeY);
		}
		// 关闭资源
		g.dispose();

		return image;
	}

	private static Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	/**
	 * 返回验证码图片的流格式
	 * 
	 * @param securityCode
	 *            验证码
	 * @return ByteArrayInputStream 图片流
	 */
	public static ByteArrayInputStream getImageAsInputStream(String securityCode) {
		BufferedImage image = createImage(securityCode);
		return convertImageToStream(image);
	}

	/**
	 * 将BufferedImage转换成ByteArrayInputStream
	 * 
	 * @param image
	 *            图片
	 * @return ByteArrayInputStream 流
	 */
	private static ByteArrayInputStream convertImageToStream(BufferedImage image) {
		ByteArrayInputStream inputStream = null;
		ByteArrayOutputStream os = null;
		try {
			os = new ByteArrayOutputStream();
			ImageIO.write(image, "gif", os);
			inputStream = new ByteArrayInputStream(os.toByteArray());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			try {
				os.close();
			} catch (IOException e1) {
				LOGGER.error(e1.getMessage(), e1);
			}
		}
		return inputStream;
	}

}
