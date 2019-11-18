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

import com.hampcode.articlesapp.common.PageInitPaginationPurchaseOrder;
import com.hampcode.articlesapp.model.Product;
import com.hampcode.articlesapp.model.PurchaseOrder;
import com.hampcode.articlesapp.model.Supplier;
import com.hampcode.articlesapp.service.ProductService;
import com.hampcode.articlesapp.service.PurchaseOrderService;
import com.hampcode.articlesapp.service.SupplierService;

@Controller
@RequestMapping("/purchaseorders")
public class PurchaseOrderController {
	
	protected static final String PURCHASEORDER_VIEW = "purchaseorders/showPurchaseOrder"; // view template for single article
	protected static final String PURCHASEORDER_ADD_FORM_VIEW = "purchaseorders/newPurchaseOrder"; // form for new article
	protected static final String PURCHASEORDER_EDIT_FORM_VIEW = "purchaseorders/editPurchaseOrder"; // form for editing an article
	protected static final String PURCHASEORDER_PAGE_VIEW = "purchaseorders/allPurchaseOrders"; // list with pagination
	
	protected static final String PURCHASEORDER_QUERYS= "purchaseorders/querysPurchaseOrders"; // list with pagination

	
	protected static final String INDEX_VIEW = "index"; // articles with pagination
	
	@Autowired
	private PageInitPaginationPurchaseOrder pageInitPagination;
	
	@Autowired
	private PurchaseOrderService purchaseorderService;
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private ProductService productService;

	@GetMapping("/{id}")
	public String getPurchaseOrderById(@PathVariable(value = "id") Long purchaseorderId, Model model) {
		model.addAttribute("purchaseorder", purchaseorderService.findById(purchaseorderId));
		return PURCHASEORDER_VIEW;
	}
	
	@GetMapping
	public ModelAndView getAllPurchaseOrders(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) {
		ModelAndView modelAndView = pageInitPagination.initPagination(pageSize, page, PURCHASEORDER_PAGE_VIEW);
		return modelAndView;
	}
	
	@GetMapping("/new")
	public String newPurchaseOrder(Model model) {

		// in case of redirection model will contain article
		if (!model.containsAttribute("purchaseorder")) {
			model.addAttribute("purchaseorder", new PurchaseOrder());
			 List<Supplier> suppliers = supplierService.getAllSupplier();
		     model.addAttribute("suppliers", suppliers);
		     List<Product> products = productService.getAllProducts();
		     model.addAttribute("products", products);
		}
		return PURCHASEORDER_ADD_FORM_VIEW;
	}

	@PostMapping("/create")
	public String createPurchaseOrder(@Valid PurchaseOrder purchaseorder, BindingResult result, Model model, RedirectAttributes attr) {

		if (result.hasErrors()/* || purchaseorderService.valid(purchaseorder) == false*/) {

		
			attr.addFlashAttribute("org.springframework.validation.BindingResult.purchaseorder", result);
			attr.addFlashAttribute("purchaseorder", purchaseorder);
			return "redirect:/purchaseorders/new";
		}
		PurchaseOrder newPurchaseOrder = purchaseorderService.createPurchaseOrder(purchaseorder);
		model.addAttribute("purchaseorder", newPurchaseOrder);

		return "redirect:/purchaseorders/" + newPurchaseOrder.getId();//verificar despues
	}
	
	@GetMapping("{id}/edit")
	public String editPurchaseOrder(@PathVariable(value = "id") Long purchaseorderId, Model model) {
		/*
		 * in case of redirection from '/article/{id}/update' model will contain purchaseorder
		 * with field values
		 */
		if (!model.containsAttribute("purchaseorder")) {
			   List<Supplier> suppliers = supplierService.getAllSupplier();
		        model.addAttribute("suppliers", suppliers);
		        List<Product> products = productService.getAllProducts();
			     model.addAttribute("products", products);
			     
			model.addAttribute("purchaseorder", purchaseorderService.findById(purchaseorderId));
			
		}
		return PURCHASEORDER_EDIT_FORM_VIEW;
	}
	
	@PostMapping(path = "/{id}/update")//revisar
	public String updatePurchaseOrder(@PathVariable(value = "id") Long purchaseorderId, @Valid PurchaseOrder purchaseorderDetails,
			BindingResult result, Model model, RedirectAttributes attr) {

		if (result.hasErrors()/* || purchaseorderService.valid(purchaseorderDetails) == false*/) {//revisar

			/// After the redirect: flash attributes pass attributes to the model
			attr.addFlashAttribute("org.springframework.validation.BindingResult.purchaseorder", result);
			attr.addFlashAttribute("purchaseorder", purchaseorderDetails);

			attr.addFlashAttribute("error", "No se permite esta Orden de Compra");

			return "redirect:/purchaseorders/" + purchaseorderDetails.getId() + "/edit";
		}

		purchaseorderService.updatePurchaseOrder(purchaseorderId, purchaseorderDetails);
		model.addAttribute("purchaseorder", purchaseorderService.findById(purchaseorderId));
		return "redirect:/purchaseorders/" + purchaseorderId;
	}
	
	@GetMapping(value = "/{id}/delete")
	public String deletePurchaseOrder(@PathVariable("id") Long purchaseorderId) {
		purchaseorderService.deletePurchaseOrder(purchaseorderId);
		return "redirect:/purchaseorders";
	}
	
	@GetMapping("/querysPurchaseOrders")
	public ModelAndView listAllPurchaseOrder(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) {
		ModelAndView modelAndView = pageInitPagination.initPagination(pageSize, page, PURCHASEORDER_QUERYS);
		return modelAndView;
	}
	
	
	
	@GetMapping("/search")
	public ModelAndView searchByProduct(@RequestParam("product") String product,
							    @RequestParam("pageSize") Optional<Integer> pageSize,
							    @RequestParam("page")Optional<Integer> page) throws Exception{
		
		ModelAndView modelAndView ;
		
		if(!product.isEmpty())
		{
			if(!this.pageInitPagination.initPaginationSearch(pageSize,page, PURCHASEORDER_PAGE_VIEW, product).isEmpty())
			{
				modelAndView=this.pageInitPagination.initPaginationSearch(pageSize, page, PURCHASEORDER_QUERYS, product);
			}else
			{
				modelAndView=this.pageInitPagination.initPagination(pageSize, page, PURCHASEORDER_QUERYS);

			}
		}
		else
		{
			modelAndView=this.pageInitPagination.initPagination(pageSize, page, PURCHASEORDER_QUERYS);

		}

		
		return modelAndView;
	}
	

	
}
