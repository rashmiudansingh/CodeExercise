package com.ehireo.user.registration.service;

import com.ehireo.user.registration.bean.LoginLink;

public class EmailService {
	
	private static String CONTENT = "Click on the link to fetch the details";
	
	private LoginLink loginLink;

	public LoginLink getLoginLink() {
		return loginLink;
	}

	public void setLoginLink(LoginLink loginLink) {
		this.loginLink = loginLink;
	}
	
	public  void sendEmail(){
		// Need to setup SMTP
		
		System.out.println("Email sent successfully to "+loginLink.getUserEmail());
	}
	

}
