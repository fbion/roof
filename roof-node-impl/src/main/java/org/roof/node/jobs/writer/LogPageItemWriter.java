package org.roof.node.jobs.writer;

import java.util.List;

import org.apache.log4j.Logger;
import org.roof.roof.dataaccess.api.Page;
import org.springframework.batch.item.ItemWriter;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

public final class LogPageItemWriter implements ItemWriter<Page> {
	private static final Logger LOGGER = Logger.getLogger(LogPageItemWriter.class);

	@Override
	public void write(List<? extends Page> items) throws Exception {
		for (Page page : items) {
			Message<Page> message = new GenericMessage<Page>(page);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(message);
				for (Object object : message.getPayload().getDataList()) {
					LOGGER.info(object);
				}
			}
		}

	}

}
