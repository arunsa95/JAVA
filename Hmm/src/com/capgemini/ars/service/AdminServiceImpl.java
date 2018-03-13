package com.capgemini.ars.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.capgemini.ars.bean.FlightInformation;
import com.capgemini.ars.dao.AdminDao;
import com.capgemini.ars.exception.AirlineException;
@Component("AdminServiceImpl")
@EnableTransactionManagement
@Transactional
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminDao adminDao;

	public AdminServiceImpl()
	{
		super();
	}

	public AdminServiceImpl(AdminDao adminDao)
	{
		super();
		this.adminDao=adminDao;
	}

	public  AdminDao getAdminDao() {
		return adminDao;
	}

	public  void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}

	@Override
	public int insertFlightDetails(FlightInformation flight)
			throws AirlineException {
		return adminDao.insertFlightDetails(flight);
	}

	@Override
	public int updateFlightDetails(FlightInformation flight)
			throws AirlineException {
		return adminDao.updateFlightDetails(flight);
	}

	@Override
	public int cancelFlightDetails(String flightId) throws AirlineException {
		return adminDao.cancelFlightDetails(flightId);
	}

	@Override
	public List<FlightInformation> viewFlightDetails() throws AirlineException {
		return adminDao.viewFlightDetails();
	}

	@Override
	public FlightInformation searchFlightDetailsById(String flightId)
			throws AirlineException {
		return adminDao.searchFlightDetailsById(flightId);
	}

	@Override
	public ArrayList<FlightInformation> searchFlightDetailsByPlace(
			String source, String destination) throws AirlineException {
		return adminDao.searchFlightDetailsByPlace(source, destination);
	}


	@Override
	public boolean availableFlightNo(String flightNo) throws AirlineException {
		try{
			FlightInformation flight=adminDao.searchFlightDetailsById(flightNo);
			if(flight.getFlightNumber()!=null)
				return false;
			else
				return true;
		}catch(NullPointerException e)
		{
			return false;
		}
	}



}
