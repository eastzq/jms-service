package com.message.jms.service.core;

import com.message.jms.service.constant.MessageEnum;

/**
 * 工厂方法的入参
 */
public class MqParam {
	/**
	 * 消息类型，1.点对点queue，2.发布订阅topic。
	 */
	private MessageEnum model;
	/**
	 * 是否开启事务。如果开启事务则需要调用commit方法确认收到消息。否则自动确认。
	 */
	private boolean isTransacted;
	/**
	 * 队列或主题的名称
	 */
	private String subject;
	/**
	 * 当模型为topic的订阅者有效。订阅者唯一id。消息服务器会根据此id确认当前订阅者身份，和判断订阅者是否消费消息。
	 */
	private String subscriberName;

	public MqParam(MessageEnum model, boolean isTransacted, String subject, String subscriberName) {
		this(model, isTransacted, subject);
		this.subscriberName = subscriberName;
	}

	public MqParam(MessageEnum model, boolean isTransacted, String subject) {
		this.model = model;
		this.isTransacted = isTransacted;
		this.subject = subject;
	}

	public MessageEnum getModel() {
		return model;
	}

	public void setModel(MessageEnum model) {
		this.model = model;
	}

	public boolean isTransacted() {
		return isTransacted;
	}

	public void setTransacted(boolean isTransacted) {
		this.isTransacted = isTransacted;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubscriberName() {
		return subscriberName;
	}

	public void setSubscriberName(String subscriberName) {
		this.subscriberName = subscriberName;
	}

}
