package net.skybert.moccasin.ejb;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import net.skybert.moccasin.model.Indian;

/**
 * IndianServiceDecorator. Decorates some of @ IndianService} 's methods.
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@Decorator
public abstract class IndianServiceDecorator implements IndianService
{
  @Inject
  @Delegate
  @Any
  IndianService indianService;

  @Override
  public Indian findIndian(Integer id)
  {
    System.out.println(getClass()
        + " does something first with call to findIndian" + " then calls "
        + IndianService.class.getName() + "'s findIndian");

    return indianService.findIndian(id);
  }
}
