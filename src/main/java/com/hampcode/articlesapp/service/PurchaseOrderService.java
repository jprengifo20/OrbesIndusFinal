package com.hampcode.articlesapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hampcode.articlesapp.model.Incident;
import com.hampcode.articlesapp.model.PurchaseOrder;

public interface PurchaseOrderService  {
	
	List<PurchaseOrder> getAllPurchaseOrders();

	PurchaseOrder createPurchaseOrder(PurchaseOrder PurchaseOrder);

	PurchaseOrder updatePurchaseOrder(Long id, PurchaseOrder PurchaseOrder);

	void deletePurchaseOrder(Long PurchaseOrderId);

	PurchaseOrder findById(Long id);
	
	
	PurchaseOrder getLatestEntry();

	
	boolean valid(PurchaseOrder purchaseorder);
	
    Page<PurchaseOrder> findAll(Pageable pageable);

    Page<PurchaseOrder> findByResponsible(String responsible,Pageable pageable);
    


}
