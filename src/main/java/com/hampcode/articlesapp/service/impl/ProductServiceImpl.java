package com.hampcode.articlesapp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hampcode.articlesapp.exception.ResourceNotFoundException;
import com.hampcode.articlesapp.model.Product;
import com.hampcode.articlesapp.repository.ProductRepository;
import com.hampcode.articlesapp.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	
	@Autowired
	private ProductRepository productRepository;
	
	
	@Override
	public List<Product> getAllProducts() {
		List<Product> products = new ArrayList<>();
		productRepository.findAll().iterator().forEachRemaining(products::add);
		return products;
	}

	@Override
	public Product createProduct(Product product) {
		Product newProduct;
		newProduct = productRepository.save(product);
		return newProduct;
	}

	@Override
	public Product updateProduct(Long id, Product productDetails) {
		Product product = findById(id);

		product.setName(productDetails.getName());
		product.setSuppliers(productDetails.getSuppliers());
		product.setUnit_price(productDetails.getUnit_price());
		product.setUnit_stock(productDetails.getUnit_stock());
		product.setDescription(productDetails.getDescription());
		
				productRepository.save(product);
				return product;
	}

	@Override
	public void deleteProduct(Long productId) {
		productRepository.delete(findById(productId));
		
	}

	@Override
	public Product findById(Long id) {
		Optional<Product> product = productRepository.findById(id);

		if (!product.isPresent()) {
			throw new ResourceNotFoundException("There is no Product with ID = " + id);
		}
		return product.get();
	}

	@Override
	public Product getLatestEntry() {
		List<Product> products = getAllProducts();
		if (products.isEmpty()) {
			return null;
		} else {
			Long latestProductID = productRepository.findTopByOrderByIdDesc();
			return findById(latestProductID);
		}
	}



	@Override
	public Page<Product> findAll(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	@Override
	public Page<Product> finByName(String name, Pageable pageable) {
		return productRepository.finByName(name, pageable);
	}

	
}
