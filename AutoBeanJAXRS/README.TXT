1.Add jar file into your class path,
2.Your AutoBeanFactory interface should extends RestAutoBeanFactory interface
3.In your EntryPoint, use REST.registerFactory(<YOUR_AUTO_BEAN_FACTORY_CLASS>.class)
4.In web.xml, 

add
	<context-param>
		<param-name>restautobean.factory</param-name>
		<param-value>YOUR_AUTO_BEAN_FACTORY_CLASS_QUALIFIED_CLASS_NAME</param-value>
	</context-param>
	
Then add

	<listener>
		<listener-class>com.seanchenxi.resteasy.autobean.server.AutoBeanFacotryIntializer</listener-class>
	</listener>

5. Your REST Resource interface should extends HasRESTClient
6. Your GWT REST Client Async interface should be created just like GWT RPC Async
7. Then use 
   RestResourceAsync service = GWT.create(RestResource.class)
   