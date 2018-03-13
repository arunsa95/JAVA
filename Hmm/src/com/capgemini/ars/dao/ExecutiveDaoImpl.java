package com.capgemini.ars.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.capgemini.ars.bean.FlightInformation;
import com.capgemini.ars.exception.AirlineException;

@Repository("ExecutiveDaoImpl")
public class ExecutiveDaoImpl implements ExecutiveDao {
	@PersistenceContext
	private EntityManager entityManager;

	public ExecutiveDaoImpl()
	{
		super();
	}
	public ExecutiveDaoImpl(EntityManager entityManager)
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
	public List<FlightInformation> displayFlightDetails()
			throws AirlineException {
		TypedQuery<FlightInformation> query = entityManager.createQuery("SELECT flight FROM FlightInformation flight", FlightInformation.class);
		return query.getResultList();
	}

	@Override
	public List<FlightInformation> displayFlightOccupancyByPeriod(
			LocalDate fromDate, LocalDate toDate) throws AirlineException {
		TypedQuery<FlightInformation> query = entityManager.createQuery("SELECT flight FROM FlightInformation flight WHERE departureDate between :fromDate and :toDate", FlightInformation.class);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		return (ArrayList<FlightInformation>) query.getResultList();
	}

	
	@Override
	public List<FlightInformation> displayFlightOccupancyByPlace(String source,
			String destination) throws AirlineException {

		TypedQuery<FlightInformation> query = entityManager.createQuery("SELECT flight FROM FlightInformation flight WHERE departureCity=:dep and arrivalCity=:arr", FlightInformation.class);
		query.setParameter("arr", source);
		query.setParameter("dep", destination);
		return (ArrayList<FlightInformation>) query.getResultList();
	}

}
