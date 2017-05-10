package com.scheidtbachmann.tank.artemis;

import com.scheidtbachmann.tank.artemis.mqtt.MqttProducer;
import com.scheidtbachmann.tank.artemis.mqtt.MqttSubscriber;

import org.apache.activemq.artemis.core.config.impl.SecurityConfiguration;
import org.apache.activemq.artemis.core.server.ActiveMQServer;
import org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ;
import org.apache.activemq.artemis.core.server.impl.ActiveMQServerImpl;
import org.apache.activemq.artemis.jms.server.embedded.EmbeddedJMS;
import org.apache.activemq.artemis.spi.core.security.ActiveMQJAASSecurityManager;
import org.apache.activemq.artemis.spi.core.security.jaas.InVMLoginModule;

import javax.enterprise.context.ApplicationScoped;

/**
 *
 */
@ApplicationScoped
public class ArtemisBroker {

  private EmbeddedJMS broker;

  public void start() {

    broker = new EmbeddedJMS();

    SecurityConfiguration securityConfig = new SecurityConfiguration();
    securityConfig.addUser("guest", "guest");
    securityConfig.addRole("guest", "guest");
    securityConfig.setDefaultUser("guest");
    ActiveMQJAASSecurityManager securityManager = new ActiveMQJAASSecurityManager(InVMLoginModule.class.getName(), securityConfig);
    broker.setSecurityManager(securityManager);

    try {
      broker.start();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
    }

  }
  public void stop(){
    try {
      broker.stop();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


}
