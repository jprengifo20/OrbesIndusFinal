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
	public List<PurchaseOrder> getAllPurchaseOrders() {
		List<PurchaseOrder> purchaseorders = new ArrayList<>();
		purchaseorderRepository.findAll().iterator().forEachRemaining(purchaseorders::add);
		return purchaseorders;
	}

	/*
	 * @Override public PurchaseOrder getOneById(Long id) { // TODO Auto-generated method
	 * stub return purchaseorderRepository.findById(id).orElseThrow(() -> new
	 * RuntimeException("PurchaseOrder not found")); }
	 */

	@Override //
	public PurchaseOrder createPurchaseOrder(PurchaseOrder purchaseorder) {
		PurchaseOrder newPurchaseOrder;
		newPurchaseOrder = purchaseorderRepository.save(purchaseorder);
		return newPurchaseOrder;
	}

	@Override
	public PurchaseOrder updatePurchaseOrder(Long id, PurchaseOrder purchaseorderDetails) {
		// TODO Auto-generated method stub
		PurchaseOrder purchaseorder = findById(id);

		purchaseorder.setSuppliers(purchaseorderDetails.getSuppliers());
		purchaseorder.setDate(purchaseorderDetails.getDate());
		purchaseorder.setProduct(purchaseorderDetails.getProduct());
		purchaseorder.setDetail(purchaseorderDetails.getDetail());
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
	public boolean valid(PurchaseOrder purchaseorder) {
		List<PurchaseOrder> purchaseorders = new ArrayList<>();
		purchaseorderRepository.findByDetail(purchaseorder.getDetail()).iterator().forEachRemaining(purchaseorders::add);
		if (!purchaseorders.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public PurchaseOrder getLatestEntry() {
		List<PurchaseOrder> purchaseorders = getAllPurchaseOrders();
		if (purchaseorders.isEmpty()) {
			return null;
		} else {
			Long latestPurchaseOrderID = purchaseorderRepository.findTopByOrderByIdDesc();
			return findById(latestPurchaseOrderID);
		}
	}

	@Override
	public Page<PurchaseOrder> findAll(Pageable pageable) {
		return purchaseorderRepository.findAll(pageable);
	}
	
	@Override
	public Page<PurchaseOrder> findByProduct(String product, Pageable pageable) {
		return purchaseorderRepository.findByProduct(product, pageable);
	}


	/*@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		purchaseorderRepository.deleteById(id);
	}

	@Override
	public List<PurchaseOrder> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PurchaseOrder create(PurchaseOrder entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PurchaseOrder update(Long id, PurchaseOrder entity) {
		// TODO Auto-generated method stub
		return null;
	}*/

}
