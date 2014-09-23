package com.todo.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.todo.templates.ToDo;

@Path("/todo")
public class ToDoService {
	
	private ToDoController data = new ToDoController();
	
	//Testing Service.
	@GET
	@Path("/test")
	public Response test(){
		return Response.ok().entity("Service Testing Successful").build();
	}
	
	//Saving Todo To Static List.
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveToDo(final ToDo todo){
		data.saveToDo(todo);
		return Response.status(201).entity("Created").build();
	}
}
