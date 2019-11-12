package com.hampcode.articlesapp.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hampcode.articlesapp.common.PageInitPaginationIncident;
import com.hampcode.articlesapp.model.Incident;
import com.hampcode.articlesapp.model.Supplier;
import com.hampcode.articlesapp.service.IncidentService;
import com.hampcode.articlesapp.service.SupplierService;

@Controller
@RequestMapping("/incidents")
public class IncidentController {
	
	protected static final String INCIDENT_VIEW = "incidents/showIncident"; // view template for single article
	protected static final String INCIDENT_ADD_FORM_VIEW = "incidents/newIncident"; // form for new article
	protected static final String INCIDENT_EDIT_FORM_VIEW = "incidents/editIncident"; // form for editing an article
	protected static final String INCIDENT_PAGE_VIEW = "incidents/allIncidents"; // list with pagination
	protected static final String INDEX_VIEW = "index"; // articles with pagination
	
	@Autowired
	private PageInitPaginationIncident pageInitPagination;
	
	@Autowired
	private IncidentService incidentService;
	@Autowired
	private SupplierService supplierService;

	@GetMapping("/{id}")
	public String getIncidentById(@PathVariable(value = "id") Long incidentId, Model model) {
		model.addAttribute("incident", incidentService.findById(incidentId));
		return INCIDENT_VIEW;
	}
	
	@GetMapping
	public ModelAndView getAllIncidents(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) {
		ModelAndView modelAndView = pageInitPagination.initPagination(pageSize, page, INCIDENT_PAGE_VIEW);
		return modelAndView;
	}
	
	@GetMapping("/new")
	public String newIncident(Model model) {

		// in case of redirection model will contain article
		if (!model.containsAttribute("incident")) {
			model.addAttribute("incident", new Incident());
			 List<Supplier> suppliers = supplierService.getAllSupplier();
		     model.addAttribute("suppliers", suppliers);
		}
		return INCIDENT_ADD_FORM_VIEW;
	}

	@PostMapping("/create")
	public String createIncident(@Valid Incident incident, BindingResult result, Model model, RedirectAttributes attr) {

		if (result.hasErrors()/* || incidentService.valid(incident) == false*/) {

		
			attr.addFlashAttribute("org.springframework.validation.BindingResult.incident", result);
			attr.addFlashAttribute("incident", incident);
			return "redirect:/incidents/new";
		}
		Incident newIncident = incidentService.createIncident(incident);
		model.addAttribute("incident", newIncident);

		return "redirect:/incidents/" + newIncident.getId();//verificar despues
	}
	
	@GetMapping("{id}/edit")
	public String editIncident(@PathVariable(value = "id") Long incidentId, Model model) {
		/*
		 * in case of redirection from '/article/{id}/update' model will contain incident
		 * with field values
		 */
		if (!model.containsAttribute("incident")) {
			 List<Supplier> suppliers = supplierService.getAllSupplier();
		        model.addAttribute("suppliers", suppliers);
			model.addAttribute("incident", incidentService.findById(incidentId));
			
		}
		return INCIDENT_EDIT_FORM_VIEW;
	}
	
	@PostMapping(path = "/{id}/update")//revisar
	public String updateIncident(@PathVariable(value = "id") Long incidentId, @Valid Incident incidentDetails,
			BindingResult result, Model model, RedirectAttributes attr) {

		if (result.hasErrors()/* || incidentService.valid(incidentDetails) == false*/) {//revisar

			/// After the redirect: flash attributes pass attributes to the model
			attr.addFlashAttribute("org.springframework.validation.BindingResult.incident", result);
			attr.addFlashAttribute("incident", incidentDetails);

			attr.addFlashAttribute("error", "No se permite esta incidencias");

			return "redirect:/incidents/" + incidentDetails.getId() + "/edit";
		}

		incidentService.updateIncident(incidentId, incidentDetails);
		model.addAttribute("incident", incidentService.findById(incidentId));
		return "redirect:/incidents/" + incidentId;
	}
	
	@GetMapping(value = "/{id}/delete")
	public String deleteIncident(@PathVariable("id") Long incidentId) {
		incidentService.deleteIncident(incidentId);
		return "redirect:/incidents";
	}
}
