package com.shine.tech.epmessagebus.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.jms.service.config.ActiveMqConfig;
import com.message.jms.service.core.ConnectionFactory;
import com.message.jms.service.core.consumer.Consumer;
import com.message.jms.service.core.consumer.QueueConsumer;
import com.message.jms.service.core.consumer.TopicConsumer;
import com.message.jms.service.core.producer.Producer;
import com.message.jms.service.core.producer.QueueProducer;
import com.message.jms.service.core.producer.TopicProducer;
import com.message.jms.service.exception.MqException;

public class QueueTest {
	private static final Logger logger = LoggerFactory.getLogger(QueueTest.class);

	public static String queue = "myQueue";

	static {
		ActiveMqConfig config = new ActiveMqConfig();
		config.setBrokerURL("tcp://10.168.3.147:61616");
		ConnectionFactory.getInstance(config);
	}

	@Test
	public void sendString2Queue() {
		Producer producer = null;
		try {
			logger.debug("准备发送消息...");
			producer = new QueueProducer(true, queue);
			producer.send("hello,world!");
			producer.commit();
		} catch (MqException e) {
		} finally {
			if (producer != null) {
				producer.close();
			}
		}
	}

	@Test
	public void consumeStringFromQueue() {
		Consumer consumer = null;
		try {
			consumer = new QueueConsumer(true, queue);
			String rs = consumer.receiveString();
			logger.debug(rs);
			consumer.commit();
		} catch (MqException e) {

		} finally {
			if (consumer != null) {
				consumer.close();
			}
		}
	}

}
