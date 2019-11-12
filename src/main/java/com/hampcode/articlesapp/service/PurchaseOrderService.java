package com.hampcode.articlesapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hampcode.articlesapp.model.PurchaseOrder;

public interface PurchaseOrderService  {
	
	List<PurchaseOrder> getAllPurchaseOrder();

	PurchaseOrder createPurchaseOrder(PurchaseOrder PurchaseOrder);

	PurchaseOrder updatePurchaseOrder(Long id, PurchaseOrder PurchaseOrder);

	void deletePurchaseOrder(Long PurchaseOrderId);

	PurchaseOrder findById(Long id);
	
	/**
     * @return newest article
     */
	PurchaseOrder getLatestEntry();

    /**
     * tests whether there is an article with te same title and author in the database
     * @param article
     * @return true if there is no article with the same author and title in the database
     */
	

	//Pagination
    Page<PurchaseOrder> findAll(Pageable pageable);

}
