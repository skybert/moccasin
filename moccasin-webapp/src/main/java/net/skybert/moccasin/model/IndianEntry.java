package net.skybert.moccasin.model;

import java.io.Serializable;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import net.skybert.moccasin.ejb.IndianService;
import net.skybert.moccasin.interceptor.Logged;

@Model
public class IndianEntry implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Named
  @Produces
  private Indian indian = new WildIndian();

  @Inject
  IndianService service;

  public Integer create()
  {
    return create(indian);
  }

  public Integer create(final Indian pIndian)
  {
    return service.create(pIndian);
  }

  @Named
  @Produces
  public Converter getTribeConverter()
  {
    return new Converter()
    {
      @Override
      @Logged
      public Object getAsObject(FacesContext context,
                                UIComponent component,
                                String value)
      {

        return service.findTribe(Integer.valueOf(value));
      }

      @Override
      @Logged
      public String getAsString(FacesContext context,
                                UIComponent component,
                                Object value)
      {
        if (value == null)
        {
          return "";
        }

        return String.valueOf(((Tribe) value).getId());
      }
    };
  }

}
