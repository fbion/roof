package org.roof.servicecheck;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.Assert;

public class FileConfigInterfaceScan {

	private static final Logger LOG = Logger.getLogger(FileConfigInterfaceScan.class);

	public static void main(String[] args) {
		Assert.notEmpty(args);
		String config_path = args[0];
		File dir = new File(config_path);
		File[] fs = dir.listFiles();
		for (File file : fs) {
			if (file.isFile()) {
				System.out.println(file.getName() + "=============================");
				InterfaceScan interfaceScan = new InterfaceScan(readConfig(file));
				try {
					interfaceScan.doScan();
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
				}
				System.out.println("=============================================");
			}
		}
	}

	private static Properties readConfig(File file) {
		Properties properties = null;
		try {
			properties = PropertiesLoaderUtils.loadProperties(new FileSystemResource(file));
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		return properties;
	}

}
