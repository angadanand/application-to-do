package com.todo.configuration;

import com.twilio.sdk.TwilioRestClient;

public class TwilioConfiguration {
	private final String ACCOUNT_SID = "ACb4c333fecc7f9bff91464cc2c4ff4724";
	private final String AUTH_TOKEN = "e33dd91fd7f00cc218217fae1377ac9d";
	
	public TwilioConfiguration(){}
	
	public TwilioRestClient getTwilioClient(){
		
		return new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
	}
}
