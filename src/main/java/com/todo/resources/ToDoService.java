package com.todo.resources;

import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
	
	//Testing Methods
	@GET
	@Path("/test/donelist")
	public Response doneListInConsole(){
		return Response.ok().entity(data.printDoneList()).build();
	}
	
	//Testing Methods
	@GET
	@Path("/test/notdonelist")
	public Response notDoneListInConsole(){
		return Response.ok().entity(data.printNotDoneList()).build();
	}
	
	/*
	 * 
	 * --------------------------------------------------------------------------
	 * 						Live Code Starts Here
	 * --------------------------------------------------------------------------
	 * 
	 */
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveToDo(final ToDo todo){
		todo.setId(""+UUID.randomUUID());
		if(data.saveToDo(todo)){
			return Response.status(201).entity("Todo added.").build();
		}
		else{
			return Response.status(400).entity("Error please try again.").build();
		}
	}
	
	@POST
	@Path("/markdone")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response markDone(final ToDo todo){
		if(todo.getDone()){
			if(data.markUndone(todo)){
				return Response.status(200).entity("Task Completed.").build();
			}
		}
		else{
			if(data.markDone(todo)){
				return Response.status(200).entity("Task Completed.").build();
			}
		}
		return Response.status(400).entity("Error please try again.").build();
	}
	
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(final ToDo todo){
		if(data.update(todo)){
			return Response.status(200).entity("Task Data Updated").build();
		}
		else{
			return Response.status(400).entity("Error please try again.").build();
		}
	}
	
	@DELETE
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response delete(final ToDo todo){
		if(data.delete(todo)){
			return Response.status(200).entity("Successfully Deleted.").build();
		}
		else{
			return Response.status(400).entity("Error please try again.").build();
		}
	}
	
	@DELETE
	@Path("/delete/donelist")
	public Response deleteAllDone(){
		if(data.deleteAllDone()){
			return Response.status(200).entity("Successfully Deleted All From Done List.").build();
		}
		else{
			return Response.status(400).entity("Error please try again.").build();
		}
	}
	
	@DELETE
	@Path("/delete/notdonelist")
	public Response deleteAllNotDone(){
		if(data.deleteAllNotDone()){
			return Response.status(200).entity("Successfully Deleted All From Not Done List.").build();
		}
		else{
			return Response.status(400).entity("Error please try again.").build();
		}
	}
}
