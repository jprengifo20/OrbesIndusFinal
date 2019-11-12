package com.hampcode.articlesapp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hampcode.articlesapp.exception.ResourceNotFoundException;
import com.hampcode.articlesapp.model.Incident;
import com.hampcode.articlesapp.model.Request;
import com.hampcode.articlesapp.repository.IncidentRepository;
import com.hampcode.articlesapp.service.IncidentService;

@Service
public class IncidentServiceImpl implements IncidentService {

	@Autowired
	private IncidentRepository incidentRepository;

	@Override 
	public List<Incident> getAllIncidents() {
		List<Incident> incidents = new ArrayList<>();
		incidentRepository.findAll().iterator().forEachRemaining(incidents::add);
		return incidents;
	}

	/*
	 * @Override public Incident getOneById(Long id) { // TODO Auto-generated method
	 * stub return incidentRepository.findById(id).orElseThrow(() -> new
	 * RuntimeException("Incident not found")); }
	 */

	@Override //
	public Incident createIncident(Incident incident) {
		Incident newIncident;
		newIncident = incidentRepository.save(incident);
		return newIncident;
	}

	@Override
	public Incident updateIncident(Long id, Incident incidentDetails) {
		// TODO Auto-generated method stub
		Incident incident = findById(id);

		incident.setSuppliers(incidentDetails.getSuppliers());
		incident.setDate(incidentDetails.getDate());
		incident.setGravity(incidentDetails.getGravity());
		incident.setObservations(incidentDetails.getObservations());
		incidentRepository.save(incident);
		return incident;
	}

	@Override
	public void deleteIncident(Long incidentId) {
		incidentRepository.delete(findById(incidentId));
	}

	@Override
	public Incident findById(Long id) {
		Optional<Incident> incident = incidentRepository.findById(id);

		if (!incident.isPresent()) {
			throw new ResourceNotFoundException("There is no Incident with ID = " + id);
		}
		return incident.get();
	}

	@Override
	public boolean valid(Incident incident) {
		List<Incident> incidents = new ArrayList<>();
		incidentRepository.findByObservations(incident.getObservations()).iterator().forEachRemaining(incidents::add);
		if (!incidents.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Incident getLatestEntry() {
		List<Incident> incidents = getAllIncidents();
		if (incidents.isEmpty()) {
			return null;
		} else {
			Long latestIncidentID = incidentRepository.findTopByOrderByIdDesc();
			return findById(latestIncidentID);
		}
	}

	@Override
	public Page<Incident> findAll(Pageable pageable) {
		return incidentRepository.findAll(pageable);
	}
	
	@Override
	public Page<Request> finByGravedad(String gravedad, Pageable pageable) {
		return incidentRepository.finByGravedad(gravedad, pageable);
	}


	/*@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		incidentRepository.deleteById(id);
	}

	@Override
	public List<Incident> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Incident create(Incident entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Incident update(Long id, Incident entity) {
		// TODO Auto-generated method stub
		return null;
	}*/

}
