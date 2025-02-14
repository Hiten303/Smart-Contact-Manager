package com.smart.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserRepository;
import com.smart.entities.user;
import com.smart.helper.Message;


import jakarta.validation.Valid;






@Controller
public class HomeController {
    @Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
    @Autowired
	private UserRepository userRepository;
	
	@GetMapping("/home")
	public String home(Model model) {
		model.addAttribute("title","home smart contact manager");
		return "home";
	}
	
	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("title","home smart contact manager");
		return "about";
	}
	
	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title","Register - Smart Contact Manager");
		model.addAttribute("user",new user());
		return "signup";
	}
	
	@PostMapping("/do-register")
	public String registeuser(
			@Valid @ModelAttribute("user") user User, 
			BindingResult result1,
			@RequestParam(value="agreement",defaultValue="false") boolean agreement,
			Model model
			) {
	 try {
		if(!agreement) {
			System.out.println("you have not agreed the terms and conditions");
		    throw new Exception("you have not agreed the terms and conditions");
		}
		
		if(result1.hasErrors()) {
			System.out.println("ERROR"+ result1.toString());
			model.addAttribute("user",User);
			return "signup";
		}
		
		User.setRole("ROLE_USER");
		User.setEnable(true);
		User.setPassword(passwordEncoder.encode(User.getPassword()));
		
		
		System.out.println("Agreement"+agreement);
		System.out.println("User"+User);
		
		user result =this.userRepository.save(User);
		
		model.addAttribute("user",new user());
		model.addAttribute("message", new Message("successfully registered !!","alert-success"));
		return "signup";
	 }
	 catch(Exception e){
		 e.printStackTrace();
		 model.addAttribute("user",User);
		 model.addAttribute("message", new Message ("Something went wrong"+ e.getMessage(),"alert-danger"));
		 return "signup";
	 }
	}
	
	
}
