package net.skybert.moccasin.ws.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.enterprise.context.RequestScoped;

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
