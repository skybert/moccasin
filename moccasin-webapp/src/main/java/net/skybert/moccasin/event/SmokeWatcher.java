package net.skybert.moccasin.event;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;

import lombok.ToString;

/**
 * The SmokeWatcher {@link Observes} CDI {@link Event}s
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@ToString
public class SmokeWatcher
{
  public void watchPipeSmoke(@Observes @PipeSmoke SmokeEvent smokeEvent)
  {
    System.out.println(getClass().getName() + " smoke observed=" + smokeEvent);
  }

}
