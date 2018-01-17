package org.roof.cache.memcache;

/**
 * Default implementation for {@link RedisCachePrefix} which uses the given
 * cache name and a delimiter and namespace for creating the prefix.
 * 
 * @author liuxin
 *
 */
public class NameSpaceMemCachePrefix implements MemCachePrefix {
	private final String delimiter;
	private final String namespace;

	public NameSpaceMemCachePrefix() {
		this("", ":");
	}

	public NameSpaceMemCachePrefix(String namespace) {
		this(namespace, ":");
	}

	public NameSpaceMemCachePrefix(String namespace, String delimiter) {
		if (delimiter == null) {
			delimiter = ":";
		}
		this.delimiter = delimiter;
		this.namespace = namespace;
	}

	@Override
	public String prefix(String cacheName) {
		String r = "";
		if (namespace != null) {
			r = r.concat(namespace).concat(delimiter);
		}
		r = r.concat(cacheName);
		r = r.concat(delimiter);
		return r;
	}
}
