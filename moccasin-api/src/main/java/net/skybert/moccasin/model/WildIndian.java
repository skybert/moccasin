package net.skybert.moccasin.model;

import lombok.ToString;

/**
 * A public indian, ugh!
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@ToString(callSuper = true)
public class WildIndian extends AbstractIndian
{
  // needed by jax-rs
  public WildIndian()
  {
  }

  public WildIndian(Integer id, String name, int age, Tribe tribe)
  {
    this.id = id;
    this.name = name;
    this.age = age;
    this.tribe = tribe;
  }

  @Override
  public WildTribe getTribe()
  {
    System.out.println("hello from " + getClass().getName());

    return new WildTribe();
  }

}
