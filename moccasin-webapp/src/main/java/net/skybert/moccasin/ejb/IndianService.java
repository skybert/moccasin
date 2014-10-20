package net.skybert.moccasin.ejb;

import javax.ejb.Local;

import net.skybert.moccasin.model.*;

import java.util.*;

@Local
public interface IndianService
{

  public List<Indian> allIndians();

  public List<Tribe> allTribes();

  public Integer create(Indian indian);

  public Object findTribe(Integer valueOf);

  public Indian findIndian(Integer valueOf);

  public List<Indian> getIndiansByName(String name);

  public List<Indian> getIndiansByTribeName(String name);
}
