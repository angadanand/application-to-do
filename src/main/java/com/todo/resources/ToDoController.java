package com.todo.resources;

import java.util.ArrayList;
import java.util.List;

import com.todo.templates.BaseController;
import com.todo.templates.ToDo;

public class ToDoController extends BaseController{
	/*
	 * After implementing ES I believe these lists are not necessary
	 * but I left it here so that I can quickly get both lists without
	 * accessing the ES service. May not be the best idea but I don't see 
	 * each of the arraylists having more than a few hundred elements at a time
	 * for this use case where I have only one user.
	 */
	static private ArrayList<ToDo> tasksdone = new ArrayList<ToDo>();
	static private ArrayList<ToDo> tasksnotdone = new ArrayList<ToDo>();
	static private String clientnumber;
	
	
	public ToDoController(){
		clientnumber = "";
	}
	
	public String getClientnumber(){
		return clientnumber;
	}
	
	public static void setClientnumber(String clientnum){
		clientnumber = new String(clientnum);
	}
	
	public ArrayList<ToDo> getDone(){
		return tasksdone;
	}
	
	public ArrayList<ToDo> getNotDone(){
		return tasksnotdone;
	}
	
	/*
	 * I'm quite positive that most of my "else" statements are not required but I like using it,
	 * It makes it easier for me to read/debug the code later. And I have OCD,
	 * I hate writing an if without an else
	 */
	public boolean saveToDo(final ToDo todo){
		if(tasksnotdone.add(todo)){
			if(updateESDocument(todo)){
				if(!(clientnumber.equals(""))){
					sendSMS("New Task : "+todo.getTitle()+"\n"+todo.getBody(), clientnumber);
				}
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}
	
	public boolean markDone(final ToDo todo){
		if(tasksnotdone.remove(todo)){
			todo.setDone(true);
			if(tasksdone.add(todo)){
				if(updateESDocument(todo)){
					if(!(clientnumber.equals(""))){
						sendSMS("Task : "+todo.getTitle()+" Completed.", clientnumber);
					}
					return true;
				}
				else{
					return false;
				}
			}
		}
		else{
			return false;
		}
		return false;
	}
	
	public boolean markUndone(final ToDo todo){
		if(tasksdone.remove(todo)){
			todo.setDone(false);
			if(tasksnotdone.add(todo)){
				if(updateESDocument(todo)){
					return true;
				}
				else{
					return false;
				}
			}
		}
		else{
			return false;
		}
		return false;
	}
	
	/*
	 * This is a result of hackery. Can be made to look much better by putting the 
	 * double written code in a function but It works.
	 */
	public boolean update(final ToDo todo){
		if(todo.getDone()){
			int location = tasksdone.indexOf(todo);
			if(location > -1){
				//make sure that its updated on the ES service before local.
				if(updateESDocument(todo)){
					tasksdone.get(location).setTitle(todo.getTitle());
					tasksdone.get(location).setBody(todo.getBody());
					return true;
				}
				else{
					return false;
				}
			}
			else{
				return false;
			}
			
		}
		else{
			int location = tasksnotdone.indexOf(todo);
			if(location > -1){
				if(updateESDocument(todo)){
					tasksnotdone.get(location).setTitle(todo.getTitle());
					tasksnotdone.get(location).setBody(todo.getBody());
					return true;
				}
				else{
					return false;
				}
			}
			else{
				return false;
			}
		}
	}
	
	public boolean delete(ToDo todo){
		if(todo.getDone()){
			if(tasksdone.remove(todo)){
				if(deleteESDocument(todo)){
					return true;
				}
				else{
					return false;
				}
			}
			else{
				return false;
			}
		}
		else{
			if(tasksnotdone.remove(todo)){
				if(deleteESDocument(todo)){
					return true;
				}
				else{
					return false;
				}
			}
			else{
				return false;
			}
		}
	}
	
	public boolean deleteAllDone(){
		try{
			for(ToDo todo : tasksdone){
				deleteESDocument(todo);
			}
			tasksdone.clear();
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteAllNotDone(){
		try{
			for(ToDo todo : tasksnotdone){
				deleteESDocument(todo);
			}
			tasksnotdone.clear();
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public List<ToDo> search(String tosearch){
		try{
			return searchESDocument(tosearch);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void restore(){
		List<ToDo>	todos = restoreDataFromESDocument();
		for(ToDo todo : todos){
			if( todo.getDone() && !(tasksdone.contains(todo))){
				tasksdone.add(todo);
			}
			else if( !(todo.getDone()) && !(tasksnotdone.contains(todo))){
				tasksnotdone.add(todo);
			}
		}
	}
	
	/*
	 * For quick testing.
	
	public String printDoneList(){
		String result="";
		for(ToDo t : tasksdone){
			result+=t.getId()+" :: "+t.getTitle()+" : "+t.getBody()+"\n";
		}
		return result;
	}
	
	public String printNotDoneList(){
		String result="";
		for(ToDo t : tasksnotdone){
			result+=t.getId()+" :: "+t.getTitle()+" : "+t.getBody()+"\n";
		}
		return result;
	}
	 */
}