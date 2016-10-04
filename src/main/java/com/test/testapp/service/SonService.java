package com.test.testapp.service;

import com.test.testapp.domain.Son;

import java.util.List;

/**
 * Service Interface for managing Son.
 */
public interface SonService {

    /**
     * Save a son.
     *
     * @param son the entity to save
     * @return the persisted entity
     */
    Son save(Son son);

    /**
     *  Get all the sons.
     *  
     *  @return the list of entities
     */
    List<Son> findAll();

    /**
     *  Get the "id" son.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Son findOne(Long id);

    /**
     *  Delete the "id" son.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
