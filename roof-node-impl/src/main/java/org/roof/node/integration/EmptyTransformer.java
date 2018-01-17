package org.roof.node.integration;

import org.springframework.integration.transformer.Transformer;
import org.springframework.messaging.Message;

public class EmptyTransformer implements Transformer {

	@Override
	public Message<?> transform(Message<?> message) {
		return message;
	}

}
