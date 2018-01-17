package org.roof.servicecheck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.roof.commons.RoofStringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class InterfaceScan {
	private static String mavenSrcPath = "src\\main\\java\\";
	private static Map<String, String> interfaceNames = new HashMap<>();

	// private static String tempPath = "hsf-ref-temp.xml";

	// cc服务
	private String version = null;
	private String group = null;
	private String tempPath = null;
	private String xmlFilePath = null;
	private String filePath = null;

	// mac引用
	// private static String group = "mac";
	// private static String tempPath = "hsf-ref-temp.xml";
	// private static String xmlFilePath =
	// "D:\\Workspaces\\space-sinopec\\sinopec-webapp\\src\\main\\resources\\mac\\hsf\\mac-ref-hsf.xml";
	// private static String filePath =
	// "D:\\Workspaces\\space-sinopec\\sinopec-mac-service-api\\src\\main\\java\\com\\sinopec\\crm\\mac";

	public InterfaceScan(String version, String group, String tempPath, String xmlFilePath, String filePath) {
		super();
		this.version = version;
		this.group = group;
		this.tempPath = tempPath;
		this.xmlFilePath = xmlFilePath;
		this.filePath = filePath;
	}

	public InterfaceScan(Properties properties) {
		this(properties.getProperty("version"), properties.getProperty("group"), properties.getProperty("tempPath"),
				properties.getProperty("xmlFilePath"), properties.getProperty("filePath"));
	}

	public void doScan() throws Exception {
		File root = new File(filePath);
		showAllFiles(root);
		f();
	}

	final void showAllFiles(File dir) throws Exception {
		File[] fs = dir.listFiles();
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].isFile()) {
				String name = fs[i].getName();
				if (isServiceInterface(name)) {
					addToIntfaceNames(name, fs[i].getAbsolutePath());
				}
			}
			if (fs[i].isDirectory()) {
				try {
					showAllFiles(fs[i]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void addToIntfaceNames(String name, String path) {
		String p = cutPackageName(path);
		p = StringUtils.replace(p, ".java", "");
		p = StringUtils.replace(p, File.separator, ".");
		if (StringUtils.startsWith(name, "I")) {
			name = StringUtils.replaceOnce(name, "I", "");
		}
		name = RoofStringUtils.firstLowerCase(StringUtils.replace(name, ".java", ""));
		interfaceNames.put(name, p);
	}

	// 是否服务interface
	private boolean isServiceInterface(String name) {
		if (StringUtils.startsWith(name, "I") && StringUtils.endsWith(name, "Service.java")) {
			return true;
		}
		return false;
	}

	// 通过路径截取包名+类名
	private String cutPackageName(String path) {
		int index = StringUtils.lastIndexOf(path, mavenSrcPath);
		return StringUtils.substring(path, index + mavenSrcPath.length());
	}

	private void f() {
		String text = loadFile(new FileSystemResource(xmlFilePath));
		String hsfServiceTemp = loadFile(new ClassPathResource("org/roof/servicecheck" + "/" + tempPath));
		printConfig(text, hsfServiceTemp);
	}

	private void printConfig(String text, String temp) {
		for (Entry<String, String> interfaceName : interfaceNames.entrySet()) {
			if (!StringUtils.contains(text, interfaceName.getValue())) {
				String hsfServiceStr = StringUtils.replaceEach(temp,
						new String[] { "#{target}", "#{interfaceName}", "#{version}", "#{group}" },
						new String[] { interfaceName.getKey(), interfaceName.getValue(), version, group });
				System.out.println(hsfServiceStr);
			}
		}
	}

	private String loadFile(Resource resource) {
		StringBuffer sb = new StringBuffer();
		File file = null;
		try {
			file = resource.getFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try (BufferedReader br = new BufferedReader(new FileReader(file));) {
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
