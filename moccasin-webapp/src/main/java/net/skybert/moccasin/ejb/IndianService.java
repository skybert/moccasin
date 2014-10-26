package net.skybert.moccasin.ejb;

import java.util.List;

import javax.ejb.Local;

import net.skybert.moccasin.data.SmokeTalk;
import net.skybert.moccasin.model.Indian;
import net.skybert.moccasin.model.Tribe;

@Local
public interface IndianService
{

  public List<Indian> allIndians();

  public List<Tribe> allTribes();

  public Integer create(Indian indian);

  public Object findTribe(Integer valueOf);

  public Indian findIndian(Integer id);

  public List<Indian> getIndiansByName(String name);

  public List<Indian> getIndiansByTribeName(String name);

  public Long remember(SmokeTalk talk);
}
