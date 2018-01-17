package org.roof.cache.memcache;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.spy.memcached.MemcachedClient;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

public class MemCacheManager implements CacheManager {

	// fast lookup by name map
	private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();
	private final Collection<String> names = Collections.unmodifiableSet(caches
			.keySet());

	private boolean usePrefix = false;
	private MemCachePrefix cachePrefix = new DefaultMemCachePrefix();

	// 0 - never expire
	private int defaultExpiration = 0;
	private Map<String, Integer> expires = null;
	private MemcachedClient memcachedClient;

	@Override
	public Cache getCache(String name) {
		Cache c = caches.get(name);
		if (c == null) {
			int expiration = computeExpiration(name);
			c = new MemCache(name,
					(usePrefix ? cachePrefix.prefix(name) : null),
					memcachedClient, expiration);
			caches.put(name, c);
		}

		return c;
	}

	private int computeExpiration(String name) {
		Integer expiration = null;
		if (expires != null) {
			expiration = expires.get(name);
		}
		return (expiration != null ? expiration.intValue() : defaultExpiration);
	}

	public Collection<String> getCacheNames() {
		return names;
	}

	public void setUsePrefix(boolean usePrefix) {
		this.usePrefix = usePrefix;
	}

	public void setCachePrefix(MemCachePrefix cachePrefix) {
		this.cachePrefix = cachePrefix;
	}

	public void setDefaultExpiration(int defaultExpireTime) {
		this.defaultExpiration = defaultExpireTime;
	}

	public void setExpires(Map<String, Integer> expires) {
		this.expires = (expires != null ? new ConcurrentHashMap<String, Integer>(
				expires) : null);
	}

	public MemcachedClient getMemcachedClient() {
		return memcachedClient;
	}

	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

}
