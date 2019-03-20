package com.message.jms.service.core.consumer;

import com.message.jms.service.constant.MessageEnum;

/**
 * 不支持单例。<br/>
 * 主题消费者。<br/>
 * 主题消费者要先启动，然后才启动主题生产者，不然有可能消息会丢失<br/>
 * @author zq
 * @date 2019-3-19 
 **/
public class TopicConsumer extends DefaultConsumer {

	/**
	 * 
	 * @param isTransacted
	 *            是否添加事务控制，true需要调用commit方法才会发送确认消费回执，否则不消费消息，下次会重新接收。false则默认接收消息时自动发送确认消费回执。
	 * @param subject
	 *            消息队列名称。
	 * @param subscriberName
	 *            当前订阅者名称。消息服务器会根据订阅者名称确认当前订阅者是否消费消息。每个订阅者都是唯一的，名称不能重复。
	 */
	public TopicConsumer(boolean isTransacted, String subject, String subscriberName) {
		super(MessageEnum.TOPIC, isTransacted, subject,subscriberName);
	}

}
