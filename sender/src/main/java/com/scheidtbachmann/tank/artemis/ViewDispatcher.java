package com.scheidtbachmann.tank.artemis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

/**
 *
 */
@ApplicationScoped
public class ViewDispatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewDispatcher.class);

//    @Resource(name = "tcp://127.0.0.1:61616/jms/queue/ViewSQMMessages")
//    private Queue temporalViewLookupQueue;
    //
//    @Inject
//    @JMSConnectionFactory(value="java:jboss/exported/jms/RemoteConnectionFactory")
//    private JMSContext jmsContext;


    /**
     * dispatches the change notification result.
     */
    public void dispatch() {
      LOGGER.info("dispatching change information");
      // Extract site information and send to queue
      //TODO catch exceptions and return status
      String msg = "statuschanged";
//      jmsContext.createProducer().send(temporalViewLookupQueue,msg);
    }
}
