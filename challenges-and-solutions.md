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

This is because I was using the database entity objects in the view
layer. Since Hibernate per default lazy loads the fields, such as "one
indian has a tribe", the ```getTribe``` method call is invoked first
in the view layer. Since the hibernate session (when the data was
fetched from the DB) is closed long time ago, Hibernate throws such an
exception. The remedy is to create dedicated, detached value objects
for the view layer.

## After securing EJB, I get invalid User after logging in

> 18:51:25,343 ERROR [org.apache.catalina.core.ContainerBase.[jboss.web].[default-host].[/moccasin-webapp-1.0].[Faces Servlet]] (http-localhost.localdomain/127.0.0.1:8080-1) JBWEB000236: Servlet.service() for servlet Faces Servlet threw exception: javax.ejb.EJBAccessException: JBAS013323: Invalid User
>   at org.jboss.as.ejb3.security.SecurityContextInterceptor$1.run(SecurityContextInterceptor.java:54) [jboss-as-ejb3-7.3.0.Final-redhat-14.jar:7.3.0.Final-redhat-14]
>   at org.jboss.as.ejb3.security.SecurityContextInterceptor$1.run(SecurityContextInterceptor.java:45) [jboss-as-ejb3-7.3.0.Final-redhat-14.jar:7.3.0.Final-redhat-14]
>   at org.jboss.as.ejb3.security.SecurityContextInterceptor.processInvocation(SecurityContextInterceptor.java:78) [jboss-as-ejb3-7.3.0.Final-redhat-14.jar:7.3.0.Final-redhat-14]
>   at org.jboss.invocation.InterceptorContext.proceed(InterceptorContext.java:288) [jboss-invocation-1.1.2.Final-redhat-1.jar:1.1.2.Final-redhat-1]
>   at org.jboss.as.ejb3.component.interceptors.ShutDownInterceptorFactory$1.processInvocation(ShutDownInterceptorFactory.java:64) [jboss-as-ejb3-7.3.0.Final-redhat-14.jar:7.3.0.Final-redhat-14]
>   at org.jboss.invocation.InterceptorContext.proceed(InterceptorContext.java:288) [jboss-invocation-1.1.2.Final-redhat-1.jar:1.1.2.Final-redhat-1]
>   at org.jboss.as.ejb3.component.interceptors.LoggingInterceptor.processInvocation(LoggingInterceptor.java:59) [jboss-as-ejb3-7.3.0.Final-redhat-14.jar:7.3.0.Final-redhat-14]
>   at org.jboss.invocation.InterceptorContext.proceed(InterceptorContext.java:288) [jboss-invocation-1.1.2.Final-redhat-1.jar:1.1.2.Final-redhat-1]
>   at org.jboss.as.ee.component.NamespaceContextInterceptor.processInvocation(NamespaceContextInterceptor.java:50) [jboss-as-ee-7.3.0.Final-redhat-14.jar:7.3.0.Final-redhat-14]
>   at org.jboss.invocation.InterceptorContext.proceed(InterceptorContext.java:288) [jboss-invocation-1.1.2.Final-redhat-1.jar:1.1.2.Final-redhat-1]
>   at org.jboss.as.ejb3.component.interceptors.AdditionalSetupInterceptor.processInvocation(AdditionalSetupInterceptor.java:55) [jboss-as-ejb3-7.3.0.Final-redhat-14.jar:7.3.0.Final-redhat-14]
>   at org.jboss.invocation.InterceptorContext.proceed(InterceptorContext.java:288) [jboss-invocation-1.1.2.Final-redhat-1.jar:1.1.2.Final-redhat-1]
>   at org.jboss.as.ee.component.TCCLInterceptor.processInvocation(TCCLInterceptor.java:45) [jboss-as-ee-7.3.0.Final-redhat-14.jar:7.3.0.Final-redhat-14]
>   at org.jboss.invocation.InterceptorContext.proceed(InterceptorContext.java:288) [jboss-invocation-1.1.2.Final-redhat-1.jar:1.1.2.Final-redhat-1]
>   at org.jboss.invocation.ChainedInterceptor.processInvocation(ChainedInterceptor.java:61) [jboss-invocation-1.1.2.Final-redhat-1.jar:1.1.2.Final-redhat-1]
>   at org.jboss.as.ee.component.ViewService$View.invoke(ViewService.java:165) [jboss-as-ee-7.3.0.Final-redhat-14.jar:7.3.0.Final-redhat-14]
>   at org.jboss.as.ee.component.ViewDescription$1.processInvocation(ViewDescription.java:182) [jboss-as-ee-7.3.0.Final-redhat-14.jar:7.3.0.Final-redhat-14]
>   at org.jboss.invocation.InterceptorContext.proceed(InterceptorContext.java:288) [jboss-invocation-1.1.2.Final-redhat-1.jar:1.1.2.Final-redhat-1]
>   at org.jboss.invocation.ChainedInterceptor.processInvocation(ChainedInterceptor.java:61) [jboss-invocation-1.1.2.Final-redhat-1.jar:1.1.2.Final-redhat-1]
>   at org.jboss.as.ee.component.ProxyInvocationHandler.invoke(ProxyInvocationHandler.java:72) [jboss-as-ee-7.3.0.Final-redhat-14.jar:7.3.0.Final-redhat-14]
>   at net.skybert.moccasin.ejb.IndianService$$$view2.allIndians(Unknown Source) [moccasin-webapp-1.0.jar:]
>   at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) [rt.jar:1.7.0_71]
>   at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57) [rt.jar:1.7.0_71]
>   at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) [rt.jar:1.7.0_71]
>   at java.lang.reflect.Method.invoke(Method.java:606) [rt.jar:1.7.0_71]
>   at org.jboss.weld.util.reflection.SecureReflections$13.work(SecureReflections.java:267) [weld-core-1.1.16.Final-redhat-1.jar:1.1.16.Final-redhat-1]
>   at org.jboss.weld.util.reflection.SecureReflectionAccess.run(SecureReflectionAccess.java:52) [weld-core-1.1.16.Final-redhat-1.jar:1.1.16.Final-redhat-1]
>   at org.jboss.weld.util.reflection.SecureReflectionAccess.runAsInvocation(SecureReflectionAccess.java:137) [weld-core-1.1.16.Final-redhat-1.jar:1.1.16.Final-redhat-1]
>   at org.jboss.weld.util.reflection.SecureReflections.invoke(SecureReflections.java:263) [weld-core-1.1.16.Final-redhat-1.jar:1.1.16.Final-redhat-1]
>   at org.jboss.weld.bean.proxy.EnterpriseBeanProxyMethodHandler.invoke(EnterpriseBeanProxyMethodHandler.java:115) [weld-core-1.1.16.Final-redhat-1.jar:1.1.16.Final-redhat-1]
>   at org.jboss.weld.bean.proxy.EnterpriseTargetBeanInstance.invoke(EnterpriseTargetBeanInstance.java:56) [weld-core-1.1.16.Final-redhat-1.jar:1.1.16.Final-redhat-1]
>   at org.jboss.weld.bean.proxy.ProxyMethodHandler.invoke(ProxyMethodHandler.java:105) [weld-core-1.1.16.Final-redhat-1.jar:1.1.16.Final-redhat-1]
>   at net.skybert.moccasin.ejb.IndianService$-1532938683$Proxy$_$$_Weld$Proxy$.allIndians(IndianService$-1532938683$Proxy$_$$_Weld$Proxy$.java) [moccasin-webapp-1.0.jar:]
>   at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) [rt.jar:1.7.0_71]
>   at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57) [rt.jar:1.7.0_71]
>   at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) [rt.jar:1.7.0_71]
>   at java.lang.reflect.Method.invoke(Method.java:606) [rt.jar:1.7.0_71]
>   at org.jboss.weld.util.reflection.SecureReflections$13.work(SecureReflections.java:267) [weld-core-1.1.16.Final-redhat-1.jar:1.1.16.Final-redhat-1]
>   at org.jboss.weld.util.reflection.SecureReflectionAccess.run(SecureReflectionAccess.java:52) [weld-core-1.1.16.Final-redhat-1.jar:1.1.16.Final-redhat-1]
>   at org.jboss.weld.util.reflection.SecureReflectionAccess.runAsInvocation(SecureReflectionAccess.java:137) [weld-core-1.1.16.Final-redhat-1.jar:1.1.16.Final-redhat-1]
>   at org.jboss.weld.util.reflection.SecureReflections.invoke(SecureReflections.java:263) [weld-core-1.1.16.Final-redhat-1.jar:1.1.16.Final-redhat-1]
>   at org.jboss.weld.introspector.jlr.WeldMethodImpl.invokeOnInstance(WeldMethodImpl.java:164) [weld-core-1.1.16.Final-redhat-1.jar:1.1.16.Final-redhat-1]
>   at org.jboss.weld.injection.MethodInjectionPoint.invokeOnInstance(MethodInjectionPoint.java:137) [weld-core-1.1.16.Final-redhat-1.jar:1.1.16.Final-redhat-1]
>   at org.jboss.weld.bean.ProducerMethod$ProducerMethodProducer.produce(ProducerMethod.java:136) [weld-core-1.1.16.Final-redhat-1.jar:1.1.16.Final-redhat-1]
>   at org.jboss.weld.bean.AbstractProducerBean$AbstractProducer.produce(AbstractProducerBean.java:319) [weld-core-1.1.16.Final-redhat-1.jar:1.1.16.Final-redhat-1]
>   at org.jboss.weld.bean.AbstractProducerBean.create(AbstractProducerBean.java:307) [weld-core-1.1.16.Final-redhat-1.jar:1.1.16.Final-redhat-1]
>   at org.jboss.weld.context.unbound.DependentContextImpl.get(DependentContextImpl.java:68) [weld-core-1.1.16.Final-redhat-1.jar:1.1.16.Final-redhat-1]
>   at org.jboss.weld.manager.BeanManagerImpl.getReference(BeanManagerImpl.java:612) [weld-core-1.1.16.Final-redhat-1.jar:1.1.16.Final-redhat-1]
>   at org.jboss.weld.el.AbstractWeldELResolver.lookup(AbstractWeldELResolver.java:133) [weld-core-1.1.16.Final-redhat-1.jar:1.1.16.Final-redhat-1]
>   at org.jboss.weld.el.AbstractWeldELResolver.getValue(AbstractWeldELResolver.java:96) [weld-core-1.1.16.Final-redhat-1.jar:1.1.16.Final-redhat-1]
>   at org.jboss.as.weld.webtier.jsf.ForwardingELResolver.getValue(ForwardingELResolver.java:46) [jboss-as-weld-7.3.0.Final-redhat-14.jar:7.3.0.Final-redhat-14]
>   at javax.el.CompositeELResolver.getValue(CompositeELResolver.java:175) [jboss-el-api_2.2_spec-1.0.2.Final-redhat-1.jar:1.0.2.Final-redhat-1]
>   at com.sun.faces.el.DemuxCompositeELResolver._getValue(DemuxCompositeELResolver.java:176) [jsf-impl-2.1.19-redhat-2.jar:2.1.19-redhat-2]
>   at com.sun.faces.el.DemuxCompositeELResolver.getValue(DemuxCompositeELResolver.java:203) [jsf-impl-2.1.19-redhat-2.jar:2.1.19-redhat-2]
>   at org.apache.el.parser.AstIdentifier.getValue(AstIdentifier.java:72) [jbossweb-7.2.2.Final-redhat-1.jar:7.2.2.Final-redhat-1]
>   at org.apache.el.ValueExpressionImpl.getValue(ValueExpressionImpl.java:189) [jbossweb-7.2.2.Final-redhat-1.jar:7.2.2.Final-redhat-1]
>   at org.jboss.weld.el.WeldValueExpression.getValue(WeldValueExpression.java:50) [weld-core-1.1.16.Final-redhat-1.jar:1.1.16.Final-redhat-1]
>   at com.sun.faces.facelets.el.TagValueExpression.getValue(TagValueExpression.java:109) [jsf-impl-2.1.19-redhat-2.jar:2.1.19-redhat-2]
>   at javax.faces.component.ComponentStateHelper.eval(ComponentStateHelper.java:194) [jboss-jsf-api_2.1_spec-2.1.19.1.Final-redhat-1.jar:2.1.19.1.Final-redhat-1]
>   at javax.faces.component.ComponentStateHelper.eval(ComponentStateHelper.java:182) [jboss-jsf-api_2.1_spec-2.1.19.1.Final-redhat-1.jar:2.1.19.1.Final-redhat-1]
>   at javax.faces.component.UIData.getValue(UIData.java:730) [jboss-jsf-api_2.1_spec-2.1.19.1.Final-redhat-1.jar:2.1.19.1.Final-redhat-1]
>   at javax.faces.component.UIData.getDataModel(UIData.java:1809) [jboss-jsf-api_2.1_spec-2.1.19.1.Final-redhat-1.jar:2.1.19.1.Final-redhat-1]
>   at javax.faces.component.UIData.setRowIndexWithoutRowStatePreserved(UIData.java:483) [jboss-jsf-api_2.1_spec-2.1.19.1.Final-redhat-1.jar:2.1.19.1.Final-redhat-1]
>   at javax.faces.component.UIData.setRowIndex(UIData.java:472) [jboss-jsf-api_2.1_spec-2.1.19.1.Final-redhat-1.jar:2.1.19.1.Final-redhat-1]
>   at com.sun.faces.renderkit.html_basic.TableRenderer.encodeBegin(TableRenderer.java:82) [jsf-impl-2.1.19-redhat-2.jar:2.1.19-redhat-2]
>   at javax.faces.component.UIComponentBase.encodeBegin(UIComponentBase.java:826) [jboss-jsf-api_2.1_spec-2.1.19.1.Final-redhat-1.jar:2.1.19.1.Final-redhat-1]
>   at javax.faces.component.UIData.encodeBegin(UIData.java:1131) [jboss-jsf-api_2.1_spec-2.1.19.1.Final-redhat-1.jar:2.1.19.1.Final-redhat-1]
>   at javax.faces.component.UIComponent.encodeAll(UIComponent.java:1777) [jboss-jsf-api_2.1_spec-2.1.19.1.Final-redhat-1.jar:2.1.19.1.Final-redhat-1]
>   at javax.faces.component.UIComponent.encodeAll(UIComponent.java:1782) [jboss-jsf-api_2.1_spec-2.1.19.1.Final-redhat-1.jar:2.1.19.1.Final-redhat-1]
>   at javax.faces.component.UIComponent.encodeAll(UIComponent.java:1782) [jboss-jsf-api_2.1_spec-2.1.19.1.Final-redhat-1.jar:2.1.19.1.Final-redhat-1]
>   at com.sun.faces.application.view.FaceletViewHandlingStrategy.renderView(FaceletViewHandlingStrategy.java:439) [jsf-impl-2.1.19-redhat-2.jar:2.1.19-redhat-2]
>   at com.sun.faces.application.view.MultiViewHandler.renderView(MultiViewHandler.java:124) [jsf-impl-2.1.19-redhat-2.jar:2.1.19-redhat-2]
>   at javax.faces.application.ViewHandlerWrapper.renderView(ViewHandlerWrapper.java:286) [jboss-jsf-api_2.1_spec-2.1.19.1.Final-redhat-1.jar:2.1.19.1.Final-redhat-1]
>   at com.sun.faces.lifecycle.RenderResponsePhase.execute(RenderResponsePhase.java:120) [jsf-impl-2.1.19-redhat-2.jar:2.1.19-redhat-2]
>   at com.sun.faces.lifecycle.Phase.doPhase(Phase.java:101) [jsf-impl-2.1.19-redhat-2.jar:2.1.19-redhat-2]
>   at com.sun.faces.lifecycle.LifecycleImpl.render(LifecycleImpl.java:139) [jsf-impl-2.1.19-redhat-2.jar:2.1.19-redhat-2]
>   at javax.faces.webapp.FacesServlet.service(FacesServlet.java:594) [jboss-jsf-api_2.1_spec-2.1.19.1.Final-redhat-1.jar:2.1.19.1.Final-redhat-1]
>   at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:295) [jbossweb-7.2.2.Final-redhat-1.jar:7.2.2.Final-redhat-1]
>   at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:214) [jbossweb-7.2.2.Final-redhat-1.jar:7.2.2.Final-redhat-1]
>   at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:230) [jbossweb-7.2.2.Final-redhat-1.jar:7.2.2.Final-redhat-1]
>   at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:149) [jbossweb-7.2.2.Final-redhat-1.jar:7.2.2.Final-redhat-1]
>   at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:499) [jbossweb-7.2.2.Final-redhat-1.jar:7.2.2.Final-redhat-1]
>   at org.jboss.as.jpa.interceptor.WebNonTxEmCloserValve.invoke(WebNonTxEmCloserValve.java:50) [jboss-as-jpa-7.3.0.Final-redhat-14.jar:7.3.0.Final-redhat-14]
>   at org.jboss.as.jpa.interceptor.WebNonTxEmCloserValve.invoke(WebNonTxEmCloserValve.java:50) [jboss-as-jpa-7.3.0.Final-redhat-14.jar:7.3.0.Final-redhat-14]
>   at org.jboss.as.web.security.SecurityContextAssociationValve.invoke(SecurityContextAssociationValve.java:169) [jboss-as-web-7.3.0.Final-redhat-14.jar:7.3.0.Final-redhat-14]
>   at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:145) [jbossweb-7.2.2.Final-redhat-1.jar:7.2.2.Final-redhat-1]
>   at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:97) [jbossweb-7.2.2.Final-redhat-1.jar:7.2.2.Final-redhat-1]
>   at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:102) [jbossweb-7.2.2.Final-redhat-1.jar:7.2.2.Final-redhat-1]
>   at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:336) [jbossweb-7.2.2.Final-redhat-1.jar:7.2.2.Final-redhat-1]
>   at org.apache.coyote.http11.Http11Processor.process(Http11Processor.java:856) [jbossweb-7.2.2.Final-redhat-1.jar:7.2.2.Final-redhat-1]
>   at org.apache.coyote.http11.Http11Protocol$Http11ConnectionHandler.process(Http11Protocol.java:653) [jbossweb-7.2.2.Final-redhat-1.jar:7.2.2.Final-redhat-1]
>   at org.apache.tomcat.util.net.JIoEndpoint$Worker.run(JIoEndpoint.java:920) [jbossweb-7.2.2.Final-redhat-1.jar:7.2.2.Final-redhat-1]
>   at java.lang.Thread.run(Thread.java:745) [rt.jar:1.7.0_71]

