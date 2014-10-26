package net.skybert.moccasin.model;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import net.skybert.moccasin.data.SmokeTalk;
import net.skybert.moccasin.ejb.IndianService;
import net.skybert.moccasin.event.PipeSmoke;
import net.skybert.moccasin.event.SmokeEvent;

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

  @Inject
  @PipeSmoke
  Event<SmokeEvent> pipeEvent;

  public String goodToSeeYou(String name)
  {
    if (conversation.isTransient())
    {
      conversation.begin();
    }

    talk.setName(name);

    pipeEvent.fire(new SmokeEvent(new Date()));

    return "number-of-winters";
  }

  public String numberOfWintersSinceLastTalk(Integer pNumberOfWinters)
  {
    talk.setNumberOfWintersSinceLastTalk(pNumberOfWinters);
    service.remember(talk);
    pipeEvent.fire(new SmokeEvent(new Date()));
    return "prolonged-friendship";
  }

  public String walkOutOfTheTent()
  {
    conversation.end();
    pipeEvent.fire(new SmokeEvent(new Date()));
    return "begin-smoking";
  }

}
