package org.roof.d2s.core;

import org.roof.fileupload.api.FileInfo;

/**
 * 动转静服务类
 * 
 * @author liuxin
 *
 */
public interface DynamicToStaticService {
	/**
	 * 将url地址的html保存为文件
	 *
	 * 
	 * @param url
	 *            地址
	 * @return
	 */
	FileInfo save(String url);

	/**
	 * 获取指定url的html页面,以字节数组的方式返回
	 * 
	 * @param url
	 *            地址
	 * @return
	 */
	byte[] getData(String url);

	/**
	 * 获取指定地址html的MD5值
	 * 
	 * @param url
	 *            地址
	 * @return
	 */
	String getDataMD5(String url);

}
