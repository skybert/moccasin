package net.skybert.moccasin.ws.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * RootWS
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@Path("/")
public class RootWS
{
  @GET
  public String ping()
  {
    return "Pong from " + getClass().getName();
  }

}
