package org.roof.creator;

import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.roof.creator.bean.Domain;
import org.roof.creator.bean.Relation;
import org.roof.dataaccess.RoofDaoSupport;
import org.roof.roof.dataaccess.api.IDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import freemarker.template.Configuration;
import freemarker.template.Template;

//@Service
public class AutoCreateUtils {

	private IDaoSupport daoSupport;

	private Configuration cfg;

	private String encode = "UTF-8";// 生成的默认编码

	private String packagePath = "";// 包目录，到该模块的根目录

	private String sysName = "";// 生成的该系统名称，用于存放WEB路径

	private String actionName = "";// Action 名称

	private String exportPrefix = "";// 文件输出目录

	private Domain mainDomain = null;

	private String currTableName = "";

	private String userDir = System.getProperty("user.dir");// 设置工程的路径

	private String templatePrefix = "E:/work34/core/roof-creator/src/main/java/org/roof/creator/template/";// 文件模版目录

	private String[] typeArr = { "create", "update", "detail" };

	/**
	 * 生成默认的全部模版文件
	 */
	public void createCode() {
		String packagePath = "com.zjhcsoft.evaluation.masterplate";

		List<String> sourcelist = new ArrayList<String>();
		sourcelist.add("E_MASTERPLATE");// 添加需要生成的表名

		this.createCode(packagePath, sourcelist);
	}

	/**
	 * 生成全部的模版文件<br/>
	 * // 实体类名中表名需要添加前缀，用下划线_分割<br/>
	 * // 实体类里面，属性名和列名相同，如果是嵌套对象，用骆驼命名法<br/>
	 * // private Staff owerStaff;// 计划填写人@OWER_STAFF_ID<br/>
	 * // private Date create_date;// 创建时间<br/>
	 * // 文件注释要在同一行里面，只包含一个空格
	 * 
	 * @param packagePath
	 * @param sourcelist
	 */
	public void createCode(String packagePath, List<String> sourcelist) {
		this.createCode(this.getUserDir(), packagePath, sourcelist);
	}

	/**
	 * 生成全部的模版文件<br/>
	 * // 实体类名中表名需要添加前缀，用下划线_分割<br/>
	 * // 实体类里面，属性名和列名相同，如果是嵌套对象，用骆驼命名法<br/>
	 * // private Staff owerStaff;// 计划填写人@OWER_STAFF_ID<br/>
	 * // private Date create_date;// 创建时间<br/>
	 * // 文件注释要在同一行里面，只包含一个空格
	 * 
	 * @param packagePath
	 * @param sourcelist
	 */
	public void createCode(String userDir, String packagePath, List<String> sourcelist) {
		this.setUserDir(userDir);// 设置工程的路径
		this.setPackagePath(packagePath);// 设置生成代码的包目录
		String[] arr = this.getPackagePath().split("\\.");
		this.setSysName(arr[2]);// 设置WEB存放的文件夹名称
		if ("".equals(this.getActionName())) {
			String tempActionName = /* arr[arr.length - 3] + "_" + */arr[arr.length - 2] + "_" + arr[arr.length - 1];
			this.setActionName(tempActionName);
		}

		List<String> errList = new ArrayList<String>();

		String str = "";
		Iterator iter = sourcelist.iterator();
		for (; iter.hasNext();) {
			String tableName = (String) iter.next();
			this.setExportPrefix("E:/E/temp/" + this.getAliasTable(tableName).toLowerCase() + "/");
			try {
				// str = autoUtils.createPoFromTable(tableName);// X
				// str = autoUtils.createVoFromTable(tableName);// X

				str = this.createActionFromTable(tableName);
				str = this.createDaoFromTable(tableName);
				str = this.createXmlFromTable(tableName);
				str = this.createServiceFromTable(tableName);

				str = this.createPageListFromTable(tableName);
				// str = this.createJsListFromTable(tableName);
				str = this.createPageFormFromTable(tableName);
				System.out.println("\nOK ==============" + str);
			} catch (Exception e) {
				errList.add(tableName);
				e.printStackTrace();
			}
		}

		iter = sourcelist.iterator();
		for (; iter.hasNext();) {
			String tableName = (String) iter.next();
			System.out.println("CREATE SEQUENCE SEQ_" + tableName + ";");
		}
		System.err.println("errList = " + "[" + errList.size() + "]" + errList);
	}

	/**
	 * 初始化
	 */
	private void init() {
		cfg = new Configuration();// 创建一个Configuration实例
		cfg.setDefaultEncoding(this.getEncode());// 此处解决乱码
	}

