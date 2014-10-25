package net.skybert.moccasin.data;

import javax.persistence.EntityManager;

import org.junit.Assert;

/**
 * GatheringIndianTest
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
public class GatheringIndianTest
{

  private EntityManager entityManager;

  public void entity_manager_was_injected() throws Exception
  {
    Assert.assertNotNull(entityManager);
  }

}
