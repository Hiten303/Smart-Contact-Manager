package com.smart.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="CONTACT")
public class contacts {
	
	//	@Override
//	public String toString() {
//		return "contacts [cId=" + cId + ", name=" + name + ", secondname=" + secondname + ", work=" + work + ", email="
//				+ email + ", phone=" + phone + ", image=" + image + ", description=" + description + ", User=" + User
//				+ "]";
//	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int cId;
	private String  name;
	private String  secondname;
	private String  work;
	private String  email;
	private String  phone;
	private String  image;
	@Column(length=1000)
	private String  description;
	
	@ManyToOne
	private user User;
	
	public user getUser() {
		return User;
	}
	public void setUser(user user) {
		User = user;
	}
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId = cId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSecondname() {
		return secondname;
	}
	public void setSecondname(String secondname) {
		this.secondname = secondname;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		return this.cId==((contacts)obj).getcId();
	}
}
