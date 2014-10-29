package net.skybert.moccasin.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.ToString;

import net.skybert.moccasin.model.Arrow;

/**
 * GatheringArrow.
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@Entity
@Table(name = DBConstants.TABLE_ARROW)
@ToString
public class GatheringArrow implements Arrow
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Min(30)
  @Max(200)
  private Integer length;

  // needed by faces
  public GatheringArrow()
  {
  }

  public GatheringArrow(final Arrow pArrow)
  {
    id = pArrow.getId();
    length = pArrow.getLength();
  }

  @Override
  public Long getId()
  {
    return id;
  }

  @Override
  public Integer getLength()
  {
    return length;
  }

}
