package com.seanchenxi.resteasy.autobean.test.client;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.seanchenxi.resteasy.autobean.share.RESTService;

@Path("/rest/greetM")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface GreetingServiceM extends RESTService {
  
  @GET @Path("/serverInfo/{name}-{ok}")
  String greetServer(@PathParam("name") String name, @PathParam("ok") boolean ok) throws IllegalArgumentException;
  
  @GET @Path("/serverInfoObject/{name}-{ok}")
  Response greetServerObject(@PathParam("name") String name, @PathParam("ok") boolean ok) throws IllegalArgumentException;
}
