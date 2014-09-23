package com.todo.resources;

import java.util.ArrayList;
import java.util.Collection;

import com.todo.templates.ToDo;

public class ToDoController{
	
	static private ArrayList<ToDo> tasksdone = new ArrayList<ToDo>();
	static private ArrayList<ToDo> tasksnotdone = new ArrayList<ToDo>();
	
	public void saveToDo(final ToDo todo){
		tasksnotdone.add(todo);
	}
	
	public void printListInConsole(){
		for(ToDo t : tasksnotdone){
			System.out.println(t.getTitle()+" : "+t.getBody());
		}
	}
}
