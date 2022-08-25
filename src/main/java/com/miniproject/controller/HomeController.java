package com.miniproject.controller;
import java.util.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import java.security.*;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.miniproject.dao.AdminRepository;
import com.miniproject.dao.ProductRepository;
import com.miniproject.dao.UserRepository;
import com.miniproject.entities.Admin;
import com.miniproject.entities.Product;
import com.miniproject.entities.User;
import com.miniproject.helper.AdminMessage;
import com.miniproject.helper.Message;

import com.razorpay.*;

@Controller
public class HomeController {
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private ProductRepository productRespository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	//payment - create handler
	
	@PostMapping("/create_order")
	@ResponseBody
	public String createOrder(@RequestBody Map<String,Object> data) throws Exception {
		
		//System.out.println("Order printed");
		System.out.println(data);
		
		int amt =Integer.parseInt(data.get("amount").toString());
		
		System.out.println(amt);
		
		var client = new RazorpayClient(/*key*/"rzp_test_3WdFkgfwuWTfRd",/*secret*/"uIHuzeZgtLoTszajWh7kcwBO");
		
		JSONObject ob = new JSONObject();
		ob.put("amount",amt*100);
		ob.put("currency","INR");
		ob.put("receipt","txn_235425");
		
		
		//create the new order.
		Order order = client.orders.create(ob);
		System.out.println("New ORder created");
		System.out.println(order);
		
		//save this the database 
		
		return order.toString();
	}
	
	
	
	//handler for home page.
	@GetMapping("/")
	public String HomePage(Model m) {
		m.addAttribute("title","Welcome - Home Page");
		System.out.println("this is home page");
		return "home1";
	}
	
	//handler for about page
	@GetMapping("/about")
	public String AboutPage(Model m) {
		m.addAttribute("title","About Us !");
		return "about1";
	}
	
	//handler for user-signup
	@GetMapping("/signup")
	public String SignUpPage(Model m) {
		m.addAttribute("title","SignUp page");
		m.addAttribute("user",new User());
		return "signup1";
	}
	
	//handler excepting the response from user - sign up page.
	
	@PostMapping("/do_register")
	public String RegisterUser(@Valid @ModelAttribute("user") User user,BindingResult result
			,Model model,HttpSession session){
		
		System.out.println(user.getEmail());
		System.out.println(user.getFirstName());
		System.out.println(user.getLastName());
		System.out.println(user.getPassword());
		System.out.println(user.getRole());
		System.out.println(user.getId());
		
		
		try {
			
			if(result.hasErrors()) {
				System.out.println(result);
				return "signup1";
			}
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setEnabled(true);
			user.setRole("ROLE_USER");
		
		
			
			session.setAttribute("message",new Message("Registered Successfully!","alert-success"));
			
			model.addAttribute("user",new User());
			
			User u = this.userRepository.save(user);
			
			return "signup1";
		}catch(Exception e){
			
			System.out.println("catched error!");
			session.setAttribute("message",new Message("Something Wrong happened!","alert-danger"));
			return "signup1";
		}
		
		
			
	}
	
	//shopping cart module page.
	
	@GetMapping("/shopping-cart/{page}")
	public String ShoppingCartUI(@PathVariable("page") Integer page,Model model) {
		
		
		Pageable pageable = PageRequest.of(page,3);
		
		Page<Product>all_food_items = this.productRespository.showAllProducts(pageable);
		
	
		
		model.addAttribute("title","Dish Cart !");
		
		model.addAttribute("products",all_food_items);
		model.addAttribute("currentPage",page);
		model.addAttribute("totalPages",all_food_items.getTotalPages());
		
		return "shopping_cart";
	}
	
	@GetMapping("/pay-online")
	public String paymentStatus(Model model){
		model.addAttribute("title","Payment Page");
		
		return "payment_page";
	}
	
	
	
	
	//----------------------admin----------------------
	
	//handler method to register admin.
	@GetMapping("/admin_signup")
	public String AdminUserSignUpPage(Model model) {
		
		model.addAttribute("title","Admin-Register page");
		
		model.addAttribute("admin",new Admin());
		
		return "admin_signup";
	}
	
	//handler method to except the response from admin-register-page
	@PostMapping("/register_admin")
	public String RegisterAdmin(@Valid @ModelAttribute("admin") Admin admin,BindingResult result
							,Model model,HttpSession session) {
			
		try {
			if(result.hasErrors()) {
				System.out.println(result);
				//System.out.println(admin);
				return "admin_signup";
			}
			
			admin.setPassword(passwordEncoder.encode(admin.getPassword()));
			admin.setRole("ROLE_ADMIN");
			admin.setEnabled(true);
			
			
			//System.out.println(admin);
			model.addAttribute("admin",new Admin());
			
			Admin a = this.adminRepository.save(admin);//saves to the database.
			
			System.out.println(a);
			
			session.setAttribute("admin_message",new AdminMessage("Successfully Registered!","alert-success"));
			
			return "admin_signup";
		}catch(Exception e){
			model.addAttribute("admin",new Admin());
			session.setAttribute("admin_message",new AdminMessage("Something went Wrong!","alert-danger"));
			
			return "admin_signup";
		}
		
	}
	
		
	
	
	
	
	
	

}

