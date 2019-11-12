package com.hampcode.articlesapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hampcode.articlesapp.model.Supplier;

public interface SupplierService {

	List<Supplier> getAllSupplier();

	Supplier createSupplier(Supplier supplier);

	Supplier updateSupplier(Long id, Supplier supplier);

	void deleteSupplier(Long supplierId);

	Supplier findById(Long id);
	
	/**
     * @return newest article
     */
	Supplier getLatestEntry();

    /**
     * tests whether there is an article with te same title and author in the database
     * @param article
     * @return true if there is no article with the same author and title in the database
     */
	

	//Pagination
    Page<Supplier> findAll(Pageable pageable);
}