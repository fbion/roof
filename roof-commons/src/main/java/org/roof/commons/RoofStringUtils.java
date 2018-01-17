package org.roof.commons;

import java.util.Random;

import org.apache.commons.lang3.StringUtils;

/**
 * String工具<br />
 * 更多工具请使用 {@link StringUtils}
 * 
 * @author liuxin 2011-9-14
 * @version 1.0 RoofStringUtils.java liuxin 2011-9-14
 */
public class RoofStringUtils {
	private static final String UNDERLINE = "_";
	private static Random randGen = null;
	private static char[] numbersAndLetters = null;
	private static Object initLock = new Object();

	private static String[] codes = new String[] { "<", ">" };
	private static String[] chars = new String[] { "&lt;", "&gt;" };

	/**
	 * 在字符串数组中查询,指定字符串的位置, 如果存在返回字符串在数组中第一个出现的位置,<br>
	 * 如果不存在则返回-1
	 * 
	 * @param str
	 *            查找的字符串
	 * @param array
	 *            被查找的字符数组
	 * @return 字符串在数组中存在的位置
	 */
	public static final int containInArray(String str, String[] array) {
		if (str == null || array == null) {
			return -1;
		}
		for (int i = 0; i < array.length; i++) {
			if (StringUtils.equals(str, array[i])) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 转换字符串中的"<", ">" 为 "&lt;", "&gt;"
	 * 
	 * @param s
	 *            需要转换的字符串
	 * @return 转换后的字符串
	 */
	public static final String escape(String s) {
		return StringUtils.replaceEach(s, codes, chars);
	}

	/**
	 * 随机产生字符串
	 * 
	 * @param length
	 *            字符串长度
	 * @return 产生的字符串
	 */
	public static final String randomString(int length, String str) {
		if (length < 1) {
			return null;
		}
		if (randGen == null) {
			synchronized (initLock) {
				if (randGen == null) {
					randGen = new Random();
					numbersAndLetters = str.toCharArray();
				}
			}
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
		}
		return new String(randBuffer);
	}

	public static final String randomNumber(int length) {
		Random r = new Random();
		String rs = "";
		for (int i = 0; i < length; i++) {
			rs += r.nextInt(10);
		}
		return rs;
	}

	public static final String randomString(int length) {
		return randomString(length, "0123456789abcdefghijklmnopqrstuvwxyz" + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	}

	/**
	 * 将下划线分割的字符串转化为驼峰分割的字符串
	 * 
	 * @param str
	 *            要转换的字符串
	 * @param separator
	 * @return 转换后的字符串
	 */
	public static final String underlineToCamelCase(String str) {
		return toCamelCase(str, null);
	}

	/**
	 * 将指定分割符号分割的字符串转化为驼峰分割的字符串
	 * 
	 * @param str
	 *            要转换的字符串
	 * @param separator
	 * @return 转换后的字符串
	 */
	public static final String toCamelCase(String str, String separator) {
		if (StringUtils.isBlank(separator)) {
			separator = UNDERLINE;
		}
		if (StringUtils.indexOf(str, separator) == -1) {
			return str;
		}
		String[] strs = StringUtils.split(str, separator);
		for (int i = 0; i < strs.length; i++) {
			if (i == 0 && StringUtils.startsWith(str, separator)) {
				strs[i] = firstUpperCase(strs[i]);
			}
			if (i != 0) {
				strs[i] = firstUpperCase(strs[i]);
			}
		}
		return StringUtils.join(strs);
	}

	/**
	 * 将字符串的首字母转换为大写
	 * 
	 * @param str
	 *            要转换的字符串
	 * @return 转换后的字符串
	 */
	public static final String firstUpperCase(String str) {
		return StringUtils.upperCase(StringUtils.substring(str, 0, 1)) + StringUtils.substring(str, 1);
	}

	/**
	 * 将字符串的首字母转换为小写
	 * 
	 * @param str
	 *            要转换的字符串
	 * @return 转换后的字符串
	 */
	public static final String firstLowerCase(String str) {
		return StringUtils.lowerCase(StringUtils.substring(str, 0, 1)) + StringUtils.substring(str, 1);
	}

	/**
	 * 将驼峰分割的字符串转化为下滑线分割的字符串
	 * 
	 * @param str
	 * @return
	 */
	public static final String camelCase2Underline(String str) {
		return null;
	}
	
	/**
	 * 比较2个字符串在某个字符之前是否相等
	 * 
	 * @param str1
	 * @param str2
	 * @param c
	 * @return
	 */
	public static final boolean isEqualBeforeChar(String str1, String str2, char c) {
        char ta[] = str1.toCharArray();
        int to = 0;
        int tc = ta.length;
        char pa[] = str2.toCharArray();
        int po = 0;
        int pc = pa.length;
        while (--pc>=0 || --tc>=0) {
        	if(ta[to]==c || pa[po]==c) {
        		break;
        	}
            if (ta[to++] != pa[po++]) {
                return false;
            }
        }
        return true;
    }
}
