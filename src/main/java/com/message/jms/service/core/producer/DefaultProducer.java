package com.message.jms.service.core.producer;

import java.io.Serializable;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.jms.service.constant.DefaultSubject;
import com.message.jms.service.constant.MessageEnum;
import com.message.jms.service.core.ConnectionFactory;
import com.message.jms.service.exception.MqException;

public class DefaultProducer implements Producer {
	private static final Logger logger = LoggerFactory.getLogger(DefaultProducer.class);

	private Connection connection;
	private Session session;
	private MessageProducer producer;
	private boolean isTransacted = true;

	protected DefaultProducer() {
	}

	/**
	 * 初始化生产者
	 * 
	 * @param model
	 *            消息类型，queue和topic
	 * @param isTransacted
	 *            是否开启事务。如果开启事务则需要调用commit方法确认收到消息。
	 * @param subject
	 *            队列或主题的名称
	 * @throws MqException
	 *             运行时异常
	 */
	public DefaultProducer(MessageEnum model, boolean isTransacted, String subject) {
		this.connection = ConnectionFactory.getInstance(null).getConnection(model);
		this.isTransacted = isTransacted;
		try {

			connection.start();
			if (this.isTransacted) {
				session = connection.createSession(true, Session.SESSION_TRANSACTED);
			} else {
				session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			}
			Destination destination = null;
			if (model == MessageEnum.QUEUE) {
				if (StringUtils.isBlank(subject)) {
					subject = DefaultSubject.DEFAULT_QUEUE_SUBJECT;
				}
				destination = session.createQueue(subject);
			} else if (model == MessageEnum.TOPIC) {
				if (StringUtils.isBlank(subject)) {
					subject = DefaultSubject.DEFAULT_TOPIC_SUBJECT;
				}
				destination = session.createTopic(subject);
			}
			this.producer = session.createProducer(destination);
		} catch (JMSException e) {
			logger.error("创建生产者失败！{}", e.toString());
			throw new MqException("创建生产者失败！", e);
		}
	}

	@Override
	public void send(Message message) {
		try {
			producer.send(message);
		} catch (JMSException e) {
			logger.error("发送消息失败！{}", e.toString());
			throw new MqException("发送消息失败失败！", e);
		}
	}

	@Override
	public void send(String message) {
		try {
			TextMessage msg = session.createTextMessage(message);
			send(msg);
		} catch (JMSException e) {
			logger.error("发送消息失败！{}", e.toString());
			throw new MqException("发送消息失败失败！", e);
		}
	}

	@Override
	public void send(Serializable message) {
		try {
			ObjectMessage msg = session.createObjectMessage(message);
			send(msg);
		} catch (JMSException e) {
			logger.error("发送消息失败！{}", e.toString());
			throw new MqException("发送消息失败失败！", e);
		}
	}

	@Override
	public void send(byte[] message) {
		try {
			BytesMessage msg = session.createBytesMessage();
			msg.writeBytes(message);
			send(msg);
		} catch (JMSException e) {
			logger.error("发送消息失败！{}", e.toString());
			throw new MqException("发送消息失败失败！", e);
		}
	}

	/**
	 * 关闭session和connection 如果采用的是单连接，该方法调用时，只关闭session，不关闭connection
	 * 若采用的是池连接，则调用该方法时，关闭session和connection
	 */
	@Override
	public void close() {
		try {
			this.session.close();
			this.connection.close();
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
