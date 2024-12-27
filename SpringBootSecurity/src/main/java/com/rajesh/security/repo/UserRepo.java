package com.rajesh.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rajesh.security.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{

	User findByUserName(String userName);
}
