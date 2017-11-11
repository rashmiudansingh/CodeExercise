package com.ehireo.user.registration.controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.user.service.GET;
import com.user.service.JSONException;
import com.user.service.Path;
import com.user.service.Produces;
import com.user.service.QueryParam;


/**
 * The interface shared to register user
 *
 * @author Rashmi
 */
public interface UserRegistrationEndPoint {

	
    /**
     * to register a new user.
     * 
     */
	@Path("/register/{user}")
	@POST
	public static String register(@PathParam("user") String user);
	
	
    /**
     * to get user
     * 
     */
	@Path("/getuser")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public static String getUser(@QueryParam("userId") String userId);
	
    /**
     * to get login details
     * 
     */	
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public static String getLoginDetails(@QueryParam("userId") String userId);
	
	
}
