package net.skybert.moccasin.model;

/**
 * A common, public tribe, ugh!
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
public class AbstractTribe implements Tribe
{
  protected String name;
  protected Integer id;

  public String getName()
  {
    return name;
  }

  public void setName(final String name)
  {
    this.name = name;
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
