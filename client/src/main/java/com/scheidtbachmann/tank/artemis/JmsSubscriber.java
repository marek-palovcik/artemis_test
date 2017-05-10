package com.scheidtbachmann.tank.artemis;

import org.apache.activemq.artemis.api.jms.ActiveMQJMSClient;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.reader.TextMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 *
 */
public class JmsSubscriber {
  private final static Logger logger = LoggerFactory.getLogger(JmsSubscriber.class);

  public void start(){
    String resultTopic = "jms.queue.sqm.testMessages";
    Queue queue = ActiveMQJMSClient.createQueue(resultTopic);

  ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616", "sqm", "api");
  JMSContext jmsContext = cf.createContext();
  jmsContext.createConsumer(queue).setMessageListener(new MyMessageListener(logger));

    try {
      Thread.sleep(1000*20);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static class MyMessageListener implements MessageListener {

    private final Logger logger;

    public MyMessageListener(final Logger parLogger) {
      this.logger = parLogger;
    }


    public void onMessage(final Message message) {
      TextMessage tm = (TextMessage) message;
      try {
        logger.info("Have message "+((TextMessage) message).getText());
      } catch (JMSException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    new JmsSubscriber().start();
  }

}
