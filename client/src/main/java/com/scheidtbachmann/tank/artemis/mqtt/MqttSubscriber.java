package com.scheidtbachmann.tank.artemis.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import io.netty.handler.codec.mqtt.MqttQoS;

/**
 *
 */
public class MqttSubscriber {

  private static final Logger LOGGER = LoggerFactory.getLogger(MqttSubscriber.class);
  private static final String ERR_MSG = "MQTT Error";
  public static final String USER_NAME = "sqm-api";
  public static final String PASSWORD = "api";
  public static final String CLIENT_ID = "Sender";
  public static final String SQM_CALCULATION_RESULTS = "SQMCalculationResults";
  public static final String SQM_NWKMESSAGES = "SQMNwkSQMMessages";

  private MqttClient mqttClient;
  private boolean isShutdown;


  private MqttCallbackHandler callbackhandler;

  public MqttSubscriber() {
    callbackhandler = new MqttCallbackHandler();
  }

  /**
   * Starts the MqttClients.
   */
  public void startCalculationResultMqttListener() {
    try {
      isShutdown = false;
      Random random = new Random();
      mqttClient = new MqttClient("tcp://0.0.0.0:61616", CLIENT_ID ,
                                  new MqttDefaultFilePersistence(System.getProperty("java.io.tmpdir")));

    } catch (MqttException e) {
      LOGGER.error("Error msg {} cause {}", e.getMessage(), e.getCause() == null ? "" : e.getCause().getMessage());
      LOGGER.error(ERR_MSG, e);
    }

    mqttClient.setCallback(callbackhandler);
    try {
      firstConnectToCalculationResultQueue();
      LOGGER.info("Listening started.");
    } catch (MqttException e) {
      LOGGER.error(ERR_MSG, e);
    }
  }

  /**
   * This Method is just a wrapper to make it easier for humans to follow the program flow.
   *
   * @throws MqttException should be caught by caller
   */
  private void firstConnectToCalculationResultQueue() throws MqttException {
    LOGGER.info("Initiating first connect to CalculationResultQueue");
    reconnectToCalculationResultQueue();
  }

  /**
   * Waits until connected to mqtt topic.
   *
   * @throws MqttException in case of unreachable broker after timeout
   */
  protected void reconnectToCalculationResultQueue() throws MqttException {
    LOGGER.info("(re)connecting to CalculationResultQueue");
    // don't try to (re)reconnectToCalculationResultQueue
    if (isShutdown) {
      LOGGER.info("reconnect is aborted due to wanted shutdown");
      return;
    }

    long start = System.currentTimeMillis();
    IMqttToken iMqttToken = null;
    while (!mqttClient.isConnected()) {
      try {
        MqttConnectOptions connOpts = new MqttConnectOptions();
//        connOpts.setUserName(USER_NAME);
//        connOpts.setPassword(PASSWORD.toCharArray());
//        connOpts.setCleanSession(false);
        iMqttToken = mqttClient.connectWithResult(connOpts);
        LOGGER.info("{}", iMqttToken);
      } catch (MqttException e) {
        LOGGER.error(ERR_MSG, e);
      }

      if (!mqttClient.isConnected()) {
        long timeout = 10 * 1000;
        if (timeout > 0 && System.currentTimeMillis() - start > timeout) {
          throw new MqttException(MqttException.REASON_CODE_BROKER_UNAVAILABLE);
        }

        try {
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          LOGGER.warn("Thread.sleep() interrupted!", e);
        }
      }
      String topic = SQM_CALCULATION_RESULTS;
      LOGGER.info("Try to connect to {} ", topic);
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      mqttClient.subscribe(topic);
      LOGGER.info("MQTT listen started.");
    }
  }

  /**
   * @TODO do javadoc .
   */
  protected void shutdownCalculationResultMqttListener() {
    LOGGER.info("Disconnecting MqttClient");
    isShutdown = true;
    // disconnect
    if (mqttClient != null && mqttClient.isConnected()) {
      try {
        mqttClient.disconnect();
        LOGGER.info("MQTT disconnected");
      } catch (MqttException e) {
        LOGGER.error(ERR_MSG, e);
      }
    }
  }

  public static void main(String[] args) {
    MqttSubscriber mqttSubscriber = new MqttSubscriber();
    mqttSubscriber.startCalculationResultMqttListener();
    try {
      Thread.sleep(10 * 1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    mqttSubscriber.shutdownCalculationResultMqttListener();
  }


}
