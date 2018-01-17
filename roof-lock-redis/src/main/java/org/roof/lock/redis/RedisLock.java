package org.roof.lock.redis;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

public class RedisLock implements Lock {
	private static final Logger LOGGER = Logger.getLogger(RedisLock.class);
	private static final String LOCK_SUFFIX = ".~lock";
	private RedisTemplate<String, Object> redisTemplate;
	private String lockName = null;
	private static final long DEFAULT_LOCK_TIMEOUT = 60 * 1000;// 默认锁超时
																// millisecond
	private static final long DEFAULT_GET_LOCK_TIMEOUT = 2 * 60 * 1000;// 默认获取锁超时
	// millisecond
	private static final long DEFAULT_GET_INTERVAL = 100;// 默认获取锁的间隔 millisecond

	private long lockTimeout = DEFAULT_LOCK_TIMEOUT; // 锁超时
	private long getLockTimeOut = DEFAULT_GET_LOCK_TIMEOUT;// 获取锁超时
	private long getInterval = DEFAULT_GET_INTERVAL; // 获取锁间隔时间
	private BoundValueOperations<String, Object> op = null;

	/**
	 * 创建锁
	 * 
	 * @param lockName
	 *            锁的名称
	 * @param redisTemplate
	 */
	public RedisLock(String lockName, RedisTemplate<String, Object> redisTemplate) {
		Assert.notNull(lockName);
		Assert.notNull(redisTemplate);
		this.lockName = lockName + LOCK_SUFFIX;
		this.redisTemplate = redisTemplate;
		op = redisTemplate.boundValueOps(this.lockName);
	}

	/**
	 * 创建锁
	 * 
	 * @param lockName
	 *            锁名称
	 * @param lockTimeout
	 *            锁超时
	 * @param getLockTimeout
	 *            获得锁超时
	 * @param getInterval
	 *            获得锁间隔
	 * @param redisTemplate
	 */
	public RedisLock(String lockName, long lockTimeout, long getLockTimeout, long getInterval,
			RedisTemplate<String, Object> redisTemplate) {
		this(lockName, redisTemplate);
		this.lockTimeout = lockTimeout;
		this.getInterval = getInterval;
	}

	@Override
	public void lock() {
		long totalGetInterval = 0;
		boolean b = op.setIfAbsent(System.currentTimeMillis());
		while (!b) {
			try {
				Thread.sleep(getInterval);
				totalGetInterval += getInterval;
				if (totalGetInterval >= getLockTimeOut) {
					throw new RedisLockException("获取锁超时");
				}
			} catch (InterruptedException e) {
				throw new RedisLockException(e);
			}
			b = op.setIfAbsent(System.currentTimeMillis());
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("[" + this.lockName + "]锁获取成功, 耗时[" + totalGetInterval + "]");
		}
		op.expire(lockTimeout, TimeUnit.MILLISECONDS);
	}

	@Override
	public void unlock() {
		redisTemplate.delete(this.lockName);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("[" + this.lockName + "]锁释放成功");
		}
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean tryLock() {
		Object o = op.get();
		if (o == null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		final long rawTimeout = unit.toMillis(time);
		long totalGetInterval = 0;
		while (op.get() != null) {
			Thread.sleep(getInterval);
			totalGetInterval += getInterval;
			if (totalGetInterval >= rawTimeout) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Condition newCondition() {
		throw new UnsupportedOperationException();
	}

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-redis-test.xml");
		@SuppressWarnings("unchecked")
		final RedisTemplate<String, Object> redisTemplate = context.getBean(RedisTemplate.class);
		testLock(redisTemplate);
	}

	private static void testLock(final RedisTemplate<String, Object> redisTemplate) {
		RedisLock lock = new RedisLock("epo:testLock", 10 * 1000, 20 * 1000, 100, redisTemplate);
		System.out.println("线程1尝试获取锁" + lock.tryLock());
		System.out.println("线程1 获取 lock 开始");
		lock.lock();
		System.out.println("线程1 获取 lock 完成");
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				RedisLock lock = new RedisLock("epo:testLock", redisTemplate);
				System.out.println("线程2 获取 lock 开始");
				System.out.println("线程2 尝试获取锁" + lock.tryLock());
				lock.lock();
				System.out.println("线程2 获取 lock 完成	");
				try {
					Thread.sleep(10 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				lock.unlock();
				System.out.println("线程2 释放 lock 完成	");

			}
		});
		thread.start();

		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// lock.unlock();
		// System.out.println("线程1 释放 lock 完成");
	}

}
