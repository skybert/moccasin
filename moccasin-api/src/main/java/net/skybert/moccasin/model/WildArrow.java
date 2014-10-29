package net.skybert.moccasin.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.ToString;

/**
 * A public arrow, ugh!
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@ToString
public class WildArrow implements Arrow
{

  private Long id;

  @Min(30)
  @Max(200)
  private Integer length;

  public WildArrow(Long pId, Integer pLength)
  {
    id = pId;
    length = pLength;
  }

  public WildArrow()
  {
  }

  @Override
  public Integer getLength()
  {
    return length;
  }

  public void setLength(Integer length)
  {
    this.length = length;
  }

  @Override
  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

}
