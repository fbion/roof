package org.roof.commons;

import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;

public class RoofNumberUtils {

	/**
	 * 保留两位小数
	 * 
	 * @param number
	 * @return
	 */
	public static String formatString(Double number) {
		if (number.toString().equals("NaN")) {
			number = new Double(0);
		}
		// if(number.toString().equals("Infinity")){
		// number = new Double(0);
		// }
		DecimalFormat format = new DecimalFormat("#0.##");
		return format.format(number);
	}

	public static int getNumberInteger(Double number) {
		return getNumberInteger(number.toString());
	}

	public static int getNumberInteger(String number) {
		if (number.contains(".")) {
			number = number.substring(0, number.lastIndexOf("."));
		}
		return Integer.parseInt(number);
	}

	/**
	 * 将数字num自动补齐成指定的len长度，前面以0补齐<br/>
	 * 如：num="23";len=4; 则返回"0023";
	 * 
	 * @param num
	 * @param len
	 * @return
	 */
	public static String fillNumLen(String num, int len) {
		if (StringUtils.isEmpty(num)) {
			return num;
		}
		String rs = "";
		for (int i = num.length(); i < len; i++) {
			rs += "0";
		}
		return rs + num;
	}

	/**
	 * 保留两位小数
	 * 
	 * @param number
	 * @return
	 */
	public static Double formatDouble(Double number) {
		if (number == null || number.toString().equals("NaN")) {
			number = new Double(0);
		}
		// if(number.toString().equals("Infinity")){
		// number = new Double(0);
		// }
		DecimalFormat format = new DecimalFormat("#0.##");
		return Double.valueOf(format.format(number));
	}

}
