package com.hampcode.articlesapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hampcode.articlesapp.model.Request;;

@Repository
public interface RequestRepository extends PagingAndSortingRepository<Request, Long> {
	
	/**
     * @return newest articleId
     */
    @Query(value = "SELECT MAX(id) FROM Request")
    Long findTopByOrderByIdDesc();

    /**
     * @param title     title of an article
     * @param author    author of an article
     * @return          List of articles with the same title and author
     */
    //title+author must be unique
    @Query("SELECT a FROM Article a WHERE a.title=:title and a.author=:author")
    List<Request> findByTitleAndAuthor(@Param("title") String title, @Param("author") String author);


	/**
     * @param pageable
     * @return          a page of entities that fulfill the restrictions
     *                  specified by the Pageable object
     */
    Page<Request> findAll(Pageable pageable);
    
    @Query("SELECT a FROM Request a WHERE a.area like %?1%")
    Page<Request> finByArea(String area,Pageable pageable);

    
    /*    @Query("SELECT a FROM Request a WHERE a.area like %?1%")
*/
	
}


