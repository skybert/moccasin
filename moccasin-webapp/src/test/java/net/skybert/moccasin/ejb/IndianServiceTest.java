package net.skybert.moccasin.ejb;

import java.io.File;

import javax.inject.Inject;

import net.skybert.moccasin.InjectionTest;
import net.skybert.moccasin.model.Indian;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class IndianServiceTest
{

  @Inject
  IndianService indianService;

  @Deployment
  public static JavaArchive createDeployment()
  {
    JavaArchive jar = ShrinkWrap
        .create(JavaArchive.class)
        .addPackages(true, InjectionTest.class.getPackage().getName())
        .addAsManifestResource(
            new File("src/test/resources/META-INF", "beans.xml"))
        .addAsManifestResource(
            new File("src/main/resources/META-INF", "persistence.xml"));

    return jar;
  }

  @Test
  public void indian_service_can_be_injected() throws Exception
  {
    Assert.assertNotNull(indianService);
    System.out.println(indianService.getClass().getName());
  }

  @Test
  public void the_mock_alternative_should_have_been_injected() throws Exception
  {
    Assert.assertNotNull(indianService);
    Indian indian = indianService.findIndian(1);
    Assert.assertNotNull(indian);
    Assert.assertEquals(IndianServiceMocka.MOCKA_INDIAN_NAME, indian.getName());
  }
}