package net.skybert.moccasin.ejb;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.inject.Alternative;

import net.skybert.moccasin.data.SmokeTalk;
import net.skybert.moccasin.model.Indian;
import net.skybert.moccasin.model.Tribe;
import net.skybert.moccasin.model.WildIndian;
import net.skybert.moccasin.model.WildTribe;

@Alternative
public class IndianServiceMocka implements IndianService, Serializable
{
  private static final long serialVersionUID = -75192206274558695L;
  static final String MOCKA_INDIAN_NAME = "Mock the Stone";

  @Override
  public List<Indian> allIndians()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Tribe> allTribes()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Integer create(Indian indian)
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object findTribe(Integer valueOf)
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Indian findIndian(Integer valueOf)
  {
    System.out.println("findIndian in" + getClass().getName());
    return new WildIndian(1, MOCKA_INDIAN_NAME, 55, new WildTribe());
  }

  @Override
  public List<Indian> getIndiansByName(String name)
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Indian> getIndiansByTribeName(String name)
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Long remember(SmokeTalk talk)
  {
    // TODO Auto-generated method stub
    return null;
  }

}