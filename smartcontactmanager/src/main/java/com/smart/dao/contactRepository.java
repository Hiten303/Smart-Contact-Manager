package com.smart.dao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entities.contacts;

public interface contactRepository extends JpaRepository<contacts,Integer> {
    
	@Query("from contacts as c where c.User.id=:userId")
	public Page<contacts> findContactsByUser(@Param("userId")int userId ,Pageable pagable);
}
