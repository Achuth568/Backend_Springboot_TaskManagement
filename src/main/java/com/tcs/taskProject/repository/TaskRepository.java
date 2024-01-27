package com.tcs.taskProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tcs.taskProject.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
	
   @Query("SELECT t FROM Task t WHERE t.users.id = :userId")
   List<Task> findAllByUserId(@Param("userId") long userId);

	//List<Task> findAllByUserId(long userId);

}
