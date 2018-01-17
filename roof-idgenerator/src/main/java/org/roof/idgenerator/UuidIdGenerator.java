package org.roof.idgenerator;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import org.roof.roof.dataaccess.api.IdGenerator;
import org.springframework.util.Assert;

/**
 * UUID 主键生成
 * 
 * @author liuxin
 *
 */
public class UuidIdGenerator implements IdGenerator {

	@Override
	public Serializable setId(Object entity, PropertyDescriptor idPropertyDescriptor) {
		Assert.notNull(entity);
		Assert.notNull(idPropertyDescriptor);
		String uuid = UUID.randomUUID().toString();
		try {
			idPropertyDescriptor.getWriteMethod().invoke(entity, uuid);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e);
		} catch (InvocationTargetException e) {
			throw new IllegalArgumentException(e);
		}
		return uuid;
	}

}
