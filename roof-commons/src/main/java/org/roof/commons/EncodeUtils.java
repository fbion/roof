package org.roof.commons;

public class EncodeUtils {
	/*********************************
	 * 作者： hongzc 时间： 2010-11-24
	 * 
	 * @param source
	 *            源字符串
	 * @return 转换成中文 ISO-8859-1
	 *********************************/
	public static String trainsGBK(String source) {
		return trains(source, "ISO-8859-1");
	}

	/*********************************
	 * 作者： hongzc 时间： 2010-11-24
	 * 
	 * @param source
	 *            源字符串
	 * @return 转换成 UTF-8
	 *********************************/
	public static String trainsUTF(String source) {
		return trains(source, "UTF-8");
	}

	/*********************************
	 * 作者： hongzc 时间： 2010-11-24
	 * 
	 * @param source
	 *            源字符串
	 * @param charset
	 *            [GBK,UTF-8,ISO-8859-1等]
	 * @return 转换成指定类型的编码
	 *********************************/
	public static String trains(String source, String charset) {
		String result = null;
		byte[] arr = null;
		try {
			arr = source.getBytes(charset);
			result = new String(arr);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public static String trains(String source, String srcCharset, String distCharset) {
		String result = null;
		byte[] arr = null;
		try {
			arr = source.getBytes(srcCharset);
			result = new String(arr, distCharset);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

}
