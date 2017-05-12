package com.scheidtbachmann.tank.artemis;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletContext;

/**
 *
 */
@ApplicationScoped
public class StartObserver {

  @Inject
  Scheduler scheduler;




  public void start(@Observes @Initialized(ApplicationScoped.class) ServletContext payload) {
    scheduler.start();
  }

  public void stop(@Observes @Destroyed(ApplicationScoped.class) ServletContext payload) {
      }

}
