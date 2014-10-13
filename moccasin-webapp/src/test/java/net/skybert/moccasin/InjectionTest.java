package net.skybert.moccasin;

import javax.inject.Inject;

import java.io.*;

import net.skybert.moccasin.model.*;
import net.skybert.moccasin.data.*;
import net.skybert.moccasin.interceptor.Logged;
import net.skybert.moccasin.interceptor.Sauron;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * InjectionTest
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@RunWith(Arquillian.class)
public class InjectionTest
{
  @Inject
  WildIndian indian;

  @Deployment
  public static JavaArchive createDeployment()
  {
    JavaArchive jar = ShrinkWrap
        .create(JavaArchive.class)
        .addPackages(true, InjectionTest.class.getPackage().getName())
        .addAsManifestResource(
            new File("src/main/resources/META-INF", "beans.xml"))
        .addAsManifestResource(
            new File("src/main/resources/META-INF", "persistence.xml"));

    System.out.println("jar=" + jar.toString(true));
    return jar;
  }

  @Test
  public void an_indian_can_be_injected()
  {
    Assert.assertNotNull(indian);
    Assert.assertNull(indian.getId());
  }

}
