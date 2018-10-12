package com.apap.tutorial5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.repository.PilotDb;
import com.apap.tutorial5.service.PilotService;

@Controller
public class PilotController {
	@Autowired
	private PilotService pilotService;
	
	@Autowired
	private PilotDb pilotDb;
	
	@RequestMapping("/")
	private String home() {
		return "home";
	}
	
	@RequestMapping(value = "/pilot/add", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("pilot", new PilotModel());
		model.addAttribute("title", "Add Pilot");
		return "addPilot";
	}
	
	@RequestMapping(value = "/pilot/add", method = RequestMethod.POST)
	private String addPilotSubmit(@ModelAttribute PilotModel pilot, Model model) {
		pilotService.addPilot(pilot);
		model.addAttribute("title", "Add Pilot");
		return "add";
	}
	
//	@RequestMapping("/pilot/view")
//	private String viewPilot(@RequestParam(value="licenseNumber") String licenseNumber, Model model) {
//		PilotModel pilotProfile = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
//		model.addAttribute("pilot", pilotProfile);
//		return "view-pilot";
//	}
	
	@RequestMapping(value = "/pilot/view", method = RequestMethod.GET)
	private String viewPilot(@RequestParam("licenseNumber") String licenseNumber, Model model) {
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		
		model.addAttribute("pilot", pilot);
		model.addAttribute("title", "View Pilot");
		return "view-pilot";
	}
	
	@RequestMapping("/pilot/delete/{id}")
	private String deletePilot(@PathVariable(value="id") Long id, Model model) {
		pilotService.deletePilotById(id);
		model.addAttribute("title", "Delete Pilot");
		return "delete-pilotFlight";
	}
	
	@RequestMapping(value = "/pilot/update/{id}", method = RequestMethod.GET)
	private String updatePilot (@PathVariable(value="id") Long id, Model model) {
		PilotModel pilots = pilotDb.getOne(id);
		model.addAttribute("pilot", pilots);
		model.addAttribute("title", "Update Pilot");
		return "update-pilot";	
	}
	
	@RequestMapping(value = "/pilot/update/{id}", method = RequestMethod.POST)
	private String saveUpdate (@ModelAttribute PilotModel pilots, @PathVariable(value="id") Long id, Model model) {
		pilotService.updatePilotDetailById(id, pilots);
		model.addAttribute("title", "Update Pilot");
		return "add";
	}
}
