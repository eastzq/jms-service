package com.message.jms.service.constant;

/**
 * jms的mq支持两种消息模型: 1、p2p，2、pub/sub
 *
 * @author zq
 */
public enum MessageEnum {
    /**
     * 队列模型
     */
    QUEUE("queue"),
    /**
     * 发布订阅模型
     */
    TOPIC("topic");
    private String type;

    MessageEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
