package net.skybert.moccasin.interceptor;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Logged
@Interceptor
public class Sauron implements Serializable
{
  private static final long serialVersionUID = 1L;

  @AroundInvoke
  public Object logIt(InvocationContext context) throws Exception
  {
    Object returnValue = context.proceed();
    for (Object parameter : context.getParameters())
    {
      System.out.println(getClass().getSimpleName() + " sees that "
          + context.getMethod().getName() + " was called with "
          + parameter.getClass().getSimpleName() + "(" + parameter + ")");
    }

    return returnValue;
  }
}
