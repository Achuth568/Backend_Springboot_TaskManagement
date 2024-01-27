package com.tcs.taskProject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcs.taskProject.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {

	Optional<Users> findByEmail(String email);

	

}
