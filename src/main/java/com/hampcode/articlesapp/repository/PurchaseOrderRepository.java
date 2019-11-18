package com.hampcode.articlesapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hampcode.articlesapp.model.PurchaseOrder;


@Repository
public interface PurchaseOrderRepository extends PagingAndSortingRepository<PurchaseOrder, Long> {
	
	/**
     * @return newest articleId
     */
	@Query(value = "SELECT MAX(id) FROM PurchaseOrder")
    Long findTopByOrderByIdDesc();
	
	 /**
     * @param title     title of an article
     * @param author    author of an article
     * @return          List of articles with the same title and author
     */
    //title+author must be unique
	@Query("SELECT i FROM PurchaseOrder i WHERE i.detail=:detail")
	List<PurchaseOrder> findByDetail(@Param("detail") String detail);

	/**
     * @param pageable
     * @return          a page of entities that fulfill the restrictions
     *                  specified by the Pageable object
     */
	Page<PurchaseOrder> findAll(Pageable pageable);

	@Query("SELECT i FROM PurchaseOrder i WHERE i.responsible like %?1%")
	Page<PurchaseOrder> findByResponsible(String responsible,Pageable pageable);

}

