package org.roof.message.api;

import java.util.concurrent.TimeUnit;

/**
 * 信息队列管理
 * 
 * @author liuxin
 *
 * @param <T>
 */
public interface SendableQueueService<T extends Sendable> {

	/**
	 * 将信息加入队列
	 * 
	 * @param sendable
	 */
	T push(T sendable, long timeout, TimeUnit unit) throws InterruptedException;

	/**
	 * 将信息弹出队列
	 * 
	 * @return
	 */
	T pull(long timeout, TimeUnit unit) throws InterruptedException;

	/**
	 * 获取队列长度
	 * 
	 * @return
	 */
	int size();

}
