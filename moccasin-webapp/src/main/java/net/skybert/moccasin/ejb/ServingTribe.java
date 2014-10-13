package net.skybert.moccasin.ejb;

import net.skybert.moccasin.model.*;
import net.skybert.moccasin.data.*;

/**
 * ServingTribe
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
public class ServingTribe extends AbstractTribe
{
  public ServingTribe(GatheringTribe tribe)
  {
    setId(tribe.getId());
    setName(tribe.getName());
  }
}
