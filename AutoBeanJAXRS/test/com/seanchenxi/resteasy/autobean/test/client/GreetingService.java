package com.seanchenxi.resteasy.autobean.test.client;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.seanchenxi.resteasy.autobean.share.RESTService;
import com.seanchenxi.resteasy.autobean.test.share.Greeting;

@Path("/rest/greet")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface GreetingService extends RESTService {
  
  @GET @Path("/serverInfo/{name}-{ok}")
  String greetServer(@PathParam("name") String name, @PathParam("ok") boolean ok) throws IllegalArgumentException;
  
  @GET @Path("/serverInfoObject/{name}-{ok}")
  Greeting greetServerObject(@PathParam("name") String name, @PathParam("ok") boolean ok) throws IllegalArgumentException;
}
