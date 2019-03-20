package com.shine.tech.epmessagebus.test;

import javax.jms.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.jms.service.core.BaseMessageListener;
import com.message.jms.service.core.MessageParser;
import com.message.jms.service.core.consumer.Consumer;
import com.message.jms.service.exception.MqException;

/**
 * 异步接收消息实例
 * @author admin
 *
 */
public class MessageHandler extends BaseMessageListener {

	private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

	public MessageHandler(Consumer consumer) {
		super(consumer);
	}

	/**
	 * 异步接收消息
	 */
	@Override
	public void onMessage(Message message) {
		try {
			String m = MessageParser.parseString(message);
			logger.debug(m);
			consumer.commit();
		} catch (MqException e) {

		} finally {
			if (consumer != null) {
				consumer.close();
			}
		}
	}

}
