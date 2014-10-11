package net.skybert.moccasin.ws.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.skybert.moccasin.data.Indian;
import net.skybert.moccasin.ejb.IndianService;

/**
 * IndianWS
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@Path("/indian")
public class IndianWS
{
  @Inject
  IndianService service;

  @GET
  @Path("ping")
  @Produces(MediaType.TEXT_PLAIN)
  public String ping()
  {
    return "pong from " + getClass().getName();
  }

  @GET
  @Path("/id/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getIndian(@QueryParam("id") Integer pId)
  {
    Indian indian = service.findIndian(pId);

    return Response.ok(indian).build();

  }

}
