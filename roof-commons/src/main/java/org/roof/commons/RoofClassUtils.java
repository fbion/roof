package org.roof.commons;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Class 工具类<br />
 * 更多工具请使用 {@link ClassUtils}
 * 
 * @author liuxin 2011-9-15
 * @version 1.0 RoofClassUtils.java liuxin 2011-9-15
 */
public class RoofClassUtils {

	/***************************************************************************
	 * 将对象转换成Map *
	 * 
	 * @param obj
	 * @return
	 **************************************************************************/
	public static Map<String, Object> objectParamsToMap(Object obj) {
		return objectParamsToMap(obj, null);
	}

	/***************************************************************************
	 * 将对象实体类转换成KEY-VALUE存放到Map params *
	 * 
	 * @param obj
	 * @return
	 **************************************************************************/
	private static Map<String, Object> objectParamsToMap(Object obj, Map<String, Object> params) {
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
	private static String propertyToField(String property) {
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

	/**
	 * 获得当前调用方法的名称
	 * 
	 * @return
	 */
	public static String getCurrMethodName() {
		return getCurrMethodName(2);
	}

	/**
	 * 获得当前调用方法的名称
	 * 
	 * @param times
	 *            几次调用，就填多少数字
	 * @return
	 */
	public static String getCurrMethodName(int times) {
		StackTraceElement[] temp = Thread.currentThread().getStackTrace();
		StackTraceElement curr = (StackTraceElement) temp[times];
		return curr.getMethodName();
	}

	/**
	 * 将class文件读取到内存
	 * 
	 * @param dir
	 *            class文件 路径,不包括包
	 * @param classFullName
	 *            class类全名
	 * @return
	 * @throws MalformedURLException
	 * @throws ClassNotFoundException
	 */
	public static Class<?> loadClass(String dir, String classFullName)
			throws MalformedURLException, ClassNotFoundException {
		URL[] urls = new URL[] { new URL("file:/" + dir) };
		URLClassLoader classLoader = new URLClassLoader(urls);
		Class<?> clazz = classLoader.loadClass(classFullName);
		return clazz;
	}

	/**
	 * 将class文件读取到内存 使用默认路径(用户目录src下)
	 * 
	 * @param classFullName
	 *            class类全名
	 * @return
	 * @throws MalformedURLException
	 * @throws ClassNotFoundException
	 */
	public static Class<?> loadClass(String classFullName) throws MalformedURLException, ClassNotFoundException {
		String dir = System.getProperty("user.dir") + "/src/";
		return loadClass(dir, classFullName);
	}

	/**
	 * 编译Java类
	 * 
	 * @param files
	 * @throws IOException
	 */
	public static void compile(File... files) throws IOException {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null, null, null);
		Iterable<? extends JavaFileObject> units = fileMgr.getJavaFileObjects(files);
		CompilationTask task = compiler.getTask(null, fileMgr, null, null, null, units);
		task.call();
		fileMgr.close();
	}

}
