package com.message.jms.service.config;

import javax.jms.QueueConnectionFactory;
import javax.jms.TopicConnectionFactory;

public abstract class JMSMqConfig {

    private String username;

    private String password;

    public abstract TopicConnectionFactory getTopicConnectionFactory();
    
	public abstract QueueConnectionFactory getQueueConnectionFactory();
    
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

}
