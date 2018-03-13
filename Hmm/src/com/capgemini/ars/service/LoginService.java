package com.capgemini.ars.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.capgemini.ars.bean.Users;
import com.capgemini.ars.dao.LoginDao;
import com.capgemini.ars.exception.AirlineException;
@Component("LoginService")
@EnableTransactionManagement
@Transactional
public class LoginService {
	@Autowired
	LoginDao loginDao;
	public LoginService()
	{
		super();
	}
	public LoginService(LoginDao loginDao)
	{
		super();
		this.loginDao=loginDao;
	}
	public LoginDao getLoginDao() {
		return loginDao;
	}
	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}
	
	public String logInValidation(String username,String password) throws AirlineException
	{
		return loginDao.logInValidation(username, password);
	}
	public int register(Users user) throws AirlineException{
		return loginDao.register(user);
	}
	public boolean validUserName(String userName) throws AirlineException {
		return loginDao.validUserName(userName);
	}
	public String getName(String email) throws AirlineException {
		return loginDao.getName(email);
	}
}
