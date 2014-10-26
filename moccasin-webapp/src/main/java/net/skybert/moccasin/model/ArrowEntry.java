package net.skybert.moccasin.model;

import java.io.Serializable;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import net.skybert.moccasin.ejb.ArrowService;

/**
 * ArrowEntry
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@Model
public class ArrowEntry implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Named
  @Produces
  private Arrow arrow = new WildArrow();

  @Inject
  ArrowService service;

  public Long create()
  {
    return create(arrow);
  }

  public Long create(final Arrow pArrow)
  {
    return service.create(pArrow);
  }

}
