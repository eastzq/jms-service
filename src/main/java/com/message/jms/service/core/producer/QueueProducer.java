package com.message.jms.service.core.producer;

import com.message.jms.service.constant.DefaultSubject;
import com.message.jms.service.constant.MessageEnum;

/**
 * 不支持单例<br/>
 * 队列生产者<br/>
 **/

public class QueueProducer extends DefaultProducer {
	/**
	 * 
	 * @param isTransacted
	 *            是否添加事务控制，true需要调用commit方法才会发送确认消费回执，否则不消费消息，下次会重新接收。false则默认接收消息时自动发送确认消费回执。
	 * @param subject
	 *            消息队列名称。
	 */
	public QueueProducer(boolean isTransacted, String subject) {
		super(MessageEnum.QUEUE, isTransacted, subject);
	}

}
