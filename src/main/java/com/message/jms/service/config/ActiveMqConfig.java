package com.message.jms.service.config;

import javax.jms.QueueConnectionFactory;
import javax.jms.TopicConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.jms.service.exception.MqException;

/**
 * ActiveMQ 参数配置
 * @date 2018年7月11日 下午5:52:03
 * @seee
 * @since
 */
public class ActiveMqConfig extends JMSMqConfig {
	
    private static final Logger logger = LoggerFactory.getLogger(ActiveMqConfig.class);
    
	// 通信地址
	private String brokerURL;

	@Override
	public QueueConnectionFactory getQueueConnectionFactory() {
        String brokerUrl = this.brokerURL;
        String username = this.getUsername();
        String password = this.getPassword();
        if (StringUtils.isBlank(brokerUrl)) {
            logger.error("broker url is blank!");
            throw new MqException("broker url is blank");
        }
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerUrl);
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            factory.setUserName(username);
            factory.setPassword(password);
        }
		return factory;
	}

	@Override
	public TopicConnectionFactory getTopicConnectionFactory() {
		return (TopicConnectionFactory) this.getQueueConnectionFactory();
	}

	public String getBrokerURL() {
		return brokerURL;
	}

	public void setBrokerURL(String brokerURL) {
		this.brokerURL = brokerURL;
	}


}
