package com.tcs.taskProject.service;

import java.util.List;

import com.tcs.taskProject.payLoad.TaskDto;

public interface TaskService {
	
	
	public TaskDto saveTask(long userId, TaskDto taskDto);
	
	
	public List<TaskDto> getTaskList(long userId);
	
	public TaskDto getTask(long userId,long taskId);
	
	public void deleteTask(long userId,long taskId);

}
