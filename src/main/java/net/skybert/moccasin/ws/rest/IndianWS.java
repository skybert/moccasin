package net.skybert.moccasin.ws.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.skybert.moccasin.data.Indian;
import net.skybert.moccasin.ejb.IndianService;
import net.skybert.moccasin.interceptor.Logged;

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
  @Produces(MediaType.TEXT_PLAIN)
  public String ping()
  {
    return "pong from " + getClass().getName();
  }

  @Logged
  @GET
  @Path("/id/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getIndian(@PathParam("id") Integer pId)
  {
    Indian indian = service.findIndian(pId);
    if (indian == null)
    {
      final String message = "No indians with id " + pId + ", ugh!\n";
      return Response.status(Response.Status.NOT_FOUND).entity(message).build();
    }
    else
    {
      return Response.ok(indian).build();
    }

  }

  @GET
  @Path("/empty")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getIndian()
  {
    Indian indian = new Indian();

    return Response.ok(indian).build();

  }

}
