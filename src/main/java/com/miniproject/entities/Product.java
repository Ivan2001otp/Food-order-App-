package com.miniproject.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="PRODUCT")
public class Product {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	
	@NotBlank(message="*Required")
	@Size(min=2,max=50,message="min-2 & max-26 characters")
	private String productName;
	
	
	@Min(value=1,message="must be greater than or equal to $1")
	@Max(value=5000,message="must be less than or equal to $10000")
	private Integer price;
	

	
	@NotBlank(message="*Required")
	@Column(length=10000)
	private String description;
	
	
	private String imageUrl;
	
	@ManyToOne
	public Admin admin;
	
	
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Product() {}

	public Product(Long id, String productName, int price, String description, String imageUrl) {
		super();
		this.id = id;
		this.productName = productName;
		this.price = price;
		//this.quantity = quantity;
		this.description = description;
		this.imageUrl = imageUrl;
	}
	
	

	

	
	
	

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
	

}
