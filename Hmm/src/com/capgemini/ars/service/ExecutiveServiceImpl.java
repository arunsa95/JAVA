package com.capgemini.ars.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.capgemini.ars.bean.FlightInformation;
import com.capgemini.ars.dao.ExecutiveDao;
import com.capgemini.ars.exception.AirlineException;
@Component("ExecutiveServiceImpl")
@EnableTransactionManagement
@Transactional
public class ExecutiveServiceImpl implements ExecutiveService {

	@Autowired
	private ExecutiveDao executiveDao;

	public ExecutiveServiceImpl()
	{
		super();
	}
	public ExecutiveServiceImpl(ExecutiveDao executiveDao)
	{
		super();
		this.executiveDao=executiveDao;
	}
	
	
	public  ExecutiveDao getExecutiveDao() {
		return executiveDao;
	}

	public  void setExecutiveDao(ExecutiveDao executiveDao) {
		this.executiveDao = executiveDao;
	}

	@Override
	public List<FlightInformation> displayFlightDetails()
			throws AirlineException {

		return executiveDao.displayFlightDetails();
	}

	@Override
	public List<FlightInformation> displayFlightOccupancyByPeriod(
			LocalDate fromDate, LocalDate toDate) throws AirlineException {

		return executiveDao.displayFlightOccupancyByPeriod(fromDate, toDate);
	}

	@Override
	public List<FlightInformation> displayFlightOccupancyByPlace(String source,
			String destination) throws AirlineException {

		return executiveDao.displayFlightOccupancyByPlace(source, destination);
	}

	

}
