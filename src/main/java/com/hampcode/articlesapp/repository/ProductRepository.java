package com.hampcode.articlesapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hampcode.articlesapp.model.Product;


@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
	
	/**
     * @return newest articleId
     */
	@Query(value = "SELECT MAX(id) FROM Product")
    Long findTopByOrderByIdDesc();
	
	 /**
     * @param title     title of an article
     * @param author    author of an article
     * @return          List of articles with the same title and author
     */
    //title+author must be unique

	@Query("SELECT i FROM Article i WHERE i.title=:title")
	List<Product> findByTutle(@Param("title") String observations);

	/**
     * @param pageable
     * @return          a page of entities that fulfill the restrictions
     *                  specified by the Pageable object
     */
	Page<Product> findAll(Pageable pageable);

	@Query("SELECT i FROM Product i WHERE i.name like %?1%")
	Page<Product> finByName(String gravedad,Pageable pageable);
	

}

