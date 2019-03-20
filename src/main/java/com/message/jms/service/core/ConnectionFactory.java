package com.message.jms.service.core;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.QueueConnectionFactory;
import javax.jms.TopicConnectionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.jms.service.config.JMSMqConfig;
import com.message.jms.service.constant.MessageEnum;
import com.message.jms.service.exception.MqException;

/**
 * 连接工厂。 单例支持。
 * TODO 今后迭代方向  ：新增连接池管理。
 **/
public final class ConnectionFactory {

	private static final Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);

	private static ConnectionFactory connectionFactory;

	private JMSMqConfig config = null;
	private QueueConnectionFactory qcFactory = null;
	private TopicConnectionFactory tcFactory = null;

	private ConnectionFactory(JMSMqConfig config) {
		logger.info("准备初始化连接工厂...");
		this.config = config;
		this.qcFactory = config.getQueueConnectionFactory();
		this.tcFactory = config.getTopicConnectionFactory();
		connectionFactory = this;
	}

	/**
	 * 获取单实例
	 * 
	 * @param config
	 *            连接初始化参数。如果是第一次初始化非空，初始化成功后可以传null。
	 * @return ConnectionFactory
	 */
	public static ConnectionFactory getInstance(JMSMqConfig config) {
		if (connectionFactory == null) {
			synchronized (ConnectionFactory.class) {
				if (connectionFactory == null) {
					connectionFactory = new ConnectionFactory(config);
				}
			}
		}
		return connectionFactory;
	}

	/**
	 * 根据配置，来动态获取连接
	 * 
	 * @param model
	 *            获取的连接类型，是主题还是队列。
	 * @throws MqException
	 *             运行时异常
	 */
	public Connection getConnection(MessageEnum model) {
		Connection connection;
		try {
			if (model == MessageEnum.QUEUE) {
				connection = this.qcFactory.createConnection();
			} else {
				connection = this.tcFactory.createConnection();
			}
		} catch (JMSException e) {
			logger.error("连接工厂创建连接出现异常！{}", e.toString());
			throw new MqException("创建连接工厂出现异常！", e);
		}
		return connection;
	}

	public JMSMqConfig getConfig() {
		return config;
	}

	public void setConfig(JMSMqConfig config) {
		this.config = config;
	}
}
