package com.message.jms.service.core.producer;

import java.io.Serializable;

import javax.jms.Message;

import com.message.jms.service.exception.MqException;

/**
 * 生产者接口 支持发送消息，事务性发送消息。支持发送字符串，字节数组，序列化对象。
 * 记得在finally块关闭连接
 * @author zq
 * @date 2019-3-19
 **/

public interface Producer {
	/**
	 * 直接发送JMS消息
	 * 
	 * @param message
	 *            消息
	 * @throws MqException
	 *             运行时异常
	 */
	void send(Message message);

	/**
	 * 发送字符串类型的消息
	 * 
	 * @param message
	 *            消息
	 * @throws MqException
	 *             运行时异常
	 */
	void send(String message);

	/**
	 * 发送javabean类型消息,但是必须实现序列化
	 * 
	 * @param message
	 *            消息
	 * @throws MqException
	 *             运行时异常
	 */
	void send(Serializable message);

	/**
	 * 发送字节数组消息
	 * 
	 * @param message
	 *            消息
	 * @throws MqException
	 *             运行时异常
	 */
	void send(byte[] message);

	/**
	 * 提交
	 */
	void commit();

	/**
	 * 回滚
	 */
	void rollback();

	/**
	 * 关闭连接
	 */
	void close();

}
