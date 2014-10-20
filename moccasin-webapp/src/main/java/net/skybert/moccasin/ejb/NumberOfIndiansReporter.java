package net.skybert.moccasin.ejb;

import java.util.*;
import javax.inject.Inject;
import javax.ejb.*;
import net.skybert.moccasin.model.*;

/**
 * NumberOfIndiansReporter reports on the number of indians.
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@Stateless
public class NumberOfIndiansReporter
{
  @Inject
  IndianService service;

  @Schedule(minute = "*/3", hour = "*")
  public void reportNumberOfIndians()
  {
    List<Indian> indians = service.allIndians();
    if (indians != null)
    {
      System.out.println(getClass() + " says there are now " + indians.size()
          + " indians");
    }
  }

}
