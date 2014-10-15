package net.skybert.moccasin.ejb;

import net.skybert.moccasin.model.*;
import net.skybert.moccasin.data.*;

/**
 * ServingIndian
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
public class ServingIndian extends AbstractIndian
{
  public ServingIndian(GatheringIndian indian)
  {
    setId(indian.getId());
    setAge(indian.getAge());
    setName(indian.getName());
    setTribe(indian.getTribe());
  }

  public ServingIndian()
  {
  }
}
