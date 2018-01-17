package org.roof.cache.memcache;

public class DefaultMemCachePrefix implements MemCachePrefix {

	private final String delimiter;

	public DefaultMemCachePrefix() {
		this(":");
	}

	public DefaultMemCachePrefix(String delimiter) {
		this.delimiter = delimiter;
	}

	public String prefix(String cacheName) {
		return (delimiter != null ? cacheName.concat(delimiter) : cacheName
				.concat(":"));
	}

}
