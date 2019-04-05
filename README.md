## jms-service
基于JMS接口的封装，默认提供了activeMQ的实现，同时支持拓展其他JMS消息组件如IBMMQ等。

#### 接口说明
1. 支持事务调度。
2. 支持主题和对列发送接收。
3. 支持集成spring，也可以单独调用。
4. 本身只是对jms的api做了一层封装，jms的api本身也是比较简单。所以这个包更适合面向那些对消息没有了解的同学。 

```java
// spring 测试用例，详细看包里的test用例
public void testSendMQ_queue() {
    ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-mq.xml");
    Producer producer = null;
    Consumer consumer = null;
    try {
        producer = MqFactory.getProducer(new MqParam(MessageEnum.QUEUE, true, "myQueue"));
        consumer = MqFactory.getConsumer(new MqParam(MessageEnum.QUEUE, true, "myQueue"));
        producer.send("hello myQueue");
        producer.commit();
        String result = consumer.receiveString();
        System.out.println(result);
        consumer.commit();
    } finally {
        if(producer!=null) {
            producer.close();
        }
        if(consumer!=null) {
            consumer.close();
        }
    }
}
```
#### 一些想法
1. 不知道这些接口能不能支持除了spring以外的消息队列的封装。协议都不一样了。
2. jms还是更适合场景小的。