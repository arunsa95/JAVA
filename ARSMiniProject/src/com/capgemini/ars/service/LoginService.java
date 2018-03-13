package com.capgemini.ars.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.capgemini.ars.bean.Users;
import com.capgemini.ars.dao.LoginDao;
import com.capgemini.ars.exception.AirlineException;

public class LoginService {
	LoginDao loginDao;
	public LoginService() {
		loginDao=new LoginDao();
	}
	public String logInValidation(String username,String password) throws AirlineException
	{
		return loginDao.logInValidation(username, password);
	}
	public int register(Users user) throws AirlineException{
		return loginDao.register(user);
	}
	public boolean validatePassword(String pass) {
		// creating regex pattern String
				String patternStr = "^((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!'\"]).{6,20})$";
				
				// Now create Pattern object.
				Pattern pattern = Pattern.compile(patternStr);
				// Now create matcher object.
				Matcher matcher = pattern.matcher(pass);
				//now matcher matches
				return matcher.matches();
	}
	public boolean validUserName(String userName) throws AirlineException {
		return loginDao.validUserName(userName);
	}
	public boolean validateMobileNo(String phone) {
		
				// creating regex pattern String
				String patternStr = "^[789][0-9]{9}$";
				// 10 digits phone number
				// 1st number must start with 7 or 8 or 9
				// followed by 9 digits
				// Now create matcher object.

				Pattern pattern = Pattern.compile(patternStr);
				Matcher matcher = pattern.matcher(phone);
				return matcher.matches();
	}
	public String getName(String email) throws AirlineException {
		return loginDao.getName(email);
	}
}
