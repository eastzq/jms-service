package com.message.jms.service.core.consumer;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.jms.service.constant.DefaultSubject;
import com.message.jms.service.constant.MessageEnum;
import com.message.jms.service.core.BaseMessageListener;
import com.message.jms.service.core.ConnectionFactory;
import com.message.jms.service.core.MessageParser;
import com.message.jms.service.exception.MqException;

/**
 * 默认基类，主要代码在此实现
 * 
 * @author zq
 */
public class DefaultConsumer implements Consumer {
	private static final Logger logger = LoggerFactory.getLogger(DefaultConsumer.class);

	private Connection connection;
	private Session session;
	private MessageConsumer consumer;

	private boolean isTransacted = true;

	private String subscriberName;

	protected DefaultConsumer() {
	}

	/**
	 * 初始化连接
	 * 
	 * @param model
	 *            消息类型，queue和topic
	 * @param isTransacted
	 *            是否开启事务。如果开启事务则需要调用commit方法确认收到消息。
	 * @param subject
	 *            队列或主题的名称
	 * @param subscriberName
	 *            当模式为主题时有用。用于标识当前的订阅者id。用于区分不同的订阅者。
	 * @throws MqException 运行时异常
	 */
	public DefaultConsumer(MessageEnum model, boolean isTransacted, String subject, String subscriberName) {
		this.connection = ConnectionFactory.getInstance(null).getConnection(model);
		this.isTransacted = isTransacted;
		this.subscriberName = subscriberName;
		try {
			if (StringUtils.isNotBlank(subscriberName) && model == MessageEnum.TOPIC) {
				connection.setClientID(this.subscriberName);
			}
			connection.start();
			if (this.isTransacted) {
				session = connection.createSession(true, Session.SESSION_TRANSACTED);
			} else {
				session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			}
			if (model == MessageEnum.QUEUE) {
				if (StringUtils.isBlank(subject)) {
					subject = DefaultSubject.DEFAULT_QUEUE_SUBJECT;
				}
				Destination destination = session.createQueue(subject);
				this.consumer = session.createConsumer(destination);
			} else if (model == MessageEnum.TOPIC) {
				if (StringUtils.isBlank(subject)) {
					subject = DefaultSubject.DEFAULT_TOPIC_SUBJECT;
				}
				Topic topic = session.createTopic(subject); // Topic名称
				this.consumer = session.createDurableSubscriber(topic, subscriberName);
			}
		} catch (JMSException e) {
			logger.error("创建消费者失败！{}", e.toString());
			throw new MqException("创建消费者失败！", e);
		}
	}

	@Override
	public Message receive() {
		Message message;
		try {
			message = consumer.receive();
		} catch (JMSException e) {
			logger.error("接收消息时出现异常！{}", e.toString());
			throw new MqException("接收消息时出现异常！", e);
		}
		return message;
	}

	@Override
	public void receive(BaseMessageListener listener) {
		try {
			consumer.setMessageListener(listener);
		} catch (JMSException e) {
			logger.error("接收消息时出现异常！{}", e.toString());
			throw new MqException("接收消息时出现异常！", e);
		}
	}

	public String receiveString() {
		try {
			Message message = this.receive();
			return MessageParser.parseString(message);
		} catch (MqException e) {
			logger.error("接收消息时出现异常！{}", e.toString());
			throw new MqException("接收消息时出现异常！", e);
		}
	}

	public byte[] receiveBytes() {
		try {
			Message message = this.receive();
			return MessageParser.parseBytes(message);
		} catch (MqException e) {
			logger.error("接收消息时出现异常！{}", e.toString());
			throw new MqException("接收消息时出现异常！", e);
		}
	}

	public <T> T receiveObject() {
		try {
			Message message = this.receive();
			return MessageParser.parseObject(message);
		} catch (MqException e) {
			logger.error("接收消息时出现异常！{}", e.toString());
			throw new MqException("接收消息时出现异常！", e);
		}
	}

	@Override
	public void close() {
		try {
			this.session.close();
			connection.close();
		} catch (JMSException e) {
			logger.error("close session error", e);
			e.printStackTrace();
		}
	}

	@Override
	public void commit() {
		try {
			session.commit();
		} catch (JMSException e) {
			logger.error("commit error", e);
			e.printStackTrace();
		}
	}

	@Override
	public void rollback() {
		try {
			session.rollback();
		} catch (JMSException e) {
			logger.error("rollback error", e);
			e.printStackTrace();
		}
	}
}
