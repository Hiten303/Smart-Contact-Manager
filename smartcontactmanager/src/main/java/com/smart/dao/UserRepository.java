package com.smart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entities.user;

public interface UserRepository extends JpaRepository<user,Integer>  {

	@Query("select u from user u where u.email=:email")
	public user getUserByUserName(@Param("email") String email) ;
	

}
