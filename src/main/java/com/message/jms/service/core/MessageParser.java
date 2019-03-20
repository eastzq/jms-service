package com.message.jms.service.core;

import javax.jms.BytesMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import com.message.jms.service.exception.MqException;

public class MessageParser {
	
	/**
	 * 将接受到的JMS消息转换成String，抛出运行时异常MqException。
	 * @param message
	 * @return String
	 */
	public static String parseString(Message message) {
		try {
			if (message instanceof TextMessage) {
				TextMessage m = (TextMessage) message;
				return  m.getText();
			}else {
				throw new MqException("不支持接收此类型消息！");
			}
		} catch (Exception e) {
			throw new MqException("类型转换出现异常！当前类型："+message.getClass().getName(),e);
		}
	}
	/**
	 * 将接受到的JMS消息转换成对象，抛出运行时异常MqException。
	 * @param message
	 * @return T
	 */
	public static <T> T parseObject(Message message) {
		try {
			if(message instanceof ObjectMessage) {
				ObjectMessage m = (ObjectMessage) message;
				return (T) m.getObject();
			}
			else {
				throw new MqException("不支持接收此类型消息！");
			}
		} catch (Exception e) {
			throw new MqException("类型转换出现异常！当前类型："+message.getClass().getName(),e);
		}
	}
	/**
	 * 将接受到的JMS消息转换成字节数组，抛出运行时异常MqException。
	 * @param message
	 * @return byte[]
	 */
	public static byte[] parseBytes(Message message) {
		try {
			if(message instanceof BytesMessage) {
				BytesMessage m = (BytesMessage) message;
                byte[] bt = new byte[(int) m.getBodyLength()];  
                m.readBytes(bt);
                return bt;
			}else {
				throw new MqException("不支持接收此类型消息！");
			}
		} catch (Exception e) {
			throw new MqException("类型转换出现异常！当前类型："+message.getClass().getName(),e);
		}
	}
}
