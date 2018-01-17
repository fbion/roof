package org.roof.message.service.impl;

import java.util.UUID;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.TimeUnit;

import org.roof.message.api.SendableQueueService;
import org.roof.message.api.Sendable;
import org.roof.message.api.SendableService;
import org.springframework.util.Assert;

/**
 * BlockingDeque实现信息队列操作
 * 
 * 当信息的优先级为0时立即发送, 不为0时则加入队尾等待发送
 * 
 * @author liuxin
 *
 * @param <T>
 *            信息
 */
public class BlockingDequeService<T extends Sendable> implements SendableQueueService<T> {
	private BlockingDeque<T> blockingDeque;
	private SendableService<T> sendableService;

	@Override
	public T push(T sendable, long timeout, TimeUnit unit) throws InterruptedException {
		Assert.notNull(blockingDeque);
		Assert.notNull(sendableService);
		if (sendable.getId() == null) {
			sendable.setId(UUID.randomUUID().toString()); // 设置唯一编码
		}
		sendable.setStatus(2); // 设置待发送
		sendableService.save(sendable); // 保存信息
		// 加入发送队列
		if (sendable.getPriority() != null && sendable.getPriority() == 0) {
			blockingDeque.offerFirst(sendable, timeout, unit);
		} else {
			blockingDeque.offer(sendable, timeout, unit);
		}
		return sendable;
	}

	@Override
	public T pull(long timeout, TimeUnit unit) throws InterruptedException {
		Assert.notNull(blockingDeque);
		return blockingDeque.poll(timeout, unit);
	}

	@Override
	public int size() {
		Assert.notNull(blockingDeque);
		return blockingDeque.size();
	}

	public BlockingDeque<T> getBlockingDeque() {
		return blockingDeque;
	}

	public SendableService<T> getSendableService() {
		return sendableService;
	}

	public void setBlockingDeque(BlockingDeque<T> blockingDeque) {
		this.blockingDeque = blockingDeque;
	}

	public void setSendableService(SendableService<T> sendableService) {
		this.sendableService = sendableService;
	}

}
