package org.roof.fileupload.impl;

import java.util.UUID;

import org.roof.fileupload.api.FileNameGenerator;

public class UuidFileNameGenerator implements FileNameGenerator {

	@Override
	public String getFileName() {
		return UUID.randomUUID().toString();
	}

}
