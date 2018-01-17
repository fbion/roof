package org.roof.commons;

import java.lang.reflect.Method;
import java.util.Date;

import org.apache.commons.lang3.ClassUtils;

/**
 * bean 工具类
 * 
 * @author liuxin 2011-3-20
 * 
 */
public class RoofBeanUtils {

	/**
	 * 查找一个类中的方法,包括其父类
	 * 
	 * @param cls
	 *            需要查找的类
	 * @param methodName
	 *            方法名
	 * @param parameterType
	 *            参数类型
	 * @return 找到的方法
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public static Method findMethod(Class<?> cls, String methodName,
			Class<?>... parameterType) throws SecurityException,
			NoSuchMethodException {
		Method method = cls.getMethod(methodName, parameterType);
		if (method == null) {
			Class<?> superclass = cls.getSuperclass();
			method = findMethod(superclass, methodName, parameterType);
		}
		return method;
	}

	/**
	 * 是否为简单属性:
	 * 
	 * boolean, byte, char, short, int, long, float, and double 和以上的包装类, 以及
	 * String, Date<br />
	 * 返回 <code>true</code>
	 * 
	 * @param cls
	 * @return
	 */
	public static boolean isSimple(Class<?> cls) {
		if(ClassUtils.isAssignable(cls, Number.class)) {
			return true;
		}
		if (ClassUtils.isPrimitiveOrWrapper(cls))
			return true;
		if (cls == String.class)
			return true;
		if (cls == Date.class)
			return true;
		return false;
	}

	public static boolean isNotSimple(Class<?> cls) {
		return !isSimple(cls);
	}

	/**
	 * 判断类是否为Boolean 或者 boolean 类型
	 * 
	 * @param cls
	 * @return
	 */
	public static boolean isBoolean(Class<?> cls) {
		return (cls == Boolean.class || cls == boolean.class) ? true : false;
	}
}
