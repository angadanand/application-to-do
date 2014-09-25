package com.todo.templates;
import io.searchbox.annotations.JestId;

/*
 *  This Provides a custom Data type to be used.
 *  I would have liked a more abstract Entity class as a superclass which held the ID
 *  but for this particular implementation I chose to avoid it because the Entity
 *  class would just have ID as a property. 
 */
public class ToDo {
	
	@JestId
	private String id;
	
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
	
	public void setId(String id){
		this.id = id;
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
	
	public String getId(){
		return id;
	}
	
	public String getTitle(){
		return title;
	}
	public String getBody(){
		return body;
	}
	public boolean getDone(){
		return done;
	}
	
	/*
	 * Overriding default equals method so that it can be used for my purpose.
	 * It is also overridden so that it can be used by the ToDoController in the 
	 * so that collection sepcific methods like .remove()
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}
		else if(obj instanceof ToDo){
			if(this.id.equals(((ToDo) obj).getId())){
				return true;
			}
		}
		else{
			return false;
		}
		return false;
	}
}
