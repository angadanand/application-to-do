package com.todo.templates;

/*
 *  This Provides a custom Data type to be used.
 */
public class ToDo {
	private String title;
	private String body;
	private boolean done;
	
	public ToDo(){}
	
	public ToDo(String title){
		this(title,"No Body");
	}
	
	public ToDo(String title, String body){
		this.title = title;
		this.body = body;
		this.done = false;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	public void setBody(String body){
		this.body = body;
	}
	public void setDone(boolean done){
		this.done = done;
	}
	
	public String getTitle(){
		return title;
	}
	public String getBody(){
		return body;
	}
	public boolean isDone(){
		return done;
	}
}