	/**
	 * 将传入的参数，通过模板生成内容
	 * 
	 * @param templateName
	 * @param root
	 * @return
	 * @throws Exception
	 */
	public String process(String templatePath, Map<String, Object> root) throws Exception {
		Template template = null;
		StringWriter writer = new StringWriter();
		try {
			if (cfg == null) {
				init();
			}
			templatePath = templatePath.replaceAll("\\\\", "/");
			String templateName = templatePath.substring(templatePath.lastIndexOf("/"));
			String directoryPath = templatePath.substring(0, templatePath.lastIndexOf("/"));
			cfg.setDirectoryForTemplateLoading(new File(directoryPath));
			template = cfg.getTemplate(templateName);
			template.setEncoding(this.getEncode());
			try {
				template.process(root, writer);
				writer.flush();
			} finally {
				writer.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				writer.flush();
				e.printStackTrace();
			}
		}
		return writer.toString();
	}

	/**
	 * 将内容写入文件
	 * 
	 * @param templatePath
	 * @param root
	 * @param exportPath
	 * @return
	 * @throws Exception
	 */
	public String processWrite(String templatePath, Map<String, Object> root, String exportPath) throws Exception {
		String success = "";
		String result = process(templatePath, root);
		exportPath = exportPath.replaceAll("\\\\", "/");
		File writeFile = new File(exportPath.substring(0, exportPath.lastIndexOf("/")));

		// 如果目录不存在,则创建目录;
		if (!writeFile.exists()) {
			// mkdirs是创建多级目录,而mkdir是创建单级目录;
			writeFile.mkdirs();
		}
		Writer fileWrite = null;
		try {
			fileWrite = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(exportPath), this.getEncode()));
			fileWrite.write(result);
			success = exportPath;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (fileWrite != null) {
					fileWrite.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return success;
	}

	/**
	 * 从表名生成实体类
	 * 
	 * @param tableName
	 *            表名
	 * @throws Exception
	 */
	public String createPoFromTable(String tableName) throws Exception {
		Domain domain = this.fillDomain(tableName);
		Map<String, Object> root = this.objectParamsToMap(domain);
		String path = this.getExportPrefix() + CreatorConstants.PACKAGE_ENTITY + "/"
				+ WordUtils.capitalize(domain.getAlias()) + ".java";

		String flag = this.processWrite(this.getTemplatePrefix() + "poTemplate.ftl", root, path);
		if (StringUtils.isNotEmpty(flag)) {
			return "文件生成到 " + path;
		} else {
			return "文件生成失败！";
		}
	}

	/**
	 * 从表名生成VO类
	 * 
	 * @param tableName
	 *            表名
	 * @throws Exception
	 */
	public String createVoFromTable(String tableName) throws Exception {
		Domain domain = this.fillDomain(tableName);
		Map<String, Object> root = this.objectParamsToMap(domain);
		String path = this.getExportPrefix() + CreatorConstants.PACKAGE_VO + "/"
				+ WordUtils.capitalize(domain.getAlias()) + "Vo.java";

		String flag = this.processWrite(this.getTemplatePrefix() + "voTemplate.ftl", root, path);
		if (StringUtils.isNotEmpty(flag)) {
			return "文件生成到 " + path;
		} else {
			return "文件生成失败！";
		}
	}

	/**
	 * 从表名生成页面JS
	 * 
	 * @param tableName
	 *            表名
	 * @throws Exception
	 */
	public String createJsListFromTable(String tableName) throws Exception {
		String t = "jsTemplateList.ftl";
		Domain domain = this.fillDomain(tableName);

		Map<String, Object> root = this.objectParamsToMap(domain);

		String path = this.getExportPrefix() + domain.getAlias() + "/js/" + (domain.getAlias()) + "_index.js";
		String flag = this.processWrite(this.getTemplatePrefix() + t, root, path);
		if (StringUtils.isNotEmpty(flag)) {
			return "文件生成到 " + path;
		} else {
			return "文件生成失败！";
		}
	}

	/**
	 * 从表名生成句柄XML
	 * 
	 * @param tableName
	 *            表名
	 * @throws Exception
	 */
	public String createXmlFromTable(String tableName) throws Exception {
		Domain domain = this.fillDomain(tableName);

		Map<String, Object> root = this.objectParamsToMap(domain);

		String path = this.getExportPrefix() + CreatorConstants.PACKAGE_DAO + "/"
				+ WordUtils.capitalize(domain.getAlias()) + "_exp.xml";
		String flag = this.processWrite(this.getTemplatePrefix() + "xmlTemplate.ftl", root, path);
		if (StringUtils.isNotEmpty(flag)) {
			return "文件生成到 " + path;
		} else {
			return "文件生成失败！";
		}
	}

