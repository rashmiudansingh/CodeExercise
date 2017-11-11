package com.ehireo.user.registration.service;

import java.util.Calendar;

import com.ehireo.user.registration.bean.LoginLink;

public class LoginLinkService {
	
	public static final int MAX_MINUTES= 15;
	
	
	public static boolean isLinkValid(LoginLink loginLink){
		if(loginLink.getExpiry().getTimeInMillis()<Calendar.getInstance().getTimeInMillis()) return false;
		else return true;
	}

}
