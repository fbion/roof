package org.roof.d2s.impl;

import org.roof.d2s.core.DynamicToStaticService;
import org.roof.fileupload.api.FileInfo;

/**
 * 动转静服务类实现
 * 
 * @author liuxin
 *
 */
public class SimpleDynamicToStaticService implements DynamicToStaticService {

	@Override
	public FileInfo save(String url) {
		return null;
	}

	@Override
	public byte[] getData(String url) {
		return null;
	}

	@Override
	public String getDataMD5(String url) {
		return null;
	}
	
	public String test(){
		return null;
	}

}
