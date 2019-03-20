package com.message.jms.service.core;

import com.message.jms.service.core.consumer.Consumer;
import com.message.jms.service.core.consumer.DefaultConsumer;
import com.message.jms.service.core.producer.DefaultProducer;
import com.message.jms.service.core.producer.Producer;

public class MqFactory {
	/**
	 * @note 获取消费者，发布订阅模型时，消费者要先订阅，生产者再发布。
     * @note 记得在finally块关闭连接
	 * @param mqParam
	 * @return {@link Consumer}
	 */
	public static Consumer getConsumer(MqParam mqParam) {
		Consumer consumer = new DefaultConsumer(mqParam.getModel(), mqParam.isTransacted(), mqParam.getSubject(),
				mqParam.getSubscriberName());
		return consumer;
	}
	/**
	 * 获取生产者
	 * @note 获取消费者，发布订阅模型时，消费者要先订阅，生产者再发布。
     * @note 记得在finally块关闭连接
	 * @param mqParam
	 * @return {@link Producer}
	 */
	public static Producer getProducer(MqParam mqParam) {
		Producer producer = new DefaultProducer(mqParam.getModel(), mqParam.isTransacted(), mqParam.getSubject());
		return producer;
	}

}
