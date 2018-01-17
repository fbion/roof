package org.roof.rqcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

public class QrcodeService {

	private static final Logger LOGGER = Logger.getLogger(QrcodeService.class);

	/**
	 * 解析二维码
	 * 
	 * @param filePath
	 *            二维码图片的文件路径 如:C:/Users/Administrator/Desktop/qr.jpg
	 * @return 二维码的内容
	 */
	public static String parse(String filePath) {
		String content = "";
		MultiFormatReader formatReader = new MultiFormatReader();
		File file = new File(filePath);
		Result result = null;
		try {
			BufferedImage image = ImageIO.read(file);
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			Map hints = new HashMap();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			result = formatReader.decode(binaryBitmap, hints);
		} catch (NotFoundException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		content = result.toString();
		return content;
	}

	/**
	 * 生成二维码
	 * 
	 * @param content
	 *            二维码内容 如一个URL：http://www.baidu.com
	 * @param fileDir
	 *            生成的文件目录 如一个文件夹：C:/Users/Administrator/Desktop
	 * @return 生成的文件名
	 */
	public static String create(String content, String fileDir) {
		String name = UUID.randomUUID().toString().toUpperCase() + ".jpg";
		try {
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			Map hints = new HashMap();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 200, 200, hints);
			File file1 = new File(fileDir, name);
			MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			name = "";
		}
		return name;
	}

	/**
	 * 二维码转换成流输出
	 * 
	 * @param content
	 * @return
	 * @throws WriterException
	 * @throws IOException
	 */
	public static void createAsStream(String content, OutputStream os) throws WriterException, IOException {
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		Map hints = new HashMap();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hints.put(EncodeHintType.MARGIN, 1); // 白色边框的设置
		BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 200, 200, hints);
		MatrixToImageWriter.writeToStream(bitMatrix, "jpg", os);
	}

}
