package com.message.jms.service.core.producer;

import com.message.jms.service.constant.DefaultSubject;
import com.message.jms.service.constant.MessageEnum;

/**
 * 不支持单例<br/>
 * 主题生产者。<br/>
 * 主题消费者要先启动，然后才启动主题生产者，不然有可能消息会丢失。<br/>
 * 
 **/

public class TopicProducer extends DefaultProducer {
	
	/**
	 * @param isTransacted
	 *            是否添加事务控制，true需要调用commit方法才会发送确认消费回执，否则不消费消息，下次会重新接收。false则默认接收消息时自动发送确认消费回执。
	 * @param subject
	 *            消息队列名称。
	 */
	
	public TopicProducer(boolean isTransacted, String subject) {
		super(MessageEnum.TOPIC, isTransacted, subject);
	}
}
