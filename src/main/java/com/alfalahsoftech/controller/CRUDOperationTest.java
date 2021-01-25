package com.alfalahsoftech.controller;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.alfalahsoftech.base.AFBaseObject;

@Path("/crud")
public class CRUDOperationTest extends AFBaseObject {


	
	@POST
	@Path("/fileUpload")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response fileUpload(String req) {
		afDebug("Request received: " + req);
		return Response.ok(req).build();
	}
	
	@POST
	@Path("/testAPI")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response testAPI(String req) {
		afDebug("Request received: " + req);
		return Response.ok(req).build();
	}
}
