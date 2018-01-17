package org.roof.commons;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 
 * @author liuxin
 * 
 */
public class RoofPropertyUtils {

	private static final Logger LOGGER = Logger
			.getLogger(RoofPropertyUtils.class);

	/**
	 * copy javabean 的值<br/>
	 * 如果目标bean某属性已经有值在则保留原值
	 * 
	 * @param source
	 *            源
	 * @param target
	 *            目的
	 * @param ignoreProperties
	 *            忽略copy的属性
	 */
	public static void copyPropertiesIgnoreExist(Object source,
			final Object target, final String[] ignoreProperties) {
		BeanCopier beanCopier = BeanCopier.create(source.getClass(),
				target.getClass(), true);
		beanCopier.copy(source, target, new Converter() {
			@SuppressWarnings("unchecked")
			public Object convert(Object value,
					@SuppressWarnings("rawtypes") Class targetClass,
					Object context) {
				try {
					String propertyName = convertToPropertyName(context
							.toString());
					Object targetValue = PropertyUtils.getProperty(target,
							propertyName);
					if (targetValue != null
							|| ArrayUtils.contains(ignoreProperties,
									propertyName)) {

						return targetValue;
					}
					if (RoofBeanUtils.isSimple(targetClass)) {
						return value;
					}
					// 如果为集合类型则新建集合拷贝原数据
					if (value != null
							&& ClassUtils.isAssignable(value.getClass(),
									Collection.class)) {
						@SuppressWarnings("rawtypes")
						Collection c = (Collection) value;
						@SuppressWarnings("rawtypes")
						Collection result = (Collection) value.getClass()
								.newInstance();
						result.addAll(c);
						return result;
					}
					Object result = targetClass.newInstance();
					copyPropertiesIgnoreExist(value, result, ignoreProperties);
					return result;
				} catch (IllegalAccessException e) {
					LOGGER.error(e);
				} catch (InvocationTargetException e) {
					LOGGER.error(e);
				} catch (NoSuchMethodException e) {
					LOGGER.error(e);
				} catch (InstantiationException e) {
					LOGGER.error(e);
				}
				return value;
			}
		});

	}

	/**
	 * get,set方法名转换为属性名
	 * 
	 * @param name
	 *            get,set方法名
	 * @return 属性名
	 */
	public static String convertToPropertyName(String name) {
		name = StringUtils.substring(name, 3);
		String c = StringUtils.substring(name, 0, 1);
		name = StringUtils.replaceOnce(name, c, StringUtils.lowerCase(c));
		return name;
	}

}
