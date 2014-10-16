package net.skybert.moccasin;

import java.io.File;

import javax.inject.Inject;

import net.skybert.moccasin.model.WildIndian;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
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
