package com.scheidtbachmann.tank.artemis;

import org.jboss.ejb3.annotation.ResourceAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


/**
 *
 */
@JMSConnectionFactory("java:/jms/remoteCF")
//@MessageDriven(
//    name = "MyMdbHandlerName",
//    activationConfig = {
//        @ActivationConfigProperty(propertyName = "connectionFactoryLookup", propertyValue = "jboss/exported/jms/RemoteConnectionFactoryArtemis"),
//
//            @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
//            @ActivationConfigProperty(propertyName = "destination", propertyValue = "ViewSQMMessages"),
//        @ActivationConfigProperty(propertyName = "connectionParameters", propertyValue = "host=127.0.0.1;port=61616")


//            @ActivationConfigProperty(propertyName = "connectionParameters", propertyValue = "host=127.0.0.1;port=61616"),
//        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
//        @ActivationConfigProperty(propertyName = "destination", propertyValue = "ViewSQMMessages"),
//        @ActivationConfigProperty(propertyName = "user", propertyValue = "guest"),
//        @ActivationConfigProperty(propertyName = "password", propertyValue = "")

//    })
@ResourceAdapter("remote-artemis")
@MessageDriven(name = "MessageConsiumer", activationConfig = {
    @ActivationConfigProperty(propertyName = "connectionFactoryLookup", propertyValue = "java:/jms/remoteCF"),
//    @ActivationConfigProperty(propertyName="user", propertyValue="sqm-api"),
//    @ActivationConfigProperty(propertyName="password", propertyValue="api"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "ViewSQMMessages"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})


public class ViewSubscriber implements MessageListener {


  private static final Logger LOGGER = LoggerFactory.getLogger(ViewSubscriber.class);

  public void onMessage(Message message) {
    LOGGER.info("Message for ViewNotify Lookup arrived");
    try {
      //TODO We need to mark a message as accepted and not return errors
      if (message instanceof TextMessage){
        String msg = ((TextMessage) message).getText();
        LOGGER.info(" Message arrived in view " + msg);
      }
      else {
        String msg = message.getBody(String.class);
        LOGGER.info(" Message arrived in view " + msg);
      }
    } catch (JMSException e) {
      // @TODO define exception handling
      LOGGER.error("Error while handling JMS Message", e);
    }
  }
}



