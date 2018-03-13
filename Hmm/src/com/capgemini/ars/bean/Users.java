package com.capgemini.ars.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
public class Users {
	@Column(name="first_name")
	String firstName;
	@Column(name="last_name")
	String lastName;
	@Id
	@Column(name="email")
	String email;
	@Column(name="password")
	String password;
	@Column(name="role")
	String role;
	@Column(name="mobile_no")
	double mobileNo;
	
	public Users(){
		
	}
	
	public Users(String firstName, String lastName, String email,
			String password, String role, double mobileNo) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.role = role;
		this.mobileNo = mobileNo;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public double getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(double mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "Users [firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", password=" + password + ", role="
				+ role + ", mobileNo=" + mobileNo + "]";
	}
	
	
	
}
