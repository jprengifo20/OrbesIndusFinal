package com.hampcode.articlesapp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hampcode.articlesapp.exception.ResourceNotFoundException;
import com.hampcode.articlesapp.model.PurchaseOrder;
import com.hampcode.articlesapp.repository.PurchaseOrderRepository;
import com.hampcode.articlesapp.service.PurchaseOrderService;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

	@Autowired
	private PurchaseOrderRepository purchaseorderRepository;
	
	@Override
	public List<PurchaseOrder> getAllPurchaseOrder() {
		List<PurchaseOrder> purchaseorders = new ArrayList<>();
		purchaseorderRepository.findAll().iterator().forEachRemaining(purchaseorders::add);
		return purchaseorders;
	}

	@Override
	public PurchaseOrder createPurchaseOrder(PurchaseOrder purchaseorder) {
		PurchaseOrder newPurchaseOrder;
		newPurchaseOrder = purchaseorderRepository.save(purchaseorder);
		return newPurchaseOrder;
	}

	@Override
	public PurchaseOrder updatePurchaseOrder(Long id, PurchaseOrder purchaseorderDetails) {
		PurchaseOrder purchaseorder = findById(id);
		
		purchaseorder.setDate(purchaseorderDetails.getDate());
		purchaseorder.setDetail(purchaseorderDetails.getDetail());
		purchaseorder.setProduct(purchaseorderDetails.getProduct());
		purchaseorder.setResponsible(purchaseorderDetails.getResponsible());
		/*purchaseorder.setSuppliers(purchaseorderDetails.getSuppliers());*/
		
		purchaseorderRepository.save(purchaseorder);
		return purchaseorder;
	}

	@Override
	public void deletePurchaseOrder(Long purchaseorderId) {
		purchaseorderRepository.delete(findById(purchaseorderId));		
	}

	@Override
	public PurchaseOrder findById(Long id) {
		Optional<PurchaseOrder> purchaseorder = purchaseorderRepository.findById(id);

		if (!purchaseorder.isPresent()) {
            throw new ResourceNotFoundException("There is no PurchaseOrder with ID = " + id);
        }

		return purchaseorder.get();
	}

	@Override
	public PurchaseOrder getLatestEntry() {
		  List<PurchaseOrder> purchaseorders = getAllPurchaseOrder();
	        if(purchaseorders.isEmpty()){
	            return null;
	        }
	        else{
	            Long latestPurchaseOrderID = purchaseorderRepository.findTopByOrderByIdDesc();
	            return findById(latestPurchaseOrderID);
	        }
	}

	
	@Override
	public Page<PurchaseOrder> findAll(Pageable pageable) {
		return purchaseorderRepository.findAll(pageable);
	}

	

	
}
