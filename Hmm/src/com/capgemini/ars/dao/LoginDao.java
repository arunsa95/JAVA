/**
 * 
 */
package com.capgemini.ars.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.capgemini.ars.bean.Users;
import com.capgemini.ars.exception.AirlineException;

/**
 * @author suadhika
 *
 */
@Repository("LoginDao")
public class LoginDao {
	@PersistenceContext
	private EntityManager entityManager;

	public LoginDao() {
		super();
	}

	public LoginDao(EntityManager entityManager)
	{
		super();
		this.entityManager=entityManager;
	}
	public EntityManager getEntityManager() {
		return entityManager;
	}
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	public String logInValidation(String userName,String password) throws AirlineException
	{
		try{
			TypedQuery<Users> query = entityManager.createQuery("SELECT user FROM Users user WHERE email=:uName and password=:pass", Users.class);
			query.setParameter("uName", userName);
			query.setParameter("pass", password);
			Users user=query.getSingleResult();
			return user.getRole();
		}catch(NoResultException e){
			return "invalid";
		}
	}
	public int register(Users user) throws AirlineException{
		int status=1;
		try{
			entityManager.persist(user);
			entityManager.flush();
		}catch(Exception e)
		{
			status=0;
			throw new AirlineException();
		}
		return status ;

	}
	public boolean validUserName(String userName) throws AirlineException {
		boolean flag=true;
		try{
			TypedQuery<Users> query = entityManager.createQuery("SELECT user FROM Users user WHERE user.email=:uName", Users.class);
			query.setParameter("uName", userName);
			Users user=query.getSingleResult();
			if(user.getFirstName()!=null)
				flag=false;
		}catch(NoResultException e)
		{
			 flag=true;
		}
		catch(Exception e)
		{
			throw new AirlineException();
		}
		return flag;
	}

	public String getName(String email) throws AirlineException {
		String sql="SELECT user FROM Users user WHERE email=:email";
		String name="";
		Users user=new Users();
		try{
			TypedQuery<Users> query=entityManager.createQuery(sql, Users.class);
			query.setParameter("email", email);
			user=query.getSingleResult();
			name=user.getFirstName()+" "+user.getLastName();
		} catch (Exception e) {
			throw new AirlineException(
					"ERRROR : Getting first name failed " + e.getMessage());
		}
		return name;
	}	
}
