package com.todo.templates;

import com.todo.configuration.SearchConfiguration;
import com.todo.configuration.TwilioConfiguration;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;



import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.IndicesExists;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
/*
 * Base controller which controls all off the remote resources
 * this is good because the underlying code is not directly exposed.
 */
public abstract class BaseController {
	
	final protected static String ES_INDEX = "todos";
	final protected static String ES_TYPE = "todo";
	final protected static String SMS_NUMBER = "+<Number>";
	
	private static JestClient esclient = new SearchConfiguration().jestClient();
	private static TwilioRestClient smsclient = new TwilioConfiguration().getTwilioClient();
	
	protected void checkIndices()
	{
		try{
			IndicesExists indicesexist = new IndicesExists.Builder(ES_INDEX).build();
			JestResult result = esclient.execute(indicesexist);
			if(!result.isSucceeded()){
				CreateIndex createindex = new CreateIndex.Builder(ES_INDEX).build();
				esclient.execute(createindex);
			}
		}
		catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	protected boolean updateESDocument(ToDo todo){
		try{
			checkIndices();
			Index index = new Index.Builder(todo).index(ES_INDEX).type(ES_TYPE).build();
			esclient.execute(index);
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	protected boolean deleteESDocument(ToDo todo){
		try{
			JestResult result = esclient.execute(new Delete.Builder(todo.getId())
									.index(ES_INDEX)
									.type(ES_TYPE)
									.build());
			
			return result.isSucceeded();
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	protected List<ToDo> searchESDocument(String tosearch){
		try{
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.queryString(tosearch));
			
            Search search = new Search.Builder(searchSourceBuilder.toString())
				            .addIndex(ES_INDEX)
				            .addType(ES_TYPE)
				            .build();
            JestResult result = esclient.execute(search);
            return result.getSourceAsObjectList(ToDo.class);
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	protected List<ToDo> restoreDataFromESDocument(){
		List<ToDo> todosInESDocument = searchESDocument("*");
		return todosInESDocument;
	}
	
	protected boolean sendSMS(String message, String clientnumber){
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("To", clientnumber));
		params.add(new BasicNameValuePair("From", SMS_NUMBER));
		params.add(new BasicNameValuePair("Body", message));
		
		try{
			MessageFactory messageFactory = smsclient.getAccount().getMessageFactory();
			Message sms = messageFactory.create(params);
			sms.getSid();
			return true;
		}
		catch(TwilioRestException tre){
			tre.printStackTrace();
			return false;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
