package net.skybert.moccasin.ejb;

import javax.ejb.Local;

import net.skybert.moccasin.data.SmokeTalk;
import net.skybert.moccasin.model.*;

import java.util.*;

@Local
public interface ArrowService
{
  public List<Arrow> allArrows();

  public Long create(Arrow arrow);

  public Object findArrow(Long pId);
}
