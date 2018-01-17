package org.roof.message.service.impl;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.roof.message.api.Sendable;
import org.roof.message.api.SendableQueueService;
import org.roof.message.api.SendableService;
import org.roof.message.api.Sender;
import org.springframework.util.Assert;

/**
 * 默认执行发送
 * 
 * @author liuxin
 *
 * @param <T>
 */
public class DefaultSender<T extends Sendable> implements Sender<T> {

	private SendableQueueService<T> sendableQueueService;
	private SendableService<T> sendableService;

	public void start() {
		Assert.notNull(sendableQueueService);
		Assert.notNull(sendableService);
		T sendable = null;
		for (;;) {
			try {
				sendable = sendableQueueService.pull(1000, TimeUnit.MILLISECONDS);
				if (sendable == null) {
					continue;
				}
				if (sendable.getSendTimes() == null) {
					sendable.setSendTimes(0);
				}
				sendable.setSendTimes(sendable.getSendTimes() + 1);
				sendable.setSendTime(new Date());
				sendable.setStatus(sendableService.send(sendable)); // 发送
			} catch (Exception e) {
				sendable.setStatus(-1);
				sendable.setErrorMsg(e.getMessage());
			} finally {
				if (sendable != null) {
					sendableService.update(sendable);
				}
			}
		}

	}

	public SendableQueueService<T> getSendableQueueService() {
		return sendableQueueService;
	}

	public SendableService<T> getSendableService() {
		return sendableService;
	}

	public void setSendableQueueService(SendableQueueService<T> sendableQueueService) {
		this.sendableQueueService = sendableQueueService;
	}

	public void setSendableService(SendableService<T> sendableService) {
		this.sendableService = sendableService;
	}

}
