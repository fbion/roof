package org.roof.node.jobs.writer;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.roof.roof.dataaccess.api.Page;
import org.roof.spring.CurrentSpringContext;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

/**
 * 将分页写入通道 <br>
 * job启动参数:<br>
 * outChannel 写出频道 <br>
 * 
 * @author liuxin
 *
 */
public class ChannelPageItemWriter implements ItemWriter<Page> {

	private MessageChannel outChannel;
	private StepExecution stepExecution;

	@Override
	public void write(List<? extends Page> items) throws Exception {
		String customOutChannelName = stepExecution.getJobParameters().getString("outChannel");
		MessageChannel customOutChannel = null;
		if (StringUtils.isNotEmpty(customOutChannelName)) {
			customOutChannel = CurrentSpringContext.getBean(customOutChannelName, MessageChannel.class);
		}
		for (Page page : items) {
			Message<Page> message = new GenericMessage<Page>(page);
			if (customOutChannel != null) {
				customOutChannel.send(message);
			} else {
				outChannel.send(message);
			}
		}
	}

	@BeforeStep
	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public MessageChannel getOutChannel() {
		return outChannel;
	}

	public void setOutChannel(MessageChannel outChannel) {
		this.outChannel = outChannel;
	}

}
