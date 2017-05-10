package com.scheidtbachmann.tank.artemis;

import org.apache.activemq.artemis.api.jms.ActiveMQJMSClient;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.TextMessage;

/**
 *
 */
public class JMSProducer {

  Logger logger = LoggerFactory.getLogger(JMSProducer.class);


  public static void main(String[] args) {
    new JMSProducer().start();
  }


  public void start() {

    // Instantiate the queue
    String resultTopic = "jms.queue.sqm.testMessages";
    Queue queue = ActiveMQJMSClient.createQueue(resultTopic);

    // Instantiate the ConnectionFactory (Using the default URI on this case)
    // Also instantiate the jmsContext
    // Using closeable interface
    ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:1883", "sa", "manager");
    JMSContext jmsContext = cf.createContext();
    // Create a message producer, note that we can chain all this into one statement
    try {
      javax.jms.JMSProducer producer = jmsContext.createProducer();
      producer.send(queue, createMessage(jmsContext, "marekTest"));
      Thread.sleep(1000 * 2);
      producer.send(queue, createMessage(jmsContext, "kai Test"));
      Thread.sleep(1000 * 2);
      producer.send(queue, createMessage(jmsContext, "zuzka Test"));
    } catch (JMSException e) {
      logger.error("Error :", e);
    } catch (InterruptedException e) {
      logger.error("Error :", e);
    }
    logger.info("message send");
    try {
      Thread.sleep(1000 * 5);
    } catch (InterruptedException e) {
      logger.error("Error :", e);
    }
    jmsContext.close();
    cf.close();
  }

  private TextMessage createMessage(final JMSContext jmsContext, final String value) throws JMSException {
    try {
      TextMessage mess = jmsContext.createTextMessage();
      mess.setText(value);
      return mess;
    } catch (JMSException e) {
      throw e;
    }
  }


}
