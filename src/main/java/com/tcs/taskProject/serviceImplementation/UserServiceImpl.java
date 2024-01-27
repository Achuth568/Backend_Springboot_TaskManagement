package com.tcs.taskProject.serviceImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tcs.taskProject.entity.Users;
import com.tcs.taskProject.payLoad.UserDto;
import com.tcs.taskProject.repository.UserRepository;
import com.tcs.taskProject.service.UserService;


@Service
public class UserServiceImpl implements UserService {
	
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDto createUser(UserDto userDto) {
		
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		
		Users user=userDtoToEntity(userDto);
		
		Users savedUser=userRepository.save(user);
		
		
		return entityToUserDto(savedUser);
	}
	
	private Users userDtoToEntity(UserDto userDto) {
		
		Users users=new Users();
		users.setName(userDto.getName());
		users.setEmail(userDto.getEmail());
		users.setPassword(userDto.getPassword());
		
		return users;
		
	}
	
	private UserDto entityToUserDto(Users savedUser) {
		
		UserDto userDto= new UserDto();
		userDto.setUserId(savedUser.getUserId());
		userDto.setName(savedUser.getName());
		userDto.setEmail(savedUser.getEmail());
		userDto.setPassword(savedUser.getPassword());
		
		return userDto;
	}

}
