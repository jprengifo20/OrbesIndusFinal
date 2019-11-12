package com.hampcode.articlesapp.controller;

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

import com.hampcode.articlesapp.common.PageInitPaginationPurcharseOrder;
import com.hampcode.articlesapp.model.PurchaseOrder;
import com.hampcode.articlesapp.service.PurchaseOrderService;

@Controller
@RequestMapping("/purchaseorders")
public class PurchaseOrderController {
	protected static final String PURCHASEORDER_VIEW = "purchaseorders/showPurchaseOrder"; // view template for single article
	protected static final String PURCHASEORDER_ADD_FORM_VIEW = "purchaseorders/newPurchaseOrder"; // form for new article
	protected static final String PURCHASEORDER_EDIT_FORM_VIEW = "purchaseorders/editPurchaseOrder"; // form for editing an article

	protected static final String PURCHASEORDER_PAGE_VIEW = "purchaseorders/allPurchaseOrders"; // list with pagination

	protected static final String INDEX_VIEW = "index"; // articles with pagination

	@Autowired
	private PageInitPaginationPurcharseOrder pageInitiPagination;
	
	@Autowired
	private PurchaseOrderService purchaseorderService;

	@GetMapping("/{id}")
	public String getPurchaseOrderById(@PathVariable(value = "id") Long purchaseorderId, Model model) {
		model.addAttribute("purchaseorder", purchaseorderService.findById(purchaseorderId));
		return PURCHASEORDER_VIEW;
	}

	
	@GetMapping
	public ModelAndView getAllPurchaseOrders(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) {
		ModelAndView modelAndView = pageInitiPagination.initPagination(pageSize, page, PURCHASEORDER_PAGE_VIEW);
		return modelAndView;
	}
	
	@GetMapping("/new")
	public String newPurchaseOrder(Model model) {

		// in case of redirection model will contain article
		if (!model.containsAttribute("purchaseorder")) {
			model.addAttribute("purchaseorder", new PurchaseOrder());
		}
		return PURCHASEORDER_ADD_FORM_VIEW;
	}

	@PostMapping("/create")
	public String createPurchaseOrder(@Valid PurchaseOrder purchaseorder, BindingResult result, Model model, RedirectAttributes attr) {

		if (result.hasErrors() ) {

			// After the redirect: flash attributes pass attributes to the model
			attr.addFlashAttribute("org.springframework.validation.BindingResult.purchaseorder", result);
			attr.addFlashAttribute("purchaseorder", purchaseorder);

			/*attr.addFlashAttribute("error", "Error a la hora de crear la solicitud");*/

			return "redirect:/purchaseorders/new";
		}
		PurchaseOrder newPurchaseOrder = purchaseorderService.createPurchaseOrder(purchaseorder);
		model.addAttribute("purchaseorder", newPurchaseOrder);

		return "redirect:/purchaseorders/" + newPurchaseOrder.getId();
	}

	@GetMapping("{id}/edit")
	public String editPurchaseOrder(@PathVariable(value = "id") Long purchaseorderId, Model model) {
		/*
		 * in case of redirection from '/article/{id}/update' model will contain article
		 * with field values
		 */
		if (!model.containsAttribute("purchaseorder")) {
			model.addAttribute("purchaseorder", purchaseorderService.findById(purchaseorderId));
		}
		return PURCHASEORDER_EDIT_FORM_VIEW;
	}

	@PostMapping(path = "/{id}/update")
	public String updatePurchaseOrder(@PathVariable(value = "id") Long purchaseorderId, @Valid PurchaseOrder purchaseorderDetails,
			BindingResult result, Model model, RedirectAttributes attr) {

		if (result.hasErrors() ) {

			/// After the redirect: flash attributes pass attributes to the model
			attr.addFlashAttribute("org.springframework.validation.BindingResult.purchaseorder", result);
			attr.addFlashAttribute("purchaseorder", purchaseorderDetails);

			attr.addFlashAttribute("error", "Error a la hora de actualizar la solicitud");

			return "redirect:/purchaseorders/" + purchaseorderDetails.getId() + "/edit";
		}

		purchaseorderService.updatePurchaseOrder(purchaseorderId, purchaseorderDetails);
		model.addAttribute("purchaseorder", purchaseorderService.findById(purchaseorderId));
		return "redirect:/purchaseorders/" + purchaseorderId;
	}

	@GetMapping(value = "/{id}/delete")
	public String deletePurchaseOrder(@PathVariable("id") Long purchaseorderId) {
		//Article article = articleService.findById(articleId);
		// String title = article.getTitle();
		purchaseorderService.deletePurchaseOrder(purchaseorderId);
		return "redirect:/purchaseorders";
	}
}