## TransientPropertyValueException: object references an unsaved transient instance
> ERROR [org.apache.catalina.core.ContainerBase.[jboss.web].[default-host].[/moccasin-webapp-1.0].[Faces Servlet]] (http-localhost.localdomain/127.0.0.1:8080-1) JBWEB000236: Servlet.service() for servlet Faces Servlet threw exception: org.hibernate.TransientPropertyValueException: object references an unsaved transient instance - save the transient instance before flushing: net.skybert.moccasin.data.GatheringIndian.tribe -> net.skybert.moccasin.data.GatheringTribe
>   at org.hibernate.engine.spi.CascadingAction$8.noCascade(CascadingAction.java:380) [hibernate-core-4.2.7.SP1-redhat-3.jar:4.2.7.SP1-redhat-3]
>   at org.hibernate.engine.internal.Cascade.cascade(Cascade.java:176) [hibernate-core-4.2.7.SP1-redhat-3.jar:4.2.7.SP1-redhat-3]

If you google this problem, you find answers that tell you that you
have to persist the tribe field before persiisting the indian:
```
entitityManager.persist(indian.getTribe());
entitityManager.persist(indian);
```
or adding ```cascade=Cascade.PERSIST``` to the tribe field inside
```GatheringIndian```. However, my problem was different: this was
supposed to be a reference to an already existing tribe entry. It
turned out to be a copy constructor which didn't copy the id, thus
requesting Hibernate to create a new tribe entry.
