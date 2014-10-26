package net.skybert.moccasin.event;

import java.util.Date;

import lombok.ToString;

/**
 * SmokeEvent. Every time an indian blows some smoke, a smoke event is
 * triggered.
 *
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@ToString
public class SmokeEvent
{
  private Date timeOfSmoke;

  public SmokeEvent(Date pTimeOSmoke)
  {
    timeOfSmoke = pTimeOSmoke;
  }

  public Date timeOfSmoke()
  {
    return timeOfSmoke;
  }

}
