package com.apap.tutorial5.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.service.FlightService;
import com.apap.tutorial5.service.PilotService;

@Controller
public class FlightController {
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
//		FlightModel flight = new FlightModel();
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		pilot.setPilotFlight(new ArrayList<FlightModel>());
		FlightModel flight = new FlightModel();
		pilot.getPilotFlight().add(flight);
//		flight.setPilot(pilot);
//		
		model.addAttribute("pilot", pilot);
		model.addAttribute("title", "Add Flight");
		return "addFlight";
	}
	
	@RequestMapping(value="/flight/add/{licenseNumber}", params={"addRow"})
	public String addFlightRow(PilotModel pilot, BindingResult bindingResult, Model model) {
		pilot.getPilotFlight().add(new FlightModel());
		model.addAttribute("pilot", pilot);
		model.addAttribute("title", "Add Flight");
		return "addFlight";
	}
	
	@RequestMapping(value="/flight/add/{licenseNumber}", params={"removeRow"})
	public String removeRow(PilotModel pilot, BindingResult bindingResult, HttpServletRequest req, Model model) {
	   int rowId = Integer.parseInt(req.getParameter("removeRow"));
	   pilot.getPilotFlight().remove(rowId);
	   model.addAttribute("pilot", pilot);
		model.addAttribute("title", "Add Flight");
	    return "addFlight";
	}
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", params= {"submit"}, method = RequestMethod.POST)
	private String SubmitFlight(@ModelAttribute PilotModel pilot, Model model) {
		PilotModel pilotDetail = pilotService.getPilotDetailByLicenseNumber(pilot.getLicenseNumber());
		for(FlightModel flight : pilot.getPilotFlight()) {
			flight.setPilot(pilotDetail);
			flightService.addFlight(flight);
			model.addAttribute("title", "Add Flight");
		}
		return "add";	
	}
	
	@RequestMapping(value = "/flight/add", method = RequestMethod.POST)
	private String addFlightSubmit(@ModelAttribute FlightModel flight, Model model) {
		flightService.addFlight(flight);
		model.addAttribute("title", "Add Flight");
		return "add";
	}
	
	@RequestMapping("/flight/view")
	private String viewPilotFlights(@RequestParam(value="flightNumber") String flightNumber, Model model) {
		FlightModel flightProfile = flightService.getFlightDetailByFlightNumber(flightNumber);
		model.addAttribute("title", "View Flight");
		model.addAttribute("flight", flightProfile);
		return "view-flight";
	}
	
//	@RequestMapping("/flight/delete/{flightNumber}")
//	private String deleteflight(@PathVariable String flightNumber, Model model) {
//		flightService.deleteFlightDetailByFlightNumber(flightNumber);
//		return "delete-pilot";
//	}

	@RequestMapping(value = "/flight/delete", method = RequestMethod.POST)
	private String deleteFlight(@ModelAttribute PilotModel pilot, Model model) {
		for(FlightModel flight : pilot.getPilotFlight()) {
			flightService.deleteFlightById(flight.getId());
			model.addAttribute("title", "Delete Flight");
		}
		return "delete-pilotFlight";	
	}
	
	@RequestMapping(value = "/flight/update/{flightNumber}", method = RequestMethod.GET)
	private String updateFlight (@PathVariable(value="flightNumber") String flightNumber, Model model) {
		FlightModel flights = flightService.getFlightDetailByFlightNumber(flightNumber);
		model.addAttribute("flight", flights);
		model.addAttribute("title", "Update Flight");
		return "update-flight";	
	}
	
	@RequestMapping(value = "/flight/update/{flightNumber}", method = RequestMethod.POST)
	private String saveUpdate (@ModelAttribute FlightModel flights, @PathVariable(value="flightNumber") String flightNumber, Model model) {
		flightService.updateFlightDetail(flightNumber, flights);
		model.addAttribute("title", "Update Flight");
		return "add";
	}
}