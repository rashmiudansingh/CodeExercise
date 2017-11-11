package com.ehireo.user.registration.controller.impl;

import java.util.StringTokenizer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONException;
import org.json.JSONObject;

import com.ehireo.user.registration.bean.LoginLink;
import com.ehireo.user.registration.bean.User;
import com.data.store.LoginLinkStore;
import com.data.store.UserStore;

@Path("/user")
@XmlRootElement(name = "user")
public class UserRegistrationEndPointImpl {
	
	private static String SERVER_NAME = "USRVC.COM";
	private static String SERVER_SOCKET_STRING = "https://";
	private static String  SERVICE = "/user";
	private static String LOGIN_LINK_URL= "/login";
	
	@Path("/register/{user}")
	@POST
	public static String register(
			String user) throws Exception{
		
		StringTokenizer tokens = new StringTokenizer(user,",");
		String userName = tokens.nextToken(",");
		String email = tokens.nextToken(",");
		String pincode = tokens.nextToken(",");
		
		
		User userInfo = new User(userName, email, pincode);
		
		String userId = UserStore.insertUser(userInfo);
		if (userId == null){
			return "An error occured while saving the error , please try again later";
		}
		
		JSONObject response  = new JSONObject();
		response.put("userid",userId);
		return response.toString();
		
	}
	
	
	@Path("/getuser")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public static String getUser(@QueryParam("userId") String userId){
		User user;
		
		user = UserStore.findUser(userId);
		
		if(user== null) {return "USER is not found, please check the user id provided";}
		LoginLink  loginLink =  new LoginLink(user.getUserId(), SERVER_SOCKET_STRING+SERVER_NAME+SERVICE+LOGIN_LINK_URL+"?userId="+userId);
		loginLink.setUserEmail(user.getEmail());
		LoginLinkStore.addLink(loginLink);
		
		EmailService emailService = new EmailService();
		emailService.setLoginLink(loginLink);
		emailService.sendEmail();
		
		return "User Id found, please check your registered email for details";
	}
		
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public static String getLoginDetails(@QueryParam("userId") String userId) throws JSONException{
		
		//check if the link is valid 
		if(LoginLinkService.isLinkValid(LoginLinkStore.findLoginLink(userId))){
		User user = UserStore.findUser(userId);
		if(user!= null){
			JSONObject response  = new JSONObject();
			response.put("user",user);
			return response.toString();
		}
		else return "Error returned";
		}
		else return "Error returned, looks like the link was expired. The link is valid for 15 minutes.";
		
	}
	

}
