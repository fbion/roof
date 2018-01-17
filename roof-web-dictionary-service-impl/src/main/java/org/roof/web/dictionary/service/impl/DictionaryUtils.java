package org.roof.web.dictionary.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.web.dictionary.dao.api.IDictionaryDao;
import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.dictionary.service.api.IDictionaryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author liuxin
 *
 */
@Component
public class DictionaryUtils implements IDictionaryUtils {
	private static final String S_DIC = "S_DIC";

	private IDictionaryDao dictionaryDao;
	private IDaoSupport daoSupport;

	private static final String SEPARATORS[] = new String[] { " ", "\t" };

	private static final Logger LOGGER = Logger
			.getLogger(DictionaryUtils.class);

	@Override
	public void importFromFile(File file) {
		String s = null;
		String parent = null;
		FileReader fr = null;
		BufferedReader br = null;
		parent = S_DIC;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			while ((s = br.readLine()) != null) {
				if (StringUtils.isEmpty(StringUtils.trimToNull(s))
						|| StringUtils.equals(StringUtils.trimToNull(s), "\t")) {
					parent = S_DIC;
					continue;
				}
				if (StringUtils.equals(parent, S_DIC)) {
					parent = createDictionary(s, parent);
				} else {
					createDictionary(s, parent);
				}
			}
		} catch (FileNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			try {
				if (fr != null) {
					fr.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}

	}

	private String createDictionary(String txt, String parent) {
		String[] ss = null;
		Dictionary result = null;
		txt = StringUtils.trim(txt);
		for (String separator : SEPARATORS) {
			ss = split(txt, separator);
			if (ss != null) {
				result = new Dictionary();
				result.setType(parent);
				result.setText(StringUtils.trim(ss[0]));
				result.setVal(StringUtils.trim(ss[1]));
				result.setActive("1");
				daoSupport.save(result);
				return result.getVal();
			}
		}
		return S_DIC;
	}

	private String[] split(String txt, String separator) {
		if (StringUtils.indexOf(txt, separator) != -1) {
			return StringUtils.split(txt, separator, 2);
		}
		return null;
	}

	public IDictionaryDao getDictionaryDao() {
		return dictionaryDao;
	}

	public IDaoSupport getDaoSupport() {
		return daoSupport;
	}

	@Autowired(required = true)
	public void setDictionaryDao(
			@Qualifier("dictionaryDao") IDictionaryDao dictionaryDao) {
		this.dictionaryDao = dictionaryDao;
	}

	@Autowired(required = true)
	public void setDaoSupport(
			@Qualifier("roofDaoSupport") IDaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}

}
