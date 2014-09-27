package com.todo.resources;

import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.todo.templates.ToDo;

@Path("/todo")
public class ToDoService {
	
	static ToDoController data = new ToDoController();
	
	//Testing Service.
	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public Response test(){
		return Response.ok().entity("Service Testing Successful").build();
	}
	
	
	/*
	 * 
	@GET
	@Path("/test/donelist")
	public Response doneListInConsole(){
		return Response.ok().entity(data.printDoneList()).build();
	}
	
	@GET
	@Path("/test/notdonelist")
	public Response notDoneListInConsole(){
		return Response.ok().entity(data.printNotDoneList()).build();
	}
	
	 * 
	 * --------------------------------------------------------------------------
	 * 						Live Code Starts Here
	 * --------------------------------------------------------------------------
	 * 
	 */
	
	/*
	 *  ================ All @GETS ============================
	 */
	@GET
	@Path("/restore")
	public Response restore(){
		try{
			data.restore();
			return Response.status(200).entity("Restore Successful.").build();
		}
		catch(Exception e){
			e.printStackTrace();
			return Response.status(400).entity("Error Occurred Please try again.").build();
		}
	}
	
	@GET
	@Path("/donelist")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDoneList(){
		return Response.status(200).entity(data.getDone()).build();
	}
	
	@GET
	@Path("/notdonelist")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNotDoneList(){
		return Response.status(200).entity(data.getNotDone()).build();
	}
	
	@GET
	@Path("/storrednumber")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getClientNumber(){
		if(data.getClientnumber().equals("")){
			return Response.status(200).entity("Number not set").build();
		}
		else{
			//This is just to store the return format for security reasons.
			String temp = data.getClientnumber().substring(0, 2)+"******"+data.getClientnumber().substring(8, 12);
			return Response.status(200).entity("Number on tab : "+temp).build();
		}
	}
	
	/*
	 *  ================ All @POSTS ============================
	 * Check Null can be checked in JavaScript by using .isNaN()
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
	@Path("/changenumber")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response setClientNumber(String number){
		if(number.length() == 12 && number.subSequence(0, 2).equals("+1")){
			ToDoController.setClientnumber(number);
			String temp = data.getClientnumber().substring(0, 2)+"******"+data.getClientnumber().substring(8, 12);
			return Response.status(200).entity(temp + " Set as default messaging number").build();
		}
		else{
			return Response.status(400).entity("Not expected number format.").build();
		}
	}
	
	@POST
	@Path("/search")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(String searchparam){
		try{
			return Response.status(200).entity(data.search(searchparam)).build();
		}
		catch(Exception e){
			e.printStackTrace();
			return Response.status(400).entity("Search Error Please try Again.").build();
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
	
	/*
	 *  ================ All @DELETES ============================
	 */
	
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
