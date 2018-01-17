package org.roof.node.integration;

import java.util.Collection;
import java.util.Iterator;

import org.roof.roof.dataaccess.api.Page;
import org.springframework.integration.splitter.AbstractMessageSplitter;
import org.springframework.messaging.Message;

/**
 * 将分页消息拆分成单条
 * 
 * @author liuxin
 *
 */
public class PageMessageSplitter extends AbstractMessageSplitter {

	@Override
	protected void sendOutputs(Object result, Message<?> requestMessage) {
		if (result instanceof Iterable<?> && shouldSplitOutput((Iterable<?>) result)) { // 判断消息中是否包含集合,如果是集合则逐条组装成新的消息
			for (Object o : (Iterable<?>) result) {
				this.produceOutput(o, requestMessage);
			}
		} else if (result instanceof Iterator<?>) {
			this.produceOutput(result, requestMessage);
		} else if (result != null) {
			this.produceOutput(result, requestMessage);
		}
	}

	@Override
	protected Object splitMessage(Message<?> message) {
		Object payload = message.getPayload(); // 获取消息体
		if (payload instanceof Collection) { // 如果消息体是集合则返回
			return payload;
		}
		if (payload instanceof Page) { // 消息体是分页则取分页的集合
			Page page = (Page) payload;
			return page.getDataList();
		}
		return message;
	}

}
