package com.hampcode.articlesapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hampcode.articlesapp.model.Request;

public interface RequestService  {
	
	List<Request> getAllRequest();

	Request createRequest(Request request);

	Request updateRequest(Long id, Request request);

	void deleteRequest(Long requestId);

	Request findById(Long id);
	
	/**
     * @return newest article
     */
	Request getLatestEntry();

    /**
     * tests whether there is an article with te same title and author in the database
     * @param article
     * @return true if there is no article with the same author and title in the database
     */
	

	//Pagination
    Page<Request> findAll(Pageable pageable);
    
    Page<Request> finByArea(String area,Pageable pageable);


}
