package org.roof.lock.redis;

/**
 * 锁异常
 * 
 * @author liuxin
 *
 */
public class RedisLockException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 688953896964920114L;

	public RedisLockException() {
		super();
	}

	public RedisLockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RedisLockException(String message, Throwable cause) {
		super(message, cause);
	}

	public RedisLockException(String message) {
		super(message);
	}

	public RedisLockException(Throwable cause) {
		super(cause);
	}

}
