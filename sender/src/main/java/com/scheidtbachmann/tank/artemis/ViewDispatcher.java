package com.scheidtbachmann.tank.artemis;

import org.jboss.ejb3.annotation.ResourceAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.Queue;

/**
 *
 */
@ApplicationScoped
public class ViewDispatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewDispatcher.class);

//  @Resource(lookup = "ViewSQMMessages")
//    private Queue temporalViewLookupQueue;

//  @JMSDestinationDefinition(interfaceName = "name",name = "ViewSQMMessages" ,resourceAdapter = "remote-artemis")
//  @Resource(lookup = "ViewSQMMessages")
//  private Destination temporalViewLookupQueue;

    @Inject
    @JMSConnectionFactory("java:/jms/remoteCF")
    private JMSContext jmsContext;

    /**
     * dispatches the change notification result.
     */
    public void dispatch() {
      LOGGER.info("dispatching change information");
      // Extract site information and send to queue
      //TODO catch exceptions and return status
      String msg = "statuschanged";
      Queue viewSQMMessages = jmsContext.createQueue("ViewSQMMessages");
      jmsContext.createProducer().send(viewSQMMessages,msg);
    }
}
