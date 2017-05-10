package com.scheidtbachmann.tank.artemis.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *
 */
public class MqttProducer {

  private static final Logger LOGGER = LoggerFactory.getLogger(MqttProducer.class);
  public static final String SQM_CALCULATION_RESULTS = "SQMCalculationResults";
  public static final String USER_NAME = "sqm_provider";
  public static final String PASSWORD = "sqm_backend";

  private MqttClient mqttClient;
  private final MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
  public static final String CLIENT_ID = "Sender1";
  public static final String SERVER_URL = "tcp://0.0.0.0:61616";

  public boolean send(String strMessage) {

    // Message Options
    //  0 (At Most Once),1 (At Least Once)=default and 2 (Exactly Once)
    int qos = 2;

    try {
      if (mqttClient == null || !mqttClient.isConnected()) {
        if (!connect()) {
          return false;
        }
      }

      MqttMessage message = new MqttMessage(strMessage.getBytes());
      message.setQos(qos);
      String resultTopic = SQM_CALCULATION_RESULTS;
      mqttClient.publish(resultTopic, message);
      LOGGER.info("Result published to topic {}.", resultTopic);

      return checkDelivery();

    } catch (MqttException e) {
      LOGGER.error("MQTT Error", e);
      return false;
    }

  }

  private boolean checkDelivery() {
    long start = System.currentTimeMillis();
    while (mqttClient.getPendingDeliveryTokens().length > 0) {
      try {
        Thread.sleep(5 * 1000);
      } catch (InterruptedException e) {
        LOGGER.error("Error", e);
      }

      long sendTimeout = 10000;
      if (sendTimeout > 0 && System.currentTimeMillis() - start > sendTimeout) {
        // send failed, goto fallback (persist messages and send later/next run
        LOGGER.error("Timeout during pending delivery");
        return false;
      }
    }
    return true;
  }

  /**
   * connects (again).
   *
   * @throws MqttException in case of error.
   */
  private boolean connect() throws MqttException {

    String persistenceDir = System.getProperty("java.io.tmpdir");

    LOGGER.info("Create MQTT Client for Server:{} clientId:{} defaultPersistenceDir:{}",
                SERVER_URL, CLIENT_ID, persistenceDir);

    mqttClient = new MqttClient(SERVER_URL, CLIENT_ID, new MqttDefaultFilePersistence(persistenceDir));

    mqttConnectOptions.setUserName(USER_NAME);
    mqttConnectOptions.setPassword(PASSWORD.toCharArray());

    // don't clean
    mqttConnectOptions.setCleanSession(false);

//    mqttClient.connect(mqttConnectOptions);
    mqttClient.connect();
    return mqttClient.isConnected();
  }

  /**
   * shutdown hook.
   */
  public void shutdown() {
    if (mqttClient != null && mqttClient.isConnected()) {
      try {
        mqttClient.disconnect();
      } catch (MqttException e) {
        LOGGER.error("MQTT Error", e);
      }
    }
  }

  public static void main(String[] args) {
    MqttProducer mqttProducer = new MqttProducer();
    mqttProducer.send("Marek message");
    try {
      Thread.sleep(1000);
      mqttProducer.send("Marek2 message");
      Thread.sleep(1000);
      mqttProducer.send("Marek3 message");
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();

      mqttProducer.shutdown();
      System.exit(0);
    }

  }
}
