package com.capgemini.ars.bean;

public class Users {
	String firstName;
	String lastName;
	String email;
	String password;
	String role;
	double mobileNo;
	public Users(){
		
	}
	public Users(String email, String password, String role, double mobileNo) {
		super();
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
