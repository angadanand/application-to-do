package com.todo.configuration;

import com.twilio.sdk.TwilioRestClient;

public class TwilioConfiguration {
	private final String ACCOUNT_SID = " Acound SID ";
	private final String AUTH_TOKEN = "Twilio auth token";
	
	public TwilioConfiguration(){}
	
	public TwilioRestClient getTwilioClient(){
		
		return new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
	}
}
