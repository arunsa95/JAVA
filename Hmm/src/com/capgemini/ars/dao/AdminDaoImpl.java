/**
 * 
 */
package com.capgemini.ars.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.capgemini.ars.bean.FlightInformation;
import com.capgemini.ars.exception.AirlineException;

/**
 * @author suadhika
 *
 */
@Repository("AdminDaoImpl")
public class AdminDaoImpl implements AdminDao {

	@PersistenceContext
	EntityManager entityManager;
	
	public AdminDaoImpl() {
		super();
	}
	
	public AdminDaoImpl(EntityManager entityManager)
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


	@Override
	public int insertFlightDetails(FlightInformation flight)
			throws AirlineException {
		int status=1;
		try{
		entityManager.persist(flight);
		entityManager.flush();
		}catch(Exception e)
		{
			status=0;
			throw new AirlineException("Insertion failed!!!! Please insert it again");
		}
		return status ;
	}

	
	@Override
	public int updateFlightDetails(FlightInformation flight)
			throws AirlineException {
		int status=1;
		try{
		entityManager.merge(flight);
		entityManager.flush();
		}catch(Exception e)
		{
			status=0;
			throw new AirlineException();
		}
		return status ;
	}

	
	@Override
	public int cancelFlightDetails(String flightId) throws AirlineException {
		int status=1;
		FlightInformation flightInfo=new FlightInformation();
		try{
			flightInfo=entityManager.find(FlightInformation.class, flightId);
			entityManager.remove(flightInfo);
		}catch(NoResultException e)
		{
			status=0;
		}
		return status ;
	}

	/* (non-Javadoc)
	 * @see com.capgemini.ars.dao.IAdminDao#viewAllFlights()
	 */
	@Override
	public List<FlightInformation> viewFlightDetails() throws AirlineException {
		List<FlightInformation> flightList=null;
		try{
		TypedQuery<FlightInformation> query = entityManager.createQuery("SELECT flight FROM FlightInformation flight", FlightInformation.class);
		flightList= query.getResultList();
		}catch(NoResultException e){
			flightList= new ArrayList<>();
		}
		return flightList;
	}

	/* (non-Javadoc)
	 * @see com.capgemini.ars.dao.IAdminDao#searchFlightDetailsById(java.lang.String)
	 */
	@Override
	public FlightInformation searchFlightDetailsById(String flightId) throws AirlineException {
		FlightInformation flight= new FlightInformation();
		flight= entityManager.find(FlightInformation.class, flightId);
		return flight;
	}

	/* (non-Javadoc)
	 * @see com.capgemini.ars.dao.IAdminDao#searchFlightDetailsByPlace(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<FlightInformation> searchFlightDetailsByPlace(String source, String destination)
			throws AirlineException {
		ArrayList<FlightInformation> flightList=null;
		try{
		TypedQuery<FlightInformation> query = entityManager.createQuery("SELECT flight FROM FlightInformation flight WHERE departureCity=:dep and arrivalCity=:arr", FlightInformation.class);
		query.setParameter("arr", destination);
		query.setParameter("dep", source);
		flightList = (ArrayList<FlightInformation>) query.getResultList();
		}catch(NoResultException e)
		{
			flightList= new ArrayList<>();
		}
		return flightList;
	}

}
