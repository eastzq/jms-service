package com.message.jms.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.message.jms.service.constant.MessageEnum;
import com.message.jms.service.core.MqFactory;
import com.message.jms.service.core.MqParam;
import com.message.jms.service.core.consumer.Consumer;
import com.message.jms.service.core.producer.Producer;

public class SpringMqTest {

	@Test
	public void testSendMQ_queue() {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-mq.xml");
		Producer producer = null;
		Consumer consumer = null;
		try {
			producer = MqFactory.getProducer(new MqParam(MessageEnum.QUEUE, true, "myQueue"));
			consumer = MqFactory.getConsumer(new MqParam(MessageEnum.QUEUE, true, "myQueue"));
			producer.send("hello myQueue");
			producer.commit();
			String result = consumer.receiveString();
			System.out.println(result);
			consumer.commit();
		} finally {
			if(producer!=null) {
				producer.close();
			}
			if(consumer!=null) {
				consumer.close();
			}
		}
	}
}
