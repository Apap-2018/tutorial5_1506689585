package com.apap.tutorial5.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.repository.PilotDb;

@Service
@Transactional
public class PilotServiceImpl implements PilotService {
	@Autowired
	private PilotDb pilotDb;

	@Override
	public PilotModel getPilotDetailByLicenseNumber(String licenseNumber) {
		return pilotDb.findByLicenseNumber(licenseNumber);
	}
	
	@Override
	public void addPilot(PilotModel pilot) {
		pilotDb.save(pilot);
	}
	
	@Override
	public void deletePilotById(Long id) {
		pilotDb.deleteById(id);
	}
	
	@Override
	public void updatePilotDetailById(long id, PilotModel pilot) {
		PilotModel ProfilePilot = pilotDb.getOne(id);
		
		ProfilePilot.setName(pilot.getName());
		ProfilePilot.setFlyHour(pilot.getFlyHour());
	}
}
