# Challenges & solutions

> Caused by: org.jboss.weld.exceptions.DeploymentException: WELD-001408
> Unsatisfied dependencies for type [IndianService] with qualifiers
> [@Named] at injection point [[field] @Inject @Named
> net.skybert.model.IndianEntry.service]

Because I had the second ```@Named``` here:
```
@Model
public class IndianEntry implements Serializable
{
  @Named @Produces private Indian indian = new Indian();
  @Named @Inject IndianService service;
}
```
Why?

----

> javax.ejb.EJBException: java.lang.IllegalArgumentException:
> org.hibernate.hql.internal.ast.QuerySyntaxException: indian is not
> mapped [select i from indian i]

Because of I had a lower case "indian" in the query (!) Capitalising
it solved the problem:

```
TypedQuery<Indian> query = entityManager.createQuery( "select i
from indian i", Indian.class);
```
----

> Caused by: org.jboss.weld.exceptions.DefinitionException: WELD-000068
> Method Producer Method [List<Tribe>] with qualifiers
> [@Any @Tribes @Named] declared as [[method] @Logged @Produces @Tribes
> @Named public net.skybert.ejb.IndianServiceImpl.allTribes()] must be
> declared on a business interface of Session bean
> [class net.skybert.ejb.IndianServiceImpl with qualifiers [@Any @Default];
> local interfaces are [IndianService]

I had declared the method in the impl class (the EJB), but not in the
interface (the Local interface).

---

> javax.servlet.ServletException: HV000030: No validator could be
> found for type: java.lang.Integer.
> javax.faces.webapp.FacesServlet.service(FacesServlet.java:606)


----
When you have @Produces on a method bean, the return type in that
class must be unique within the class.

---

> Caused by: java.lang.IllegalArgumentException: Named query not found: select i from Indian i where i.tribe.name = :tribeName
>   at org.hibernate.ejb.AbstractEntityManagerImpl.createNamedQuery(AbstractEntityManagerImpl.java:665) [hibernate-entitymanager-4.2.7.SP1-redhat-3.jar:4.2.7.SP1-redhat-3]
>   at org.jboss.as.jpa.container.AbstractEntityManager.createNamedQuery(AbstractEntityManager.java:79) [jboss-as-jpa-7.3.0.Final-redhat-14.jar:7.3.0.Final-redhat-14]
>   at net.skybert.ejb.IndianServiceImpl.getIndiansByTribeName(IndianServiceImpl.java:89) [classes:]

I did:
    TypedQuery<Tribe> query = entityManager.createNamedQuery("...");
instead of:
    TypedQuery<Tribe> query = entityManager.createQuery("....");

## jackson and hibernate lazy loading

> WARN  [org.jboss.resteasy.core.SynchronousDispatcher] (http-/127.0.0.1:8080-1) Failed executing GET /indian/id/3: org.jboss.resteasy.spi.WriterException: org.codehaus.jackson.map.JsonMappingException: failed to lazily initialize a collection of role: net.skybert.moccasin.data.Tribe.indians, could not initialize proxy - no Session (through reference chain: net.skybert.moccasin.data.Indian["tribe"]->net.skybert.moccasin.data.Tribe["indians"])
>     at org.jboss.resteasy.core.ServerResponse.writeTo(ServerResponse.java:262) [resteasy-jaxrs-2.3.7.Final-redhat-2.jar:2.3.7.Final-redhat-2]
>     at org.jboss.resteasy.core.SynchronousDispatcher.writeJaxrsResponse(SynchronousDispatcher.java:616) [resteasy-jaxrs-2.3.7.Final-redhat-2.jar:2.3.7.Final-redhat-2]
>     at org.jboss.resteasy.core.SynchronousDispatcher.invoke(SynchronousDispatcher.java:528) [resteasy-jaxrs-2.3.7.Final-redhat-2.jar:2.3.7.Final-redhat-2]
>     at org.jboss.resteasy.core.SynchronousDispatcher.invoke(SynchronousDispatcher.java:126) [resteasy-jaxrs-2.3.7.Final-redhat-2.jar:2.3.7.Final-redhat-2]
>     at org.jboss.resteasy.plugins.server.servlet.ServletContainerDispatcher.service(ServletContainerDispatcher.java:208) [resteasy-jaxrs-2.3.7.Final-redhat-2.jar:2.3.7.Final-redhat-2]
>     at org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher.service(HttpServletDispatcher.java:55) [resteasy-jaxrs-2.3.7.Final-redhat-2.jar:2.3.7.Final-redhat-2]
>     at org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher.service(HttpServletDispatcher.java:50) [resteasy-jaxrs-2.3.7.Final-redhat-2.jar:2.3.7.Final-redhat-2]
>     at javax.servlet.http.HttpServlet.service(HttpServlet.java:847) [jboss-servlet-api_3.0_spec-1.0.2.Final-redhat-1.jar:1.0.2.Final-redhat-1]
>     at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:295) [jbossweb-7.2.2.Final-redhat-1.jar:7.2.2.Final-redhat-1]
>     at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:214) [jbossweb-7.2.2.Final-redhat-1.jar:7.2.2.Final-redhat-1]
>     at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:230) [jbossweb-7.2.2.Final-redhat-1.jar:7.2.2.Final-redhat-1]
>     at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:149) [jbossweb-7.2.2.Final-redhat-1.jar:7.2.2.Final-redhat-1]
>     at org.jboss.as.jpa.interceptor.WebNonTxEmCloserValve.invoke(WebNonTxEmCloserValve.java:50) [jboss-as-jpa-7.3.0.Final-redhat-14.jar:7.3.0.Final-redhat-14]
>     at org.jboss.as.jpa.interceptor.WebNonTxEmCloserValve.invoke(WebNonTxEmCloserValve.java:50) [jboss-as-jpa-7.3.0.Final-redhat-14.jar:7.3.0.Final-redhat-14]
>     at org.jboss.as.web.security.SecurityContextAssociationValve.invoke(SecurityContextAssociationValve.java:169) [jboss-as-web-7.3.0.Final-redhat-14.jar:7.3.0.Final-redhat-14]
>     at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:145) [jbossweb-7.2.2.Final-redhat-1.jar:7.2.2.Final-redhat-1]
>     at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:97) [jbossweb-7.2.2.Final-redhat-1.jar:7.2.2.Final-redhat-1]
>     at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:102) [jbossweb-7.2.2.Final-redhat-1.jar:7.2.2.Final-redhat-1]
>     at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:336) [jbossweb-7.2.2.Final-redhat-1.jar:7.2.2.Final-redhat-1]
>     at org.apache.coyote.http11.Http11Processor.process(Http11Processor.java:856) [jbossweb-7.2.2.Final-redhat-1.jar:7.2.2.Final-redhat-1]
>     at org.apache.coyote.http11.Http11Protocol$Http11ConnectionHandler.process(Http11Protocol.java:653) [jbossweb-7.2.2.Final-redhat-1.jar:7.2.2.Final-redhat-1]
>     at org.apache.tomcat.util.net.JIoEndpoint$Worker.run(JIoEndpoint.java:920) [jbossweb-7.2.2.Final-redhat-1.jar:7.2.2.Final-redhat-1]
>     at java.lang.Thread.run(Thread.java:745) [rt.jar:1.7.0_65]
> Caused by: org.codehaus.jackson.map.JsonMappingException: failed to lazily initialize a collection of role: net.skybert.moccasin.data.Tribe.indians, could not initialize proxy - no Session (through reference chain: net.skybert.moccasin.data.Indian["tribe"]->net.skybert.moccasin.data.Tribe["indians"])
>     at org.codehaus.jackson.map.JsonMappingException.wrapWithPath(JsonMappingException.java:218)
>     at org.codehaus.jackson.map.JsonMappingException.wrapWithPath(JsonMappingException.java:183)
>     at org.codehaus.jackson.map.ser.std.SerializerBase.wrapAndThrow(SerializerBase.java:140)
>     at org.codehaus.jackson.map.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:158)
>     at org.codehaus.jackson.map.ser.BeanSerializer.serialize(BeanSerializer.java:112)
>     at org.codehaus.jackson.map.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:446)
>     at org.codehaus.jackson.map.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:150)
>     at org.codehaus.jackson.map.ser.BeanSerializer.serialize(BeanSerializer.java:112)
>     at org.codehaus.jackson.map.ser.StdSerializerProvider._serializeValue(StdSerializerProvider.java:610)
>     at org.codehaus.jackson.map.ser.StdSerializerProvider.serializeValue(StdSerializerProvider.java:256)
>     at org.codehaus.jackson.map.ObjectMapper.writeValue(ObjectMapper.java:1613)
>     at org.codehaus.jackson.jaxrs.JacksonJsonProvider.writeTo(JacksonJsonProvider.java:558)
>     at org.jboss.resteasy.core.interception.MessageBodyWriterContextImpl.proceed(MessageBodyWriterContextImpl.java:117) [resteasy-jaxrs-2.3.7.Final-redhat-2.jar:2.3.7.Final-redhat-2]
>     at org.jboss.resteasy.plugins.providers.jackson.JacksonJsonpInterceptor.write(JacksonJsonpInterceptor.java:112) [resteasy-jackson-provider-2.3.7.Final-redhat-2.jar:2.3.7.Final-redhat-2]
>     at org.jboss.resteasy.core.interception.MessageBodyWriterContextImpl.proceed(MessageBodyWriterContextImpl.java:123) [resteasy-jaxrs-2.3.7.Final-redhat-2.jar:2.3.7.Final-redhat-2]
>     at org.jboss.resteasy.plugins.interceptors.encoding.GZIPEncodingInterceptor.write(GZIPEncodingInterceptor.java:104) [resteasy-jaxrs-2.3.7.Final-redhat-2.jar:2.3.7.Final-redhat-2]
>     at org.jboss.resteasy.core.interception.MessageBodyWriterContextImpl.proceed(MessageBodyWriterContextImpl.java:123) [resteasy-jaxrs-2.3.7.Final-redhat-2.jar:2.3.7.Final-redhat-2]
>     at org.jboss.resteasy.core.ServerResponse.writeTo(ServerResponse.java:250) [resteasy-jaxrs-2.3.7.Final-redhat-2.jar:2.3.7.Final-redhat-2]
>     ... 22 more
> Caused by: org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: net.skybert.moccasin.data.Tribe.indians, could not initialize proxy - no Session
>     at org.hibernate.collection.internal.AbstractPersistentCollection.throwLazyInitializationException(AbstractPersistentCollection.java:566) [hibernate-core-4.2.7.SP1-redhat-3.jar:4.2.7.SP1-redhat-3]
>     at org.hibernate.collection.internal.AbstractPersistentCollection.withTemporarySessionIfNeeded(AbstractPersistentCollection.java:186) [hibernate-core-4.2.7.SP1-redhat-3.jar:4.2.7.SP1-redhat-3]
>     at org.hibernate.collection.internal.AbstractPersistentCollection.initialize(AbstractPersistentCollection.java:545) [hibernate-core-4.2.7.SP1-redhat-3.jar:4.2.7.SP1-redhat-3]
>     at org.hibernate.collection.internal.AbstractPersistentCollection.read(AbstractPersistentCollection.java:124) [hibernate-core-4.2.7.SP1-redhat-3.jar:4.2.7.SP1-redhat-3]
>     at org.hibernate.collection.internal.PersistentBag.iterator(PersistentBag.java:266) [hibernate-core-4.2.7.SP1-redhat-3.jar:4.2.7.SP1-redhat-3]
>     at org.codehaus.jackson.map.ser.std.CollectionSerializer.serializeContents(CollectionSerializer.java:45)
>     at org.codehaus.jackson.map.ser.std.CollectionSerializer.serializeContents(CollectionSerializer.java:23)
>     at org.codehaus.jackson.map.ser.std.AsArraySerializerBase.serialize(AsArraySerializerBase.java:86)
>     at org.codehaus.jackson.map.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:446)
>     at org.codehaus.jackson.map.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:150)
>     ... 36 more

