package net.skybert.moccasin.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

/**
 * PipeSmoke is needed to be able to inject different kinds of {@link *
 * SmokeEvent}s into the beans firing them.
 * 
 * If we didn't have this qualifier, we'd have to have just one kind of
 * SmokeEvent and use <code>@Inject @Any</code> when injecting the event beans.
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD,
    ElementType.PARAMETER })
public @interface PipeSmoke
{

}
