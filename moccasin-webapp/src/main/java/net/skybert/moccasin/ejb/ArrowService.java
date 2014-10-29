package net.skybert.moccasin.ejb;

import java.util.List;

import javax.ejb.Local;

import net.skybert.moccasin.model.Arrow;

@Local
public interface ArrowService
{
  public List<Arrow> allArrows();

  public Long create(Arrow arrow);

  public Object findArrow(Long pId);
}
