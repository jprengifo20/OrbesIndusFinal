package com.hampcode.articlesapp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hampcode.articlesapp.exception.ResourceNotFoundException;
import com.hampcode.articlesapp.model.Supplier;
import com.hampcode.articlesapp.repository.RequestRepository;
import com.hampcode.articlesapp.repository.SupplierRepository;
import com.hampcode.articlesapp.service.SupplierService;

@Service
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	private SupplierRepository supplierRepository;

	@Override
	public List<Supplier> getAllSupplier() {
		List<Supplier> suppliers = new ArrayList<>();
		supplierRepository.findAll().iterator().forEachRemaining(suppliers::add);
		return suppliers;
	}

	@Override
	public Supplier createSupplier(Supplier supplier) {
		Supplier newSupplier;
		newSupplier = supplierRepository.save(supplier);
		return newSupplier;
	}

	@Override
	public Supplier updateSupplier(Long id, Supplier supplierDetails) {
		Supplier supplier = findById(id);

		supplier.setEnterprise(supplierDetails.getEnterprise());
		supplier.setRuc(supplierDetails.getRuc());
		supplier.setProduct(supplierDetails.getProduct());
		supplier.setPhone(supplierDetails.getPhone());
		
		supplierRepository.save(supplier);
		return supplier;
	}

	@Override
	public void deleteSupplier(Long supplierId) {
supplierRepository.delete(findById(supplierId));		
	}

	@Override
	public Supplier findById(Long id) {
		Optional<Supplier> request = supplierRepository.findById(id);

		if (!request.isPresent()) {
            throw new ResourceNotFoundException("There is no Supplier with ID = " + id);
        }

		return request.get();
	}

	@Override
	public Supplier getLatestEntry() {
		 List<Supplier> suppliers = getAllSupplier();
	        if(suppliers.isEmpty()){
	            return null;
	        }
	        else{
	            Long latestSupplierID = supplierRepository.findTopByOrderByIdDesc();
	            return findById(latestSupplierID);
	        }
	}

	@Override
	public Page<Supplier> findAll(Pageable pageable) {
		return supplierRepository.findAll(pageable);
	}

	@Override
	public Page<Supplier> finByEnterprise(String enterprise, Pageable pageable) {
		return supplierRepository.finByEnterprise(enterprise, pageable);
	}

	
}