package com.scheidtbachmann.tank.artemis;

import javax.annotation.Resource;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.inject.Inject;

/**
 *
 */
@Singleton
public class Scheduler {

  @Resource
  private TimerService timerService;

  @Inject
  ViewDispatcher view;

  public void start(){
    ScheduleExpression calculationSchedule = new ScheduleExpression();
    calculationSchedule.second("*/10");
    calculationSchedule.minute("*");
    calculationSchedule.hour("*");
    TimerConfig calcTimerConfig = new TimerConfig();
    calcTimerConfig.setPersistent(false);
    calcTimerConfig.setInfo("Kai");
    Timer timer = timerService.createCalendarTimer(calculationSchedule, calcTimerConfig);
  }

  @Timeout
  public void timer(){
    view.dispatch();
  }


}
