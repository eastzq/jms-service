package com.message.jms.service.core;

import javax.jms.Message;
import javax.jms.MessageListener;

import com.message.jms.service.core.consumer.Consumer;
import com.message.jms.service.exception.MqException;
/**
 * 封装了异步监听消息的通用功能。
 * 使用时需要继承此类，实现onMessage方法
 * 使用时必须传入Consumer
 */
public abstract class BaseMessageListener implements MessageListener {
	
	/**
	 * 消费者。
	 */
	protected Consumer consumer;

	/**
	 * 抛出运行时异常MqException。
	 * @param consumer 消费者
	 */
	public BaseMessageListener(Consumer consumer) {
		if (consumer == null) {
			throw new MqException("当前消费者不能为空！");
		}
		this.consumer = consumer;
	}
	
	/**
	 * 1.使用预置的转换方法，取到目标对象。<br/>
	 * 2.如果是事务控制需要主动调consumer的提交和回滚和关闭连接。否则当前消息默认未被消费成功。会重发送。<br/>
	 * @param message JMS消息 
	 */
	@Override
	public abstract void onMessage(Message message);


}
