package com.tcs.taskProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.taskProject.payLoad.TaskDto;
import com.tcs.taskProject.service.TaskService;

@RestController
@RequestMapping("/api")
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	
	//save the task
	
	
	@PostMapping("/{userId}/tasks")
	public ResponseEntity<TaskDto> saveTask(@PathVariable long userId, @RequestBody TaskDto taskDto) {
	    
	    return new ResponseEntity<>(taskService.saveTask(userId,taskDto), HttpStatus.CREATED);
	}

	
	
	//get the tasks
	
	@GetMapping("/{userId}/tasks")
	public ResponseEntity<List<TaskDto>> getAllTasks(@PathVariable long userId){
		
		return new ResponseEntity<>(taskService.getTaskList(userId),HttpStatus.OK);
		
	}
	
	//get the individual task
	
	@GetMapping("/{userId}/tasks/{taskId}")
	public ResponseEntity<TaskDto> getTask(@PathVariable long userId,@PathVariable long taskId ){
		
		
		return new ResponseEntity<>(taskService.getTask(userId, taskId),HttpStatus.OK);
		
	}
	
	
	
	//delete individual task
	
	
	@DeleteMapping("/{taskId}/tasks/{userId}")
	public ResponseEntity<String> deleteTask(@PathVariable long taskId,@PathVariable long userId ){
		
		taskService.deleteTask(userId, taskId);
		
		return new ResponseEntity<>("task deleted sucessfully",HttpStatus.OK);
		
	}

}
