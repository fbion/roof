package org.roof.web.resource.service.api;

import java.io.File;
import java.util.List;

import org.roof.roof.dataaccess.api.DaoException;
import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.web.resource.entity.Module;

public interface IResourcesUtils {

	public abstract void initBasicOperation(Module module);

	/**
	 * 导出资源表到文件
	 * 
	 * @param file
	 * @throws DaoException
	 */
	public abstract void exportToFile(File file) throws DaoException;

	public abstract List<Module> findChildren(Long pid);

	/**
	 * 从文件导入资源
	 * 
	 * @param file
	 * @throws DaoException
	 */
	public abstract void importFromFile(File file) throws DaoException;

	public abstract boolean resourceExists(String path) throws DaoException;

}