package com.miniproject.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.miniproject.entities.*;


public interface AdminRepository extends JpaRepository<Admin,Long>{
	
@Query("select a from Admin a where a.email =:email")
	public Admin getAdminByName(@Param("email") String adminName);
	
	
	
}