	/**
	 * 从表名生成列表页面
	 * 
	 * @param tableName
	 *            表名
	 * @throws Exception
	 */
	public String createPageListFromTable(String tableName) throws Exception {
		Domain domain = this.fillDomain(tableName);

		Map<String, Object> root = this.objectParamsToMap(domain);
		root.put("includeBase", "${basePath}");
		root.put("el$", "$");

		String path = this.getExportPrefix() + domain.getAlias() + "/" + (domain.getAlias()) + "_list.jsp";
		String flag = this.processWrite(this.getTemplatePrefix() + "pageTemplateList.ftl", root, path);
		if (StringUtils.isNotEmpty(flag)) {
			return "文件生成到 " + path;
		} else {
			return "文件生成失败！";
		}
	}

	/**
	 * 从表名生成表单页面
	 * 
	 * @param tableName
	 *            表名
	 * @throws Exception
	 */
	public String createPageFormFromTable(String tableName) throws Exception {
		Domain domain = this.fillDomain(tableName);
		String path = "";
		String flag = "";

		Map<String, Object> root = this.objectParamsToMap(domain);
		root.put("includeBase", "${basePath}");
		root.put("el$", "$");
		// // 新增,修改,查看页面分离
		// for (int i = 0; i < typeArr.length; i++) {
		// root.put("type", typeArr[i]);
		// path = this.getExportPrefix() + domain.getAlias() + "/" +
		// (domain.getAlias()) + "_" + typeArr[i] + ".jsp";
		// flag = this.processWrite(this.getTemplatePrefix() +
		// "pageTemplateForm.ftl", root, path);
		// }
		// 新增,修改,查看页面中表单部分复用
		typeArr = new String[] { "Create", "Update", "Form" };
		for (int i = 0; i < typeArr.length; i++) {
			root.put("type", typeArr[i]);
			path = this.getExportPrefix() + domain.getAlias() + "/" + (domain.getAlias()) + "_"
					+ typeArr[i].toLowerCase() + ".jsp";
			flag = this.processWrite(this.getTemplatePrefix() + "pageTemplate" + typeArr[i] + ".ftl", root, path);
		}
		if (StringUtils.isNotEmpty(flag)) {
			return "文件生成到 " + path;
		} else {
			return "文件生成失败！";
		}
	}

	/**
	 * 从表名生成Action类
	 * 
	 * @param tableName
	 *            表名
	 * @throws Exception
	 */
	public String createActionFromTable(String tableName) throws Exception {
		Domain domain = this.fillDomain(tableName);

		Map<String, Object> root = this.objectParamsToMap(domain);

		String path = this.getExportPrefix() + CreatorConstants.PACKAGE_ACTION + "/"
				+ WordUtils.capitalize(domain.getAlias()) + "Action.java";
		String flag = this.processWrite(this.getTemplatePrefix() + "actionTemplate.ftl", root, path);
		if (StringUtils.isNotEmpty(flag)) {
			return "文件生成到 " + path;
		} else {
			return "文件生成失败！";
		}
	}

	/**
	 * 从表名生成Dao类
	 * 
	 * @param tableName
	 *            表名
	 * @throws Exception
	 */
	public String createDaoFromTable(String tableName) throws Exception {
		Domain domain = this.fillDomain(tableName);
		Map<String, Object> root = this.objectParamsToMap(domain);

		String path = this.getExportPrefix() + CreatorConstants.PACKAGE_DAO + "/"
				+ WordUtils.capitalize(domain.getAlias()) + "Dao.java";
		String flag = this.processWrite(this.getTemplatePrefix() + "daoTemplate.ftl", root, path);
		if (StringUtils.isNotEmpty(flag)) {
			return "文件生成到 " + path;
		} else {
			return "文件生成失败！";
		}
	}

	/**
	 * 从表名生成Service类
	 * 
	 * @param tableName
	 *            表名
	 * @throws Exception
	 */
	public String createServiceFromTable(String tableName) throws Exception {
		Domain domain = this.fillDomain(tableName);

		Map<String, Object> root = this.objectParamsToMap(domain);

		String path = this.getExportPrefix() + CreatorConstants.PACKAGE_SERVICE + "/"
				+ WordUtils.capitalize(domain.getAlias()) + "Service.java";
		String flag = this.processWrite(this.getTemplatePrefix() + "serviceTemplate.ftl", root, path);
		if (StringUtils.isNotEmpty(flag)) {
			return "文件生成到 " + path;
		} else {
			return "文件生成失败！";
		}
	}

