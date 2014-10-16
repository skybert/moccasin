package net.skybert.moccasin.jms;

import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

/**
 * BuffaloStoryMemory
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@Path("/buffalo/memory")
public class BuffaloStoryMemory
{

  // TODO why don't these two @Resource annotation work?
  @Resource(mappedName = "java:/ConnectionFactory")
  private ConnectionFactory connectionFactory;

  @Resource(mappedName = "java:/topic/buffalo")
  private Topic topic;

  @POST
  public Response createMessage(@Context HttpServletRequest request)
      throws JMSException, URISyntaxException, NamingException
  {
    System.out.println("@Resource injection produced connectionFactory="
        + connectionFactory + " and topic=" + topic);

    InitialContext ic = new InitialContext();
    connectionFactory = (ConnectionFactory) ic.lookup("/ConnectionFactory");
    topic = (Topic) ic.lookup("/topic/buffalo");

    Connection connection = null;

    try
    {
      System.out.println("connection factory=" + connectionFactory);
      System.out.println("connection topic=" + topic);

      connection = connectionFactory.createConnection();
      Session session = connection.createSession(false,
          Session.AUTO_ACKNOWLEDGE);
      Destination destination = topic;
      MessageProducer messageProducer = session.createProducer(destination);
      connection.start();
      TextMessage message = session.createTextMessage();
      message.setText("Message collected and put on the topic by "
          + getClass().getName() + " @ " + System.currentTimeMillis());
      messageProducer.send(message);
    }
    catch (JMSException je)
    {
      je.printStackTrace();
    }
    finally
    {
      if (connection != null)
      {
        connection.close();
      }

    }

    URI uri = UriBuilder.fromUri(request.getPathInfo()).build();
    return Response.created(uri).build();

  }
}
