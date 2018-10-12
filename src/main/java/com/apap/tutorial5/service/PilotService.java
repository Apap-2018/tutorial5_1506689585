package com.apap.tutorial5.service;

import java.util.List;

import com.apap.tutorial5.model.PilotModel;

public interface PilotService {
	PilotModel getPilotDetailByLicenseNumber(String licenseNumber);
//  List<PilotModel> getPilotList();
	void addPilot(PilotModel pilot);
	void deletePilotById(Long id);
	void updatePilotDetailById(long id, PilotModel pilot);
}
