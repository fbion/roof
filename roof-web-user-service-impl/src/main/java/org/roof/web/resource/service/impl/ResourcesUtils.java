package org.roof.web.resource.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.roof.roof.dataaccess.api.DaoException;
import org.roof.web.resource.dao.api.IResourceDao;
import org.roof.web.resource.entity.Module;
import org.roof.web.resource.entity.Privilege;
import org.roof.web.resource.entity.Resource;
import org.roof.web.resource.service.api.IResourcesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 资源表导入导出
 * 
 * @author liuxin
 * 
 */
@Service
public class ResourcesUtils implements IResourcesUtils {
	private IResourceDao resourceDao;
	private static Logger logger = Logger.getLogger(ResourcesUtils.class);

	private static final String[] INIT_BASIC_RESOURCES = new String[] {
			"Privilege|#name#列表|/**#path#/list*|#name#列表|list|#path#/list|1|2|#path#|true||html",
			"Privilege|#name#详情|/**#path#/detail*||detail|#path#/detail|5|2|#path#|true||html",
			"Privilege|#name#修改|/**#path#/update*||update|#path#/update|3|2|#path#|false||json",
			"Privilege|#name#修改页面|/**#path#/update_page*||page|#path#/update_page||3|#path#/update|true||html ",
			"Privilege|#name#新增|/**#path#/create*||create|#path#/create|2|2|#path#|false||json",
			"Privilege|#name#新增页面|/**#path#/create_page*||page|#path#/create_page||3|#path#/create|true||html ",
			"Privilege|#name#删除|/**#path#/delete*||delete|#path#/delete|4|2|#path#|true||json" };

	@Override
	public void initBasicOperation(Module module) {
		try {
			for (int i = 0; i < INIT_BASIC_RESOURCES.length; i++) {
				String s = INIT_BASIC_RESOURCES[i];
				s = StringUtils.replace(s, "#name#", module.getName());
				s = StringUtils.replace(s, "#path#", module.getPath());
				Resource res = deseqResource(s);
				if (res != null) {
					resourceDao.save(res);
				}
			}
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 导出资源表到文件
	 * 
	 * @param file
	 * @throws DaoException
	 */
	@Override
	public void exportToFile(File file) throws DaoException {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(file);
			Module root = findRoot();
			seq(root, fileWriter);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			try {
				fileWriter.close();
			} catch (IOException e1) {
				logger.error(e1);
			}
		}
		return;
	}

	private void seq(Module module, FileWriter fileWriter) throws IOException {
		List<Module> ms = findChildren(module.getId());
		if (CollectionUtils.isNotEmpty(ms)) {
			for (Module m : ms) {
				fileWriter.write(seqModule(m) + "\n");
				seq(m, fileWriter);
			}
		}
	}

	@Override
	public List<Module> findChildren(Long pid) {
		Privilege privilege = new Privilege();
		Privilege parent = new Privilege();
		parent.setId(pid);
		privilege.setParent(parent);
		@SuppressWarnings("unchecked")
		List<Module> result = (List<Module>) resourceDao.selectForList("org.roof.web.resource.dao.selectPrivilege",
				privilege);
		return result;
	}

	private String seqModule(Module o) {
		String s = StringUtils.EMPTY;
		s = s + getPorp(o, "name") + "|" + getPorp(o, "pattern") + "|" + getPorp(o, "description") + "|"
				+ getPorp(o, "identify") + "|" + getPorp(o, "path") + "|" + getPorp(o, "seq") + "|" + getPorp(o, "lvl")
				+ "|" + (StringUtils.isBlank(getPorp(o, "parent")) ? "" : getPorp(o, "parent.path")) + "|"
				+ getPorp(o, "leaf");
		if (o instanceof Privilege) {
			s = "Privilege|" + s + seqPrivilege((Privilege) o);
		} else if (o instanceof Module) {
			s = "Module|" + s;
		}
		return s;
	}

	private String seqPrivilege(Privilege o) {
		String s = "|" + getPorp(o, "remark") + "|" + getPorp(o, "format");
		return s;
	}

	private String getPorp(Object o, String porpName) {
		try {
			return ObjectUtils.toString(PropertyUtils.getProperty(o, porpName), "");
		} catch (IllegalAccessException e) {
			logger.error(e);
		} catch (InvocationTargetException e) {
			logger.error(e);
		} catch (NoSuchMethodException e) {
			logger.error(e);
		}
		return "";
	}

	private void setProp(Object o, String porpName, Object value) {
		try {
			PropertyUtils.setProperty(o, porpName, value);
		} catch (IllegalAccessException e) {
			logger.error(e);
		} catch (InvocationTargetException e) {
			logger.error(e);
		} catch (NoSuchMethodException e) {
			logger.error(e);
		}
	}

	private Module findRoot() throws DaoException {
		return (Module) resourceDao.selectForObject("org.roof.web.resource.dao.findRootModule");
	}

	/**
	 * 从文件导入资源
	 * 
	 * @param file
	 * @throws DaoException
	 */
	@Override
	public void importFromFile(File file) throws DaoException {
		FileReader in = null;
		BufferedReader br = null;
		try {
			in = new FileReader(file);
			br = new BufferedReader(in);
			String s = null;
			while ((s = br.readLine()) != null) {
				Resource res = deseqResource(s);
				if (res != null) {
					resourceDao.save(res);
				}
			}
		} catch (FileNotFoundException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			try {
				in.close();
				br.close();
			} catch (IOException e) {
				logger.error(e);
			}
		}
		return;
	}

	private Resource deseqResource(String s) throws DaoException {
		String[] vals = StringUtils.splitByWholeSeparatorPreserveAllTokens(s, "|");
		String path = vals[5];
		String type = vals[0];
		if (resourceExists(path)) {
			return null;
		}
		Resource resource = null;
		if (StringUtils.equals(type, "Module")) {
			resource = new Module();
		}
		if (StringUtils.equals(type, "Privilege")) {
			resource = new Privilege();
		}
		resource.setDtype(type);
		deseqModule(resource, vals);
		return resource;
	}

	private void deseqModule(Resource o, String[] vals) throws DaoException {
		setProp(o, "name", vals[1]);
		setProp(o, "pattern", vals[2]);
		setProp(o, "description", vals[3]);
		setProp(o, "identify", vals[4]);
		setProp(o, "path", vals[5]);
		setProp(o, "seq", NumberUtils.createInteger(StringUtils.isBlank(vals[6]) ? null : vals[6]));
		setProp(o, "lvl", NumberUtils.createInteger(StringUtils.isBlank(vals[7]) ? null : vals[7]));
		Module parent = findModuleByPath(vals[8]);
		setProp(o, "parent", parent);
		setProp(o, "leaf", BooleanUtils.toBoolean(vals[9]));
		if (o instanceof Privilege) {
			deseqPrivilege(o, vals);
		}
	}

	private void deseqPrivilege(Resource o, String[] vals) {
		setProp(o, "remark", vals[10]);
		setProp(o, "format", vals[11]);
	}

	@Override
	public boolean resourceExists(String path) throws DaoException {
		if (findModuleByPath(path) != null) {
			return true;
		}
		return false;
	}

	private Module findModuleByPath(String path) throws DaoException {
		Module module = new Module();
		module.setPath(path);
		Module m = (Module) resourceDao.selectForObject("org.roof.web.resource.dao.selectModule", module);
		return m;
	}

	@Autowired
	public void setResourceDao(@Qualifier("resourceDao") IResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}
}
