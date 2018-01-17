package org.roof.message.service.impl;

import java.util.concurrent.Executor;

import org.roof.message.api.Sendable;
import org.roof.message.api.Sender;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * 多线程执行发送
 * 
 * @author liuxin
 *
 * @param <T>
 */
public class ThreadPoolSender<T extends Sendable> implements Sender<T>, InitializingBean {

	private Executor executor;
	private int size = 3; // 启动线程数
	private Sender<T> sender;

	@Override
	public void afterPropertiesSet() throws Exception {
		start();
	}

	@Override
	public void start() {
		Assert.notNull(executor);
		Assert.notNull(sender);
		for (int i = 0; i < size; i++) {
			executor.execute(new Runnable() {

				@Override
				public void run() {
					sender.start();
				}

			});
		}
	}

	public Executor getExecutor() {
		return executor;
	}

	public void setExecutor(Executor executor) {
		this.executor = executor;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Sender<T> getSender() {
		return sender;
	}

	public void setSender(Sender<T> sender) {
		this.sender = sender;
	}

}
