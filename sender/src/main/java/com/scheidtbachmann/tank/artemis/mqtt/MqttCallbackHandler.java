package com.scheidtbachmann.tank.artemis.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class MqttCallbackHandler implements MqttCallback {

  private static final Logger LOGGER = LoggerFactory.getLogger(MqttCallbackHandler.class);


  public void connectionLost(final Throwable throwable) {
    LOGGER.info("Connection lost");
  }

  public void messageArrived(final String s, final MqttMessage mqttMessage) throws Exception {
    LOGGER.info("MQTT Message arrived for QoS:{}", mqttMessage.getQos());
    byte[] payload = mqttMessage.getPayload();
    String pom = new String(payload);
    LOGGER.info("Message {}", pom);
  }

  public void deliveryComplete(final IMqttDeliveryToken iMqttDeliveryToken) {
    LOGGER.info("Delivery complet");
  }
}
