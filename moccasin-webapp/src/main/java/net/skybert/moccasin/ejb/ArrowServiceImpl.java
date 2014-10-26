package net.skybert.moccasin.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import net.skybert.moccasin.data.GatheringArrow;
import net.skybert.moccasin.model.Arrow;
import net.skybert.moccasin.model.WildArrow;

/**
 * ArrowServiceImpl
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@Stateless
public class ArrowServiceImpl implements ArrowService
{
  @PersistenceContext
  private EntityManager entityManager;

  @PostConstruct
  public void postInit()
  {
    System.out.println("A new " + getClass().getName() + " has been created");
  }

  @Produces
  @Named("allArrows")
  @Override
  public List<Arrow> allArrows()
  {
    TypedQuery<GatheringArrow> query = entityManager.createQuery(
        "select a from GatheringArrow a", GatheringArrow.class);
    List<GatheringArrow> arrows = query.getResultList();

    List<Arrow> result = new ArrayList<>();
    for (GatheringArrow arrow : arrows)
    {
      result.add(new WildArrow(arrow.getId(), arrow.getLength()));
    }

    return result;
  }

  @Override
  public Long create(Arrow arrow)
  {
    entityManager.persist(new GatheringArrow(arrow));
    return arrow.getId();
  }

  @Override
  public Object findArrow(Long pId)
  {
    GatheringArrow arrow = entityManager.find(GatheringArrow.class, pId);
    return new WildArrow(arrow.getId(), arrow.getLength());
  }

}
