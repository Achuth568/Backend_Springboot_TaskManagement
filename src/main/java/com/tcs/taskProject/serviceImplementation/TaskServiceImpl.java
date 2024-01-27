package com.tcs.taskProject.serviceImplementation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.taskProject.entity.Task;
import com.tcs.taskProject.entity.Users;
import com.tcs.taskProject.exceptionHandling.ApiException;
import com.tcs.taskProject.exceptionHandling.TaskNotFound;
import com.tcs.taskProject.exceptionHandling.UserNotFound;
import com.tcs.taskProject.payLoad.TaskDto;
import com.tcs.taskProject.repository.TaskRepository;
import com.tcs.taskProject.repository.UserRepository;
import com.tcs.taskProject.service.TaskService;


@Service
public  class TaskServiceImpl implements TaskService{
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Autowired
	private TaskRepository taskRepository;
	
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public TaskDto saveTask(long userId, TaskDto taskDto) {
		
		Users user= userRepository.findById(userId)
				.orElseThrow(()->new UserNotFound(String.format("User Id %d not found", userId)));
		
		Task task= modelMapper.map(taskDto,Task.class);
		
	    task.setUsers(user);
		Task savedTask=taskRepository.save(task);
		
		
		return modelMapper.map(savedTask,TaskDto.class);
	}

	@Override
	public List<TaskDto> getTaskList(long userId) {
		userRepository.findById(userId)
		.orElseThrow(()->new UserNotFound(String.format("User Id %d not found", userId)));
		
		
		List<Task> tasks=taskRepository.findAllByUserId(userId);
		
		return tasks.stream().map(task->modelMapper.map(task,TaskDto.class)).collect(Collectors.toList());
	}

	@Override
	public TaskDto getTask(long userId, long taskId) {
		
		Users user= userRepository.findById(userId)
				.orElseThrow(()->new UserNotFound(String.format("User Id %d not found", userId)));
		
		Task task=taskRepository.findById(taskId)
				.orElseThrow(()->new TaskNotFound(String.format("User Id %d not found", taskId)));
		
		
		if(user.getUserId()!=task.getUsers().getUserId())
		{
			throw new ApiException(String.format("Task id %d is not belongs to userid %d", taskId,userId));
		}
		
		return modelMapper.map(task,TaskDto.class);
	}

	@Override
	public void deleteTask(long userId, long taskId) {
		
		Users user= userRepository.findById(userId)
				.orElseThrow(()->new UserNotFound(String.format("User Id %d not found", userId)));
		
		Task task=taskRepository.findById(taskId)
				.orElseThrow(()->new TaskNotFound(String.format("User Id %d not found", taskId)));
		
		
		if(user.getUserId()!=task.getUsers().getUserId())
		{
			throw new ApiException(String.format("Task id %d is not belongs to userid %d", taskId,userId));
		}
		
		
		taskRepository.deleteById(taskId);
	}
	
	
	
	

}
