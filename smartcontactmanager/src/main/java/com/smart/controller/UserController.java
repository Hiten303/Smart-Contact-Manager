package com.smart.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.UserRepository;
import com.smart.dao.contactRepository;
import com.smart.entities.contacts;
import com.smart.entities.user;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
   
	@Autowired
	private UserRepository userrepository ;
	@Autowired
	private contactRepository contactrepository;
	
	// method for giving user data 
	@ModelAttribute
	public void addcommonData(Model model , Principal principal) {
		
		String username= principal.getName();	 
		System.out.println("Username "+username);
		
		user User =userrepository.getUserByUserName(username);
		
	    System.out.println("USER " +User);
	    
	    model.addAttribute("user", User);	
	}
	
	// request for mapping 
	@RequestMapping("/index")
	public String dashboard(Model model , Principal principal ){
		return "normal/user_dashboard";
    }
	// open and add form handler
	@GetMapping("/add-contact")
	public String openaddcontactform(Model model) {
		
		model.addAttribute("title", "add contact");
		model.addAttribute("contact", new contacts());
		return "normal/add_contact_form";
	}
	
	@PostMapping("/process-contact")
	public String processcontact(@ModelAttribute contacts contact ,@RequestParam("profileImage") MultipartFile file,Principal principal ,HttpSession session) {
		try {
		String name = principal.getName();
		user User =this.userrepository.getUserByUserName(name);
		
		if(file.isEmpty()) {
			System.out.println(" File is empty ");
            contact.setImage("contactimage.jpeg");
		}
		else {
			contact.setImage(file.getOriginalFilename());
			File savefile=new ClassPathResource("static/img").getFile();
		    Path path= Paths.get(savefile.getAbsolutePath()+File.separator+file.getOriginalFilename());
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			System.out.println(" image is uploaded");
		}
		
		contact.setUser(User);
		User.getContact().add(contact);
		
		this.userrepository.save(User);
		
		System.out.println("data "+contact);
		System.out.println("Added to database");
		
		session.setAttribute("message", new Message("Your contact is added","success"));
		
		}
		catch(Exception e) {
			System.out.println("ERROR "+e.getMessage());
			e.printStackTrace();
			session.setAttribute("message", new Message("something went wrong ","danger"));
		}
		return "normal/add_contact_form";
		
	}
	
	@GetMapping("/showcontact/{page}")
	public String showContact(@PathVariable("page") Integer page, Model model ,Principal principal) {
		
		String username=principal.getName();
		user User =this.userrepository.getUserByUserName(username);
		Pageable pageable =PageRequest.of(page, 5);
        
		Page<contacts> contacts= this.contactrepository.findContactsByUser(User.getId(),pageable);
		
		model.addAttribute("contacts", contacts);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalpages", contacts.getTotalPages());

		model.addAttribute("title", "Show Contacts");
		return "/normal/showcontact";
	}
	
	@GetMapping("/contact/{cId}")
    public String showContactDetails(@PathVariable("cId") Integer cId) {
	   System.out.println("cid"+cId);
		return "normal/contact_detail"; 
   }
}



 