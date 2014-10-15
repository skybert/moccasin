package net.skybert.moccasin.jms;

import javax.annotation.Resource;
import javax.naming.*;
import javax.inject.Inject;
import javax.jms.Connection;
import java.net.*;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.skybert.moccasin.model.*;

/**
 * BuffaloStoryTeller
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@Path("/buffalo/story")
public class BuffaloStoryTeller
{

  @Resource(mappedName = "java:/ConnectionFactory")
  private ConnectionFactory connectionFactory;

  @Resource(mappedName = "java:/queue/buffalo")
  private Queue queue;

  // @Resource(mappedName = "java:/topic/buffalo")
  // private Topic topic;

  @POST
  public Response createMessage()
    throws JMSException,
    URISyntaxException,
    NamingException
  {

    InitialContext ic = new InitialContext();
    connectionFactory = (ConnectionFactory) ic.lookup("/ConnectionFactory");
    queue = (Queue) ic.lookup("/queue/buffalo");

    Connection connection = null;

    try
    {
      System.out.println("connection factory=" + connectionFactory);
      System.out.println("connection queue=" + queue);

      connection = connectionFactory.createConnection();
      Session session = connection.createSession(false,
          Session.AUTO_ACKNOWLEDGE);
      Destination destination = queue;
      MessageProducer messageProducer = session.createProducer(destination);
      connection.start();
      TextMessage message = session.createTextMessage();
      message.setText("Message from " + getClass().getName() + " @ "
          + System.currentTimeMillis());
      messageProducer.send(message);
    }
    catch (JMSException je)
    {
      je.printStackTrace();
      if (connection != null)
      {
        connection.close();
      }

    }
    return Response.created(new URI("http://example.com")).build();

  }
}