	/**
	 * 生成数据库的列注释
	 * 
	 * @param tableName
	 * @throws Exception
	 * 
	 *             private User localeUser; // 被检查人
	 * @LOCALE_USER_ID private String remarks; // 问题描述
	 */
	private void addComment(String tableName) {
		try {

			List<String> result = new ArrayList<String>();
			String filename = this.getUserDir() + "/src/main/java/" + this.getPackagePath().replace(".", "/") + "/"
					+ CreatorConstants.PACKAGE_ENTITY + "/" + WordUtils.capitalize(this.getAliasTable(tableName))
					+ ".java";
			InputStreamReader in = new InputStreamReader(new FileInputStream(filename), "UTF-8");
			BufferedReader br = new BufferedReader(in);
			String s = null;
			String split = "//";
			String flag = "@";
			while ((s = br.readLine()) != null) {
				if (s.contains("List")) {// 列表忽略
					continue;
				}
				if (s.contains(split)) {// s = private User localeUser; //
										// 被检查人@LOCALE_USER_ID
					if (s.trim().startsWith(split)) {// 该行被注释
						continue;
					}
					if (!s.contains(";")) {
						continue;
					}
					String temp = s.substring(0, s.indexOf(";")).trim();
					if (s.contains("=")) {// s = private boolean flag = true; //
											// 包含初始值
						temp = s.substring(0, s.indexOf("=")).trim();
					}
					String colName = temp.substring(temp.lastIndexOf(" ")).trim();
					if (s.contains(flag)) {
						colName = s.substring(s.indexOf(flag) + flag.length()).trim();
						s = s.replace(flag, "").replace(colName, "");
						if (StringUtils.isEmpty(colName)) {
							continue;
						}
					}
					String sql = "comment on column " + tableName + "." + colName + " is '"
							+ s.substring(s.indexOf(split) + split.length()).trim() + "'";
					result.add(sql);
				}
			}
			br.close();
			in.close();

			Iterator iter = result.iterator();
			for (; iter.hasNext();) {
				((RoofDaoSupport) daoSupport).getSqlSessionTemplate().selectList("tool_auto_exp_execute_statement",
						iter.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 填充 Domain 里的数据
	 * 
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	private Domain fillDomain(String tableName) throws Exception {
		if (!StringUtils.equalsIgnoreCase(this.getCurrTableName(), tableName)) {
			this.setCurrTableName(tableName);
			this.setMainDomain(null);
		}
		if (this.getMainDomain() == null) {
			this.addComment(tableName);// 生成数据库的列注释

			Domain dataDomain = new Domain();
			dataDomain.setSysName(this.getSysName());
			dataDomain.setActionName(this.getActionName());
			dataDomain.setTableName(tableName.toLowerCase());
			dataDomain.setAlias(this.getAliasTable(tableName));
			dataDomain.setPackagePath(this.getPackagePath());
			dataDomain.setPackageArr(Arrays.asList(this.getPackagePath().split("\\.")));
			dataDomain.setPrimaryKey(this.findPrimaryKeyFromTable(dataDomain.getTableName()));
			dataDomain.setFields(this.findColumnsFromTable(dataDomain.getTableName()));
			dataDomain.setTableDisplay(this.findTableDisplayFromTable(dataDomain.getTableName()));
			dataDomain.setRelations(this.findRelationFromTable(dataDomain.getTableName()));
			String[] arr = this.getActionName().split("_");
			String fileDir = "";
			for (int i = 0; i < arr.length; i++) {
				fileDir += "/" + arr[i];
			}
			dataDomain.setFileDir(fileDir);
			this.setMainDomain(dataDomain);
		}
		return this.getMainDomain();
	}

	/**
	 * 得到表的别名
	 * 
	 * @param tableName
	 * @return
	 */
	public String getAliasTable(String tableName) {
		String[] arr = tableName.split("_");
		StringBuffer result = new StringBuffer();
		for (int i = 1; i < arr.length; i++) {// 去掉表名的前缀
			String temp = arr[i].toLowerCase();
			if (i != 1) {
				result.append(WordUtils.capitalize(temp));
			} else {
				result.append(temp);
			}
		}
		return result.toString();
	}

	/**
	 * 得到列的别名
	 * 
	 * @param colName
	 * @return
	 */
	private String getAliasCol(String colName) {
		String[] arr = colName.split("_");
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < arr.length - 1; i++) {// 去掉列名的后缀
			String temp = arr[i].toLowerCase();
			if (i != 0) {
				result.append(WordUtils.capitalize(temp));
			} else {
				result.append(temp);
			}
		}
		return result.toString();
	}

	/**
	 * 从表名获得属性列表
	 * 
	 * @param tableName
	 *            表名
	 */
	private List findColumnsFromTable(String tableName) {
		return ((RoofDaoSupport) daoSupport).getSqlSessionTemplate().selectList(
				"tool_auto_exp_find_columns_from_table", tableName);
	}

	/**
	 * 从表名获得主键
	 * 
	 * @param tableName
	 *            表名
	 */
	private List findPrimaryKeyFromTable(String tableName) {
		return ((RoofDaoSupport) daoSupport).getSqlSessionTemplate().selectList(
				"tool_auto_exp_find_primarykey_from_table", tableName);
	}

	/**
	 * 从表名获得主外键关系
	 * 
	 * @param tableName
	 *            表名
	 */
	private List findRelationFromTable(String tableName) {
		Map<String, String> args = new HashMap<String, String>();
		args.put("tableName", tableName);
		List source = ((RoofDaoSupport) daoSupport).getSqlSessionTemplate().selectList(
				"tool_auto_exp_find_relation_from_table", args);
		List<Relation> result = new ArrayList<Relation>();

		Iterator iter = source.iterator();
		for (; iter.hasNext();) {
			Relation relation = (Relation) iter.next();
			relation.setAlias(this.getAliasCol(relation.getForeignCol()));
			result.add(relation);
		}
		return result;
	}

	/**
	 * 从表名获得表的描述
	 * 
	 * @param tableName
	 *            表名
	 */
	private String findTableDisplayFromTable(String tableName) {
		Object obj = ((RoofDaoSupport) daoSupport).getSqlSessionTemplate().selectOne(
				"tool_auto_exp_find_table_display_from_table", tableName);
		if (obj == null) {
			return "";
		}
		return obj.toString();
	}

	/***************************************************************************
	 * 将对象转换成Map *
	 * 
	 * @param obj
	 * @return
	 **************************************************************************/
	public Map<String, Object> objectParamsToMap(Object obj) {
		return objectParamsToMap(obj, null);
	}

	/***************************************************************************
	 * 将对象实体类转换成KEY-VALUE存放到Map params *
	 * 
	 * @param obj
	 * @return
	 **************************************************************************/
	private Map<String, Object> objectParamsToMap(Object obj, Map<String, Object> params) {
		if (params == null) {
			params = new HashMap<String, Object>(0);
		}
		if (obj == null) {
			return params;
		}
		try {
			PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
			PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				if (!StringUtils.equals(name, "class")) {
					Object tempVal = propertyUtilsBean.getNestedProperty(obj, name);
					if (tempVal == null || "".equals(tempVal)) {
						continue;
					}
					params.put(propertyToField(name), tempVal);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}

	/***************************************************************************
	 * 对象属性转换为字段
	 * 
	 * @param property
	 **************************************************************************/
	private String propertyToField(String property) {
		if (null == property) {
			return "";
		}
		char[] chars = property.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (char c : chars) {
			sb.append(c);
		}
		return sb.toString();
	}

	public Configuration getCfg() {
		return cfg;
	}

	public void setCfg(Configuration cfg) {
		this.cfg = cfg;
	}

	public String getCurrTableName() {
		return currTableName;
	}

	public void setCurrTableName(String currTableName) {
		this.currTableName = currTableName;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public Domain getMainDomain() {
		return mainDomain;
	}

	public void setMainDomain(Domain mainDomain) {
		this.mainDomain = mainDomain;
	}

	public String getTemplatePrefix() {
		return templatePrefix;
	}

	public void setTemplatePrefix(String templatePrefix) {
		this.templatePrefix = templatePrefix;
	}

	public void setExportPrefix(String exportPrefix) {
		this.exportPrefix = exportPrefix;
	}

	public String getExportPrefix() {
		return exportPrefix;
	}

	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}

	public String getPackagePath() {
		return packagePath;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getUserDir() {
		return userDir;
	}

	public void setUserDir(String userDir) {
		this.userDir = userDir;
	}

	@Autowired(required = true)
	public void setDaoSupport(@Qualifier("roofDaoSupport") IDaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}

}
