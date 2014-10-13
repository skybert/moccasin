package net.skybert.moccasin.ws.rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.skybert.moccasin.model.*;

/**
 * A REST Client for Moccasin.
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
public class MoccasinClient
{

  public static void main(String[] args)
  {
    Client client = ClientBuilder.newClient();
    String url = "http://localhost:8080/moccasin-webapp-1.0/rest-ws/indian/id/1";
    WebTarget target = client.target(url);
    Response response = target.request().accept(MediaType.APPLICATION_JSON)
        .get();
    Indian indian = response.readEntity(WildIndian.class);
    response.close();

    System.out.println(MoccasinClient.class.getName() + " received indian="
        + indian + " from url=" + url + " ugh!");

  }
}
