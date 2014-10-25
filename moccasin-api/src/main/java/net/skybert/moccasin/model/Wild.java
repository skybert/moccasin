package net.skybert.moccasin.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

/**
 * Wild. Makes it possible to inject Wild implementations of the "pure" model
 * interfaces, see injection of {@link Indian} and {@link Tribe}.
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD,
    ElementType.PARAMETER })
public @interface Wild
{
}
