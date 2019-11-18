package com.hampcode.articlesapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hampcode.articlesapp.model.Product;

public interface ProductService {

	List<Product> getAllProducts();
	
	Product createProduct(Product product);
	
	Product updateProduct(Long id, Product product);
	
	void deleteProduct(Long productId);
	
	Product findById(Long id);
	
	
	Product getLatestEntry();
	
	
    Page<Product> findAll(Pageable pageable);

    Page<Product> finByName(String name,Pageable pageable);
}
