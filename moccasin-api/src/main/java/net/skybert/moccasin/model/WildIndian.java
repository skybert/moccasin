package net.skybert.moccasin.model;

import lombok.ToString;

/**
 * A public indian, ugh!
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@ToString(callSuper = true)
public class WildIndian implements Indian
// extends AbstractIndian
{
  protected String name;
  protected Integer age;
  protected Tribe tribe;
  protected Integer id;

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

  public void setTribe(WildTribe tribe)
  {
    this.tribe = tribe;
  }

  @Override
  public WildTribe getTribe()
  {
    System.out.println("hello from " + getClass().getName());
    if (tribe != null)
    {
      return new WildTribe(tribe.getId(), tribe.getName());
    }
    else
    {
      return new WildTribe();
    }
  }

  @Override
  public Integer getId()
  {
    return id;
  }

  @Override
  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  @Override
  public Integer getAge()
  {
    return age;
  }

  public void setAge(Integer age)
  {
    this.age = age;
  }
}
