package com.miniproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.miniproject.dao.AdminRepository;
import com.miniproject.entities.Admin;

public class AdminDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private AdminRepository adminRepository;
	
	@Override
	public UserDetails loadUserByUsername(String adminName) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		Admin admin = adminRepository.getAdminByName(adminName);
		
		if(admin==null) {
			throw new UsernameNotFoundException("Could not found admin!!");
		}
	
		CustomerAdminDetails customerAdminDetails = new CustomerAdminDetails(admin);
		
		return customerAdminDetails;
	}

}
