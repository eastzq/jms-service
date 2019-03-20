package com.message.jms.service.core.consumer;

import com.message.jms.service.constant.DefaultSubject;
import com.message.jms.service.constant.MessageEnum;

/**
 * 不支持单例<br/>
 * 队列消费者<br/>
 * @author zq
 * @date 2019-3-19
 **/

public class QueueConsumer extends DefaultConsumer {
	
	/**
	 * 
	 * @param isTransacted
	 *            是否添加事务控制。
	 * @param subject
	 *            消息队列名称。
	 */
    public QueueConsumer(boolean isTransacted,String subject) {
        super(MessageEnum.QUEUE, isTransacted, subject,null);
    }
}
