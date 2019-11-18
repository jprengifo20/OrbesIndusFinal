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

import com.hampcode.articlesapp.common.PageInitPaginationProduct;
import com.hampcode.articlesapp.model.Product;
import com.hampcode.articlesapp.model.Supplier;
import com.hampcode.articlesapp.service.ProductService;
import com.hampcode.articlesapp.service.SupplierService;

@Controller
@RequestMapping("/products")
public class ProductController {
	
	protected static final String PRODUCT_VIEW = "products/showProduct"; // view template for single article
	protected static final String PRODUCT_ADD_FORM_VIEW = "products/newProduct"; // form for new article
	protected static final String PRODUCT_EDIT_FORM_VIEW = "products/editProduct"; // form for editing an article
	protected static final String PRODUCT_PAGE_VIEW = "products/allProducts"; // list with pagination
	
	protected static final String PRODUCT_QUERYS= "products/querysProducts"; // list with pagination

	
	protected static final String INDEX_VIEW = "index"; // articles with pagination
	
	@Autowired
	
	private PageInitPaginationProduct pageInitPagination;
	
	@Autowired
	private ProductService productService;
	@Autowired
	private SupplierService supplierService;

	@GetMapping("/{id}")
	public String getProductById(@PathVariable(value = "id") Long productId, Model model) {
		model.addAttribute("product", productService.findById(productId));
		return PRODUCT_VIEW;
	}
	
	@GetMapping
	public ModelAndView getAllProducts(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) {
		ModelAndView modelAndView = pageInitPagination.initPagination(pageSize, page, PRODUCT_PAGE_VIEW);
		return modelAndView;
	}
	
	@GetMapping("/new")
	public String newProduct(Model model) {

		// in case of redirection model will contain article
		if (!model.containsAttribute("product")) {
			model.addAttribute("product", new Product());
			 List<Supplier> suppliers = supplierService.getAllSupplier();
		     model.addAttribute("suppliers", suppliers);
		}
		return PRODUCT_ADD_FORM_VIEW;
	}

	@PostMapping("/create")
	public String createProduct(@Valid Product product, BindingResult result, Model model, RedirectAttributes attr) {

		if (result.hasErrors()/* || productService.valid(product) == false*/) {

		
			attr.addFlashAttribute("org.springframework.validation.BindingResult.product", result);
			attr.addFlashAttribute("product", product);
			return "redirect:/products/new";
		}
		Product newProduct = productService.createProduct(product);
		model.addAttribute("product", newProduct);

		return "redirect:/products/" + newProduct.getId();//verificar despues
	}
	
	@GetMapping("{id}/edit")
	public String editProduct(@PathVariable(value = "id") Long productId, Model model) {
		/*
		 * in case of redirection from '/article/{id}/update' model will contain product
		 * with field values
		 */
		if (!model.containsAttribute("product")) {
			 List<Supplier> suppliers = supplierService.getAllSupplier();
		        model.addAttribute("suppliers", suppliers);
			model.addAttribute("product", productService.findById(productId));
			
		}
		return PRODUCT_EDIT_FORM_VIEW;
	}
	
	@PostMapping(path = "/{id}/update")//revisar
	public String updateProduct(@PathVariable(value = "id") Long productId, @Valid Product productDetails,
			BindingResult result, Model model, RedirectAttributes attr) {

		if (result.hasErrors()/* || productService.valid(productDetails) == false*/) {//revisar

			/// After the redirect: flash attributes pass attributes to the model
			attr.addFlashAttribute("org.springframework.validation.BindingResult.product", result);
			attr.addFlashAttribute("product", productDetails);

			attr.addFlashAttribute("error", "No se permite esta incidencias");

			return "redirect:/products/" + productDetails.getId() + "/edit";
		}

		productService.updateProduct(productId, productDetails);
		model.addAttribute("product", productService.findById(productId));
		return "redirect:/products/" + productId;
	}
	
	@GetMapping(value = "/{id}/delete")
	public String deleteProduct(@PathVariable("id") Long productId) {
		productService.deleteProduct(productId);
		return "redirect:/products";
	}
	
	@GetMapping("/querysProducts")
	public ModelAndView listAllProduct(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) {
		ModelAndView modelAndView = pageInitPagination.initPagination(pageSize, page, PRODUCT_QUERYS);
		return modelAndView;
	}
	
	
	
	@GetMapping("/search")
	public ModelAndView searchByName(@RequestParam("name") String gravity,
							    @RequestParam("pageSize") Optional<Integer> pageSize,
							    @RequestParam("page")Optional<Integer> page) throws Exception{
		
		ModelAndView modelAndView ;
		
		if(!gravity.isEmpty())
		{
			if(!this.pageInitPagination.initPaginationSearch(pageSize,page, PRODUCT_PAGE_VIEW, gravity).isEmpty())
			{
				modelAndView=this.pageInitPagination.initPaginationSearch(pageSize, page, PRODUCT_QUERYS, gravity);
			}else
			{
				modelAndView=this.pageInitPagination.initPagination(pageSize, page, PRODUCT_QUERYS);

			}
		}
		else
		{
			modelAndView=this.pageInitPagination.initPagination(pageSize, page, PRODUCT_QUERYS);

		}

		
		return modelAndView;
	}
	

	
}
