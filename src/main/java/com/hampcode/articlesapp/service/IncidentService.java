package com.hampcode.articlesapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hampcode.articlesapp.model.Incident;

public interface IncidentService {

	List<Incident> getAllIncidents();
	Incident createIncident(Incident incident);
	Incident updateIncident(Long id, Incident incident);
	void deleteIncident(Long incidentId);
	Incident findById(Long id);
	
	
	Incident getLatestEntry();
	
	boolean valid(Incident incident);
	
    Page<Incident> findAll(Pageable pageable);

    Page<Incident> finByGravedad(String gravedad,Pageable pageable);
}
