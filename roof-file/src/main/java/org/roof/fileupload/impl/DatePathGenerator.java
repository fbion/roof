package org.roof.fileupload.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.roof.fileupload.api.PathGenerator;

/**
 * 按照日期生成文件存放路径
 * <p>
 * 按照小时生成文件夹:yyyy/MM/dd/HH
 * </p>
 * <p>
 * 按照日生成文件夹:yyyy/MM/dd
 * </p>
 * <p>
 * 按照月生成文件夹:yyyy/MM
 * </p>
 * 
 * 
 * @author liuxin
 *
 */
public class DatePathGenerator implements PathGenerator {

	private String parsePattern = "yyyy/MM/dd";
	private SimpleDateFormat sdf = new SimpleDateFormat(parsePattern);

	@Override
	public String getPath() {
		return sdf.format(new Date());
	}

}
