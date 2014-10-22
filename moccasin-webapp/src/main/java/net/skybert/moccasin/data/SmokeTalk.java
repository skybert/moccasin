package net.skybert.moccasin.data;

import javax.persistence.*;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * SmokeTalk
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@Entity
@EqualsAndHashCode
@ToString
public class SmokeTalk
{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private Integer numberOfWintersSinceLastTalk;

  public Long getId()
  {
    return id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Integer getNumberOfWintersSinceLastTalk()
  {
    return numberOfWintersSinceLastTalk;
  }

  public void setNumberOfWintersSinceLastTalk(Integer numberOfWintersSinceLastTalk)
  {
    this.numberOfWintersSinceLastTalk = numberOfWintersSinceLastTalk;
  }
}
