package com.miniproject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.miniproject.entities.*;


public interface UserRepository extends JpaRepository<User,Long>{
	
	@Query("select u from User u where u.lastName=:lastName or u.firstName=:firstName")
	public User findBylastnameOrFirstname(@Param("lastName") String lastName,@Param("firstName") String firstName);
		
	@Query("select u from User u where u.firstName like ?1")
	public User findByFirstnameLike(@Param("firstName")String firstName);
}
