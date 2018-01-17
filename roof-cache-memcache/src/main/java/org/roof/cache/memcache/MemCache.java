package org.roof.cache.memcache;

import net.spy.memcached.MemcachedClient;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.util.Assert;

public class MemCache implements Cache {
	private final String name;
	private final String prefix;
	private final int expiration;
	private MemcachedClient memcachedClient;

	public MemCache(String name, String prefix,
			MemcachedClient memcachedClient, int expiration) {
		this.name = name;
		this.prefix = prefix;
		this.memcachedClient = memcachedClient;
		this.expiration = expiration;
	}

	public String getName() {
		return this.name;
	}

	public Object getNativeCache() {
		return memcachedClient;
	}

	public ValueWrapper get(Object key) {
		Assert.notNull(key);
		Object value = this.memcachedClient
				.get(this.computeKey(key.toString()));
		return toWrapper(value);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Object key, Class<T> type) {
		Assert.notNull(key);
		return (T) memcachedClient.get(this.computeKey(key.toString()));
	}

	public void put(Object key, Object value) {
		memcachedClient.set(this.computeKey(key.toString()), this.expiration,
				value);

	}

	public ValueWrapper putIfAbsent(Object key, Object value) {
		Assert.notNull(key);
		Object val = memcachedClient.get(this.computeKey(key.toString()));
		if (val == null) {
			put(key, value);
			val = value;
		}
		return toWrapper(val);
	}

	public void evict(Object key) {
		Assert.notNull(key);
		memcachedClient.delete(this.computeKey(key.toString()));

	}

	public void clear() {
		throw new UnsupportedOperationException("no implementation");
	}

	private ValueWrapper toWrapper(Object value) {
		return (value != null ? new SimpleValueWrapper(value) : null);
	}

	private String computeKey(Object key) {
		if (prefix == null || prefix.length() == 0)
			return key.toString();
		return prefix.concat(key.toString());
	}

}
