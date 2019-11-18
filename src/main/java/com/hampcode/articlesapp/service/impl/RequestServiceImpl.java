package com.hampcode.articlesapp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hampcode.articlesapp.exception.ResourceNotFoundException;
import com.hampcode.articlesapp.model.Request;
import com.hampcode.articlesapp.repository.RequestRepository;
import com.hampcode.articlesapp.service.RequestService;

@Service
public class RequestServiceImpl implements RequestService {

	@Autowired
	private RequestRepository requestRepository;
	
	@Override
	public List<Request> getAllRequest() {
		List<Request> requests = new ArrayList<>();
		requestRepository.findAll().iterator().forEachRemaining(requests::add);
		return requests;
	}

	@Override
	public Request createRequest(Request request) {
		Request newRequest;
		newRequest = requestRepository.save(request);
		return newRequest;
	}

	@Override
	public Request updateRequest(Long id, Request requestDetails) {
		Request request = findById(id);

		request.setArea(requestDetails.getArea());
		request.setProducts(requestDetails.getProducts());
		request.setQuantity(requestDetails.getQuantity());
		request.setState(requestDetails.getState());
		request.setDate(requestDetails.getDate());
		request.setDate2(requestDetails.getDate2());
		request.setUnit_price(requestDetails.getUnit_price());
		request.setDescription(requestDetails.getDescription());

		requestRepository.save(request);
		return request;
	}

	@Override
	public void deleteRequest(Long requestId) {
		requestRepository.delete(findById(requestId));		
	}

	@Override
	public Request findById(Long id) {
		Optional<Request> request = requestRepository.findById(id);

		if (!request.isPresent()) {
            throw new ResourceNotFoundException("There is no Request with ID = " + id);
        }

		return request.get();
	}

	@Override
	public Request getLatestEntry() {
		  List<Request> requests = getAllRequest();
	        if(requests.isEmpty()){
	            return null;
	        }
	        else{
	            Long latestRequestID = requestRepository.findTopByOrderByIdDesc();
	            return findById(latestRequestID);
	        }
	}

	
	@Override
	public Page<Request> findAll(Pageable pageable) {
		return requestRepository.findAll(pageable);
	}

	@Override
	public Page<Request> finByArea(String area, Pageable pageable) {
		
			return requestRepository.finByArea(area, pageable);
		
		
		
	}

	

	
}
