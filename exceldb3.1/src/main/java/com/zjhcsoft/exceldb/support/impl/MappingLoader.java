package com.zjhcsoft.exceldb.support.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.zjhcsoft.exceldb.entity.XslDb;
import com.zjhcsoft.xdm.XmlEncoder;

/**
 * 
 * @author liuxin 2011-4-12
 * 
 */
public class MappingLoader {
	private static Map<String, XslDb> MAPPINGS;

	public static XslDb getXslDb(String id) {
		if (MAPPINGS == null) {
			load();
		}
		return MAPPINGS.get(id);
	}

	public static void load() {
		MAPPINGS = new HashMap<String, XslDb>();

		InputStream in = null;
		SAXReader reader = new SAXReader();
		try {
			in = MappingLoader.class.getClassLoader().getResourceAsStream(
					"mapping.xml");
			Document document = reader.read(in);
			@SuppressWarnings("unchecked")
			List<Node> nodes = document.selectNodes("mapping/dbxsl");
			XmlEncoder xmlEncoder = new XmlEncoder();
			@SuppressWarnings("unchecked")
			List<XslDb> xslDbs = (List<XslDb>) xmlEncoder.encodeCollection(
					nodes, List.class, XslDb.class);
			for (XslDb xslDb : xslDbs) {
				MAPPINGS.put(xslDb.getId(), xslDb);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
