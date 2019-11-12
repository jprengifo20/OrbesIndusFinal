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

import com.hampcode.articlesapp.common.PageInitPaginationSupplier;
import com.hampcode.articlesapp.model.Supplier;
import com.hampcode.articlesapp.service.SupplierService;

@Controller
@RequestMapping("/suppliers")
public class SupplierController {

	protected static final String SUPPLIER_VIEW = "suppliers/showSupplier"; // view template for single article
	protected static final String SUPPLIER_ADD_FORM_VIEW = "suppliers/newSupplier"; // form for new article
	protected static final String SUPPLIER_EDIT_FORM_VIEW = "suppliers/editSupplier"; // form for editing an article
	protected static final String SUPPLIER_PAGE_VIEW = "suppliers/allSuppliers"; // list with pagination
	protected static final String INDEX_VIEW = "index"; // articles with pagination

	

	@Autowired
	private PageInitPaginationSupplier pageInitPagination;
	
	@Autowired
	private SupplierService supplierService;
	
	@GetMapping("/{id}")
	public String getSupplierById(@PathVariable(value = "id") Long supplierId, Model model) {
		model.addAttribute("supplier", supplierService.findById(supplierId));
		return SUPPLIER_VIEW;
	}

	@GetMapping
	public ModelAndView getAllSuppliers(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) {
		ModelAndView modelAndView = pageInitPagination.initPagination(pageSize, page, SUPPLIER_PAGE_VIEW);
		return modelAndView;
	}

	@GetMapping("/new")
	public String newSupplier(Model model) {

		// in case of redirection model will contain article
		if (!model.containsAttribute("supplier")) {
			model.addAttribute("supplier", new Supplier());
		}
		return SUPPLIER_ADD_FORM_VIEW;
	}

	@PostMapping("/create")
	public String createSupplier(@Valid Supplier supplier, BindingResult result, Model model, RedirectAttributes attr) {

		if(result.hasErrors())
		{
			attr.addFlashAttribute("org.springframework.validation.BindingResult.supplier",result);
			attr.addFlashAttribute("supplier",supplier);
			return "redirect:/suppliers/new";
		}
		Supplier newSupplier = supplierService.createSupplier(supplier);
		model.addAttribute("supplier", newSupplier);

		return "redirect:/suppliers/" + newSupplier.getSupplierId();
	}

	@GetMapping("{id}/edit")
	public String editSupplier(@PathVariable(value = "id") Long supplierId, Model model) {
		/*
		 * in case of redirection from '/article/{id}/update' model will contain article
		 * with field values
		 */
		if (!model.containsAttribute("supplier")) {
			model.addAttribute("supplier", supplierService.findById(supplierId));
		}
		return SUPPLIER_EDIT_FORM_VIEW;
	}

	@PostMapping(path = "/{id}/update")
	public String updateSupplier(@PathVariable(value = "id") Long supplierId,  @Valid Supplier supplierDetails,
			BindingResult result, Model model, RedirectAttributes attr) {
 
		if(result.hasErrors())
		{
			attr.addFlashAttribute("org.springframework.validation.BindingResult.supplier",result);
			attr.addFlashAttribute("supplier",supplierDetails);
			
			attr.addFlashAttribute("error", "Error a la hora de actualizar los Proveedores");

			//return "redirect:/suppliers/{id}/edit";
			return "redirect:/suppliers/" + supplierDetails.getSupplierId() + "/edit";
		}	
		supplierService.updateSupplier(supplierId, supplierDetails);
		model.addAttribute("supplier", supplierService.findById(supplierId));
		return "redirect:/suppliers/" + supplierId;
	}

	
	
	/*@PostMapping(path = "/{id}/update")
	public String updateSupplier(@PathVariable(value = "id") Long supplierId, @Valid Supplier supplierDetails,
			BindingResult result, Model model, RedirectAttributes attr) {

		if (result.hasErrors() || supplierService.findById(supplierId) == false) {

			/// After the redirect: flash attributes pass attributes to the model
			attr.addFlashAttribute("org.springframework.validation.BindingResult.supplier", result);
			attr.addFlashAttribute("supplier", supplierDetails);

			attr.addFlashAttribute("error", "No se permite articulos con el mismo titulo y autor");

			return "redirect:/suppliers/" + articleDetails.getArticleId() + "/edit";
		}

		articleService.updateArticle(articleId, articleDetails);
		model.addAttribute("article", articleService.findById(articleId));
		return "redirect:/articles/" + articleId;
	}*/

	
	
	
	
	
	@GetMapping(value = "/{id}/delete")
	public String deleteSupplier(@PathVariable("id") Long supplierId) {
		supplierService.deleteSupplier(supplierId);
		return "redirect:/suppliers";
	}

}
