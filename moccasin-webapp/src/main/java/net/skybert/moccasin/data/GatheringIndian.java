package net.skybert.moccasin.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import net.skybert.moccasin.model.*;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(exclude = "tribe")
@EqualsAndHashCode
@Entity
@Table(name = "indian")
public class GatheringIndian implements Indian
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(unique = true)
  private String name;

  @Min(1)
  @Max(200)
  private Integer age;

  // needed by jax-rs
  public GatheringIndian()
  {
  }

  public GatheringIndian(final Indian indian)
  {
    name = indian.getName();
    age = indian.getAge();
    tribe = new GatheringTribe((Tribe) indian.getTribe());
  }

  @ManyToOne
  // @JoinColumn(name = DBConstants.TRIBE_ID)
  private GatheringTribe tribe;

  public Integer getId()
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

  public void setTribe(GatheringTribe tribe)
  {
    this.tribe = tribe;
  }

  public Integer getAge()
  {
    return age;
  }

  public void setAge(Integer age)
  {
    this.age = age;
  }

  public GatheringTribe getTribe()
  {
    return tribe;
  }

}
