package com.hampcode.articlesapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hampcode.articlesapp.model.Request;
import com.hampcode.articlesapp.model.Supplier;

@Repository
public interface SupplierRepository extends PagingAndSortingRepository<Supplier, Long> {

	/**
     * @return newest articleId
     */
    @Query(value = "SELECT MAX(id) FROM Supplier")
    Long findTopByOrderByIdDesc();

    /**
     * @param title     title of an article
     * @param author    author of an article
     * @return          List of articles with the same title and author
     */
    //title+author must be unique
    @Query("SELECT a FROM Article a WHERE a.title=:title and a.author=:author")
    List<Supplier> findByTitleAndAuthor(@Param("title") String title, @Param("author") String author);


	/**
     * @param pageable
     * @return          a page of entities that fulfill the restrictions
     *                  specified by the Pageable object
     */
    Page<Supplier> findAll(Pageable pageable);
    
    
    @Query("SELECT a FROM Supplier a WHERE a.enterprise like %?1%")
    Page<Supplier> finByEnterprise(String enterprise,Pageable pageable);
}
