package mail;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.roof.commons.Md5Generator;

public class TestMd5 {

	@Test
	public void test() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		String mail = "madfrogxin@163.com";
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] srcBytes = mail.getBytes();
		// 使用srcBytes更新摘要
		md5.update(srcBytes);
		// 完成哈希计算，得到result
		byte[] resultBytes = md5.digest();
		int j = resultBytes.length;
		char str[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = resultBytes[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
		System.out.println(ArrayUtils.toString(str));

		System.out.println(new Md5Generator().calcmd5(mail));
	}

}
