package net.skybert.moccasin.ejb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import net.skybert.moccasin.model.*;
import net.skybert.moccasin.data.*;
import net.skybert.moccasin.interceptor.Logged;

@Stateless
public class IndianServiceImpl implements IndianService
{

  @PersistenceContext
  private EntityManager entityManager;

  public long create(Indian indian)
  {
    entityManager.persist(new GatheringIndian(indian));
    return indian.getId();
  }

  // Without Produces, nothing is put into the named EL variable.
  @Logged
  @Produces
  @Named("allIndians")
  public List<Indian> allIndians()
  {
    TypedQuery<GatheringIndian> query = entityManager.createQuery(
        "select i from Indian i", GatheringIndian.class);
    List<GatheringIndian> indians = query.getResultList();
    return toIndians(indians);
  }

  private List<Indian> toIndians(final List<GatheringIndian> indians)
  {
    List<Indian> result = new ArrayList<>();
    for (GatheringIndian indian : indians)
    {
      result.add(new ServingIndian(indian));
    }
    return result;
  }

  @Logged
  @Produces
  @Named("allTribes")
  public List<Tribe> allTribes()
  {
    TypedQuery<GatheringTribe> query = entityManager.createQuery(
        "select t from Tribe t", GatheringTribe.class);
    List<GatheringTribe> tribes = query.getResultList();

    // just for fun
    if (tribes.size() > 0)
    {
      System.out.println("tribe=" + tribes.get(0).getName() + " has indians="
          + getIndiansByTribeName(tribes.get(0).getName()));
    }

    List<Tribe> result = new ArrayList<>();
    for (GatheringTribe tribe : tribes)
    {
      result.add(new ServingTribe(tribe));
    }

    return result;
  }

  @Logged
  @Override
  public Tribe findTribe(Integer id)
  {
    GatheringTribe tribe = entityManager.find(GatheringTribe.class, id);
    return new ServingTribe(tribe);
  }

  @Logged
  @Override
  public Indian findIndian(Integer id)
  {
    GatheringIndian indian = entityManager.find(GatheringIndian.class, id);
    if (indian != null)
    {
      return new ServingIndian(indian);
    }
    else
    {
      System.out.println(getClass() + " couldn't find indian with id=" + id);

      return new ServingIndian();
    }
  }

  public List<Indian> getIndiansByName(String name)
  {
    TypedQuery<GatheringIndian> query = entityManager.createQuery(
        "select i from Indian i where i.name = :indianName",
        GatheringIndian.class);
    query.setParameter("indianName", name);

    List<GatheringIndian> indians = query.getResultList();
    return toIndians(indians);
  }

  public java.util.List<Indian> getIndiansByTribeName(String name)
  {
    TypedQuery<GatheringIndian> query = entityManager.createQuery(
        "select i from Indian i where i.tribe.name = :tribeName",
        GatheringIndian.class);
    query.setParameter("tribeName", name);

    List<GatheringIndian> gatheringIndians = query.getResultList();
    return toIndians(gatheringIndians);
  }
}
