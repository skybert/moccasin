package net.skybert.moccasin.model;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import net.skybert.moccasin.data.SmokeTalk;
import net.skybert.moccasin.ejb.IndianService;

@Named("pipe")
@ConversationScoped
public class SmokingThePeacePipe implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Named
  @Produces
  private SmokeTalk talk = new SmokeTalk();

  @Inject
  IndianService service;

  @Inject
  Conversation conversation;

  public String goodToSeeYou(String name)
  {

    if (conversation.isTransient())
    {
      conversation.begin();
    }

    talk.setName(name);

    return "number-of-winters";
  }

  public String numberOfWintersSinceLastTalk(Integer pNumberOfWinters)
  {
    talk.setNumberOfWintersSinceLastTalk(pNumberOfWinters);
    service.remember(talk);
    return "prolonged-friendship";
  }

  public String walkOutOfTheTent()
  {
    conversation.end();
    return "begin-smoking";
  }

}
