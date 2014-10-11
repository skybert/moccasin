package net.skybert.moccasin.ws.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * MoccasinApplication
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@ApplicationPath("/rest-ws")
public class MoccasinApplication extends Application
{
  @Override
  public Set<Class<?>> getClasses()
  {
    HashSet<Class<?>> classes = new HashSet<Class<?>>();
    classes.add(IndianWS.class);
    return classes;
  }
}
