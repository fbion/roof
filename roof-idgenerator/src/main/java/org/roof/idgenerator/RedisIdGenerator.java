package org.roof.idgenerator;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.roof.roof.dataaccess.api.IdGenerator;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * <p>
 * redis 自增Id生成
 * </p>
 * <p>
 * 原子自增可用于分布式环境
 * </p>
 * 
 * @author liuxin
 *
 */
public class RedisIdGenerator implements IdGenerator {
	private RedisTemplate<String, Long> redisTemplate;
	private long initVal = 0;

	public RedisIdGenerator(RedisTemplate<String, Long> redisTemplate) {
		super();
		this.redisTemplate = redisTemplate;
	}

	@Override
	public Serializable setId(Object entity, PropertyDescriptor idPropertyDescriptor) {
		String key = "sequence:" + getSeqName(entity.getClass());
		long val = redisTemplate.boundValueOps(key).increment(1);
		if (val < initVal) {
			redisTemplate.boundValueOps(key).increment(initVal - val);
			val = initVal;
		}
		try {
			idPropertyDescriptor.getWriteMethod().invoke(entity, val);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e);
		} catch (InvocationTargetException e) {
			throw new IllegalArgumentException(e);
		}
		return val;
	}

	private String getSeqName(Class<?> cls) {
		while (cls.getSuperclass() != Object.class) {
			cls = cls.getSuperclass();
		}
		return cls.getName();
	}

	public long getInitVal() {
		return initVal;
	}

	public void setInitVal(long initVal) {
		this.initVal = initVal;
	}

}
