package com.message.jms.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.jms.service.config.ActiveMqConfig;
import com.message.jms.service.core.ConnectionFactory;
import com.message.jms.service.core.consumer.Consumer;
import com.message.jms.service.core.consumer.TopicConsumer;
import com.message.jms.service.core.producer.Producer;
import com.message.jms.service.core.producer.TopicProducer;
import com.message.jms.service.exception.MqException;

public class TopicTest {
	private static final Logger logger = LoggerFactory.getLogger(TopicTest.class);

	public static String topic = "myTopic";

	static {
		ActiveMqConfig config = new ActiveMqConfig();
		config.setBrokerURL("tcp://10.168.3.147:61616");
		ConnectionFactory.getInstance(config);
	}

	@Test
	public void sendString2Topic() {
		Producer producer = null;
		try {
			logger.debug("准备发送消息...");
			producer = new TopicProducer(true, topic);
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
	public void consumeStringFromTopic() {
		Consumer consumer = null;
		try {
			consumer = new TopicConsumer(true, topic, "s1");
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

	@Test
	public void consumeStringFromTopicAsync() {
		Consumer consumer = new TopicConsumer(true, topic, "s2");
		consumer.receive(new MessageHandler(consumer));
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
