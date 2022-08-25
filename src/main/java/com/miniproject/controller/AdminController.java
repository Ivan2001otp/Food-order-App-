package com.miniproject.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.miniproject.dao.AdminRepository;

import com.miniproject.dao.ProductRepository;
import com.miniproject.entities.Admin;
import com.miniproject.entities.Product;
import com.miniproject.helper.Message;

@Controller
@RequestMapping("/admin")
public class AdminController {
	//remember to add Principal - p
	//handler method for admin main dashboard-add,update,deleteall,deletebyi
	
	
	@Autowired
	private AdminRepository adminRepository;
	

	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping("/dashboard")
	public String AdminDashBoard(Model model,Principal principal) {
	
		String adminName = principal.getName();
		
		Admin a = this.adminRepository.getAdminByName(adminName);
		
		
		model.addAttribute("products",this.productRepository.getAllAdminProducts(a.getAdminId()));
		//model.addAttribute("products",a.productList);
		
		model.addAttribute("nameAdmin",a.getName());
		
		return "admin_dashboard";
	}
	
	//handler method to add the product to the database by the admin
	@GetMapping("/admin_product_choice")
	public String AdminActivity(Model model) {
		model.addAttribute("title","Admin Activity");
		model.addAttribute("product",new Product());
		return "admin_activity";
	}
	
	
	//handler method to Request the POST of above method
	@PostMapping("/admin_product_process")
	public String AddProductByAdmin(@Valid @ModelAttribute("product") Product product
			,BindingResult result,@RequestParam("productImage") MultipartFile file
			,Model model,Principal principal,HttpSession session) {
	
		
					//System.out.println("img file is empty");
					
		try {	
			
			
			if(result.hasErrors()) {	
					System.out.println(" Errors - "+result);
					return "admin_activity";
			}
			String admin_name = principal.getName();
			
			Admin admin = this.adminRepository.getAdminByName(admin_name);
			
			product.setAdmin(admin);
			
		
			
			
			//uploading and processing image.
			
			
			//Date date = new Date();
			
			product.setImageUrl(file.getOriginalFilename());
			
			File savefile = new ClassPathResource("static/img1").getFile();
			
			Paths.get(savefile.getAbsolutePath()+File.separator+file.getOriginalFilename());
			
			Files.copy(file.getInputStream(),
					Paths.get(savefile.getAbsolutePath()+File.separator+file.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			
			System.out.println("image is uploaded");
			
		
			admin.productList.add(product);
			
			//for image
			//model.addAttribute("Products",admin.productList);
			
			System.out.println(product.getImageUrl());
			
			System.out.println(product.getProductName());
			System.out.println(product.getPrice());
			System.out.println(product.getImageUrl());
			System.out.println(product.getDescription());
			System.out.println(product.getAdmin());
			
			
			
			model.addAttribute("product",new Product());
			
			productRepository.save(product);
			System.out.println("product data saved successfully!");
			
			session.setAttribute("message",new Message("Successfully Added","alert-success"));
			
			return "admin_activity";
		}catch(Exception e){
			System.out.println(e.getMessage());
			System.out.println("exception executed!");
			System.out.println("Or price field is null");
			
			session.setAttribute("message",new Message("Something Went Wrong","alert-danger"));
			
			return "admin_activity";
		}
		
		
		
		
	}
	
	//handler method to update the product
	@GetMapping("/edit-product/{id}")
	public String UpdateProduct(@PathVariable Long id,Model model) {
		Product currentProduct = this.productRepository.findById(id).get();
		
		model.addAttribute("title","Update the product");
		
		model.addAttribute("adminProduct",currentProduct);
		return "update_product";
	}
	
	
	//handler method for update-process of product
	@PostMapping("/edit/{id}")
	public String UpdateProductProcess(@Valid @ModelAttribute("adminProduct") Product product
			,BindingResult result,@PathVariable Long id,@RequestParam("productImage") MultipartFile file
			,Model model,Principal principal,HttpSession session){
		
			
			Product OldProduct = this.productRepository.findById(id).get();
			
			try {	
			
			
			if(result.hasErrors()) {	
					System.out.println(" Errors - "+result);
					return "redirect:/admin/admin_dashboard";
			}
			
			
			if(!file.isEmpty()) {
				//then user has selected the new fileimage.replace with the old one.
				
				//deletes the old image
				File deleteFile = new ClassPathResource("static/img1").getFile();
				File file1 = new File(deleteFile,OldProduct.getImageUrl());
				file1.delete();
				
				
				//updates the image
				File savefile = new ClassPathResource("static/img1").getFile();
				
				Paths.get(savefile.getAbsolutePath()+File.separator+file.getOriginalFilename());
				
				Files.copy(file.getInputStream(),
						Paths.get(savefile.getAbsolutePath()+File.separator+file.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
				
				product.setImageUrl(file.getOriginalFilename());//updates the file name in 
				
				
			}
			else {
				//retain the old image.
				//if the img is not selected then,the old image is set by default.
				product.setImageUrl(OldProduct.getDescription());
				
			}
			
			/* here write the code of the field data
			  */
			System.out.println("product Name - "+product.getProductName());
			System.out.println("productId-"+OldProduct.getId());
			
			
			String admin_name = principal.getName();
			
			Admin admin = this.adminRepository.getAdminByName(admin_name);
			
			product.setAdmin(admin);
			
			
			
			
			OldProduct.setAdmin(null);
			this.productRepository.delete(OldProduct);
			
			this.productRepository.save(product);
			
			session.setAttribute("message",new Message("Successfully updated","alert-success"));
			
			
			return "redirect:/admin/dashboard";
		}catch(Exception e){
			
			session.setAttribute("message",new Message("Something went wrong","alert-danger"));
			
			System.out.println(e.getMessage());
			System.out.println("exception executed!");
			System.out.println("price field is null");
			return "redirect:/admin/dashboard";
		}
		
		
	}
	
	
	
	//handler method to delete the product
	@GetMapping("/delete-product/{id}")
	public String DeleteProduct(@PathVariable("id") Long id,HttpSession session) {
		
		System.out.println(id);
		
		Product p1 = this.productRepository.findById(id).get();
		
		p1.setAdmin(null);
		
		this.productRepository.delete(p1);
		
		session.setAttribute("message",new Message("Successfully-Deleted!","alert-success"));
		
		return "redirect:/admin/dashboard";
	}
	
	
	//delete all function.
	
	
	
	
	
}
