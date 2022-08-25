package com.miniproject.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name="admin")
public class Admin {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long adminId;
	
	@NotBlank(message="must fill! or")
	@Size(min=3,max=20,message="min-3 & max-20 characters")
	private String name;
	
	@NotBlank(message="must fill or")
	@Pattern(regexp="^[a-zA-Z0-9_.-]+@[a-zA-Z0-9.-]+$",message="Invalid Email!")
	private String email;
	
	@NotBlank(message="must fill!")
	private String password;
	
	private boolean enabled;
	
	private String role;
	
	
	
	public Admin() {}
	
	public Admin(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	
	
	@Override
	public String toString() {
		return "Admin [adminId=" + adminId + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", enabled=" + enabled + ", role=" + role + ", productList=" + productList + "]";
	}



	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="admin")
	public List<Product>productList=new ArrayList<>();
	
	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public long getAdminId() {
		return adminId;
	}

	public void setAdminId(long adminId) {
		this.adminId = adminId;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
