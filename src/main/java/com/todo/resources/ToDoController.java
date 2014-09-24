package com.todo.resources;

import java.util.ArrayList;

import com.todo.templates.ToDo;

public class ToDoController{
	
	static private ArrayList<ToDo> tasksdone = new ArrayList<ToDo>();
	static private ArrayList<ToDo> tasksnotdone = new ArrayList<ToDo>();
	
	public ArrayList<ToDo> getDone(){
		return tasksdone;
	}
	
	public ArrayList<ToDo> getNotDone(){
		return tasksnotdone;
	}
	
	public boolean saveToDo(final ToDo todo){
		if(tasksnotdone.add(todo)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean markDone(final ToDo todo){
		if(tasksnotdone.remove(todo)){
			todo.setDone(true);
			if(tasksdone.add(todo)){
				return true;
			}
		}
		return false;
	}
	
	public boolean markUndone(final ToDo todo){
		if(tasksdone.remove(todo)){
			todo.setDone(false);
			if(tasksnotdone.add(todo)){
				return true;
			}
		}
		return false;
	}
	
	public boolean update(final ToDo todo){
		if(todo.getDone()){
			int location = tasksdone.indexOf(todo);
			if(location > -1){
				tasksdone.get(location).setTitle(todo.getTitle());
				tasksdone.get(location).setBody(todo.getBody());
				return true;
			}
			else{
				return false;
			}
			
		}
		else{
			int location = tasksnotdone.indexOf(todo);
			if(location > -1){
				tasksnotdone.get(location).setTitle(todo.getTitle());
				tasksnotdone.get(location).setBody(todo.getBody());
				return true;
			}
			else{
				return false;
			}
		}
	}
	
	public boolean delete(ToDo todo){
		if(todo.getDone()){
			if(tasksdone.remove(todo)){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			if(tasksnotdone.remove(todo)){
				return true;
			}
			else{
				return false;
			}
		}
	}
	
	public boolean deleteAllDone(){
		try{
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
			tasksnotdone.clear();
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
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
}
