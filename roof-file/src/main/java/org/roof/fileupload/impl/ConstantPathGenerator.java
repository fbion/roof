package org.roof.fileupload.impl;

import org.roof.fileupload.api.PathGenerator;

public class ConstantPathGenerator implements PathGenerator {
	private String path;

	@Override
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
