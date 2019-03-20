package com.message.jms.service.core.consumer;

import javax.jms.Message;

import com.message.jms.service.core.BaseMessageListener;
import com.message.jms.service.exception.MqException;

/**
 * 消费者，提供消费的api。支持同步和异步处理。支持事务处理。
 * 记得在finally块关闭连接
 * @author zq
 **/

public interface Consumer {
    /**
     * 接收消息，同步，返回Jms消息
	 * @throws MqException 运行时异常
	 */
    Message receive();

    /**
     * 接收字符串消息，同步。
     * @return String
     * @throws MqException 运行时异常
     */
    String receiveString();

    /**
     * 接收对象消息，同步。
     * @return T
     * @throws MqException 运行时异常
     */
    <T> T receiveObject();
    
    
    /**
     * 接收字节数组，同步。
     * @return byte[]
     * @throws MqException 运行时异常
     */
     byte[] receiveBytes();  
    
    
    /**
     * 接收消息，异步
     * @param listener 监听器MessageListener接口实现类
      * @throws MqException 运行时异常
     */
    void receive(BaseMessageListener listener);
	
    /**
     * 关闭当前会话资源
     */
    void close();
    
    /**
     * 提交
     */
    void commit();
    
    /**
     * 回滚
     */
    void rollback();
}
