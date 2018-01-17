package org.roof.notify.redis;

import org.roof.notify.api.Message;
import org.roof.notify.api.MessageListener;

public class PrintMessageListener implements MessageListener {

	@Override
	public void onMessage(Message message, String topic) {
		System.out.println(message.getBody());
	}

}
