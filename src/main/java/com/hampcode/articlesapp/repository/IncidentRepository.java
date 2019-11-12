package com.hampcode.articlesapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hampcode.articlesapp.model.Article;
import com.hampcode.articlesapp.model.Incident;
import com.hampcode.articlesapp.model.Request;


@Repository
public interface IncidentRepository extends PagingAndSortingRepository<Incident, Long> {
	
	/**
     * @return newest articleId
     */
	@Query(value = "SELECT MAX(id) FROM Incident")
    Long findTopByOrderByIdDesc();
	
	 /**
     * @param title     title of an article
     * @param author    author of an article
     * @return          List of articles with the same title and author
     */
    //title+author must be unique
	@Query("SELECT i FROM Incident i WHERE i.observations=:observations")
	List<Incident> findByObservations(@Param("observations") String observations);

	/**
     * @param pageable
     * @return          a page of entities that fulfill the restrictions
     *                  specified by the Pageable object
     */
	Page<Incident> findAll(Pageable pageable);


}

