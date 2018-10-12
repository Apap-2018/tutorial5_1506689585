package com.apap.tutorial5.service;

import java.util.List;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;

public interface FlightService {
	void addFlight(FlightModel flight);
	FlightModel getFlightDetailByFlightNumber(String flightNumber);
	void deleteFlightDetailByFlightNumber(String flightNumber);
	void deleteFlightById(long id);
	void updateFlightDetail(String flightNumber, FlightModel flights);
}
