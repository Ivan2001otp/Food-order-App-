package com.miniproject.dao;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.miniproject.entities.*;


public interface ProductRepository extends JpaRepository<Product,Long>{
	
	//@Query("from Contact as c where c.user.id =:userId")
	
	@Query("from Product as p where p.admin.adminId =:AdminId")
	List<Product>getAllAdminProducts(@Param("AdminId") Long AdminId);
	
	@Query(value="SELECT * FROM food_app.product ORDER by product_name",nativeQuery=true)
	public Page<Product>showAllProducts(Pageable pageable);
}
