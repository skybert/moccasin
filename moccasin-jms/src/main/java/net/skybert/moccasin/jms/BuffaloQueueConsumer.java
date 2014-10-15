package net.skybert.moccasin.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * QueueConsumer
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@MessageDriven(name = "BuffaloQueueConsumer", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/buffalo"),
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class BuffaloQueueConsumer implements MessageListener
{
  @Override
  public void onMessage(Message message)
  {
    TextMessage textMessage = null;
    try
    {
      if (message instanceof TextMessage)
      {
        textMessage = (TextMessage) message;
        System.out.println(getClass().getName() + " received message="
            + textMessage.getText());
      }
      else
      {
        System.out.println(getClass().getName()
            + " didn't understand message of type="
            + message.getClass().getName());
      }

    }
    catch (JMSException e)
    {
      throw new RuntimeException(e);
    }

  }
}
