package net.skybert.moccasin.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A common, public indian, ugh!
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@ToString
@EqualsAndHashCode
public abstract class AbstractIndian implements Indian
{
  protected String name;
  protected Integer age;
  protected Tribe tribe;
  protected Integer id;

  public String getName()
  {
    return name;
  }

  public void setName(final String name)
  {
    this.name = name;
  }

  public Integer getAge()
  {
    return age;
  }

  public void setAge(final Integer age)
  {
    this.age = age;
  }

  public Object getTribe()
  {
    return tribe;
  }

  public void setTribe(final Tribe tribe)
  {
    this.tribe = tribe;
  }

  public Integer getId()
  {
    return id;
  }

  public void setId(final Integer id)
  {
    this.id = id;
  }
}
