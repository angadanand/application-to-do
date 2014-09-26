package com.todo.configuration;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

/*
 * The search client configuration
 */

public class SearchConfiguration {
	public SearchConfiguration(){}
	public JestClient jestClient(){
		String connectionUrl;
		
		if(System.getenv("SEARCHBOX_URL") != null){
			connectionUrl = System.getenv("SEARCHBOX_URL");
		}
		/*
		 * If the above fails manually entering. 
		 * But this might fail as URLs/URIs might change but good backup
		 * well at least in my mind.Mainly for private testing
		 */
		else{
			connectionUrl="http://paas:c06b087385d22349c522292c338bacd4@bofur-us-east-1.searchly.com";
		}
		
		/*
		 * Hacked through some configuration.
		 */
		JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
        		.Builder(connectionUrl)
        		.multiThreaded(true)
        		.build());
        return factory.getObject();
	}
}
