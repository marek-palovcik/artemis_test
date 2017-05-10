import com.scheidtbachmann.tank.artemis.ArtemisBroker;
import com.scheidtbachmann.tank.artemis.StartObserver;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 */
@RunWith(Arquillian.class)
public class ITArtemis {

  @Deployment
  public static Archive<?> createDeployment() {
    WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "ITArtemis.war");
//        .addPackage(Destination.class.getPackage())
//        .addPackage(JMSContext.class.getPackage())
//        .addAsResource("broker.xml", "broker.xml");
//    webArchive.addClass(ArtemisBroker.class)
//        .addClass(StartObserver.class);

//    webArchive.addAsLibraries(Maven.resolver().resolve("org.apache.activemq:artemis-server:2.1.0-SNAPSHOT")
//                                  .withTransitivity().asFile());
////    webArchive.addAsLibraries(Maven.resolver().resolve("org.apache.activemq:artemis-core:2.1.0-SNAPSHOT")
////                                  .withTransitivity().asFile());
//    webArchive.addAsLibraries(Maven.resolver().resolve("org.apache.activemq:artemis-jms-server:2.1.0-SNAPSHOT")
//                                  .withTransitivity().asFile());
////    webArchive.addAsLibraries(Maven.resolver().resolve("org.apache.activemq:artemis-jms-client:2.1.0-SNAPSHOT")
////                                  .withTransitivity().asFile());
//    webArchive.addAsLibraries(Maven.resolver().resolve("org.apache.activemq:artemis-mqtt-protocol:2.1.0-SNAPSHOT")
//                                  .withTransitivity().asFile());
////    webArchive.addAsLibraries(Maven.resolver().resolve("org.apache.activemq:artemis-openwire-protocol:2.0.0")
////                                  .withTransitivity().asFile());
////    webArchive.addAsLibraries(Maven.resolver().resolve("org.apache.activemq:artemis-hornetq-protocol:2.0.0")
////                                  .withTransitivity().asFile());
////    webArchive.addAsLibraries(Maven.resolver().resolve("org.apache.activemq:artemis-stomp-protocol:1.0.0")
////                                  .withTransitivity().asFile());
////    webArchive.addAsLibraries(Maven.resolver().resolve("org.apache.activemq:artemis-amqp-protocol:1.0.0")
////                                  .withTransitivity().asFile());
//
//    webArchive.addAsLibraries(Maven.resolver().resolve("org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.1.0")
//                                  .withoutTransitivity().asFile());

    return webArchive;
  }


  @Test
  public void needTest() {
    Assert.assertTrue(true);
    if (1==1)return;
//    System.out.println("Run test ");
//    try {
//      Thread.sleep(30 * 1000);
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
//    Assert.assertTrue(true);
//    System.out.println("Run test end ");
  }
}
