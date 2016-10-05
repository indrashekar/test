package com.test.testapp.service;

import com.test.testapp.domain.Standard;

import java.util.List;

/**
 * Service Interface for managing Standard.
 */
public interface StandardService {

    /**
     * Save a standard.
     *
     * @param standard the entity to save
     * @return the persisted entity
     */
    Standard save(Standard standard);

    /**
     *  Get all the standards.
     *  
     *  @return the list of entities
     */
    List<Standard> findAll();

    /**
     *  Get the "id" standard.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Standard findOne(Long id);

    /**
     *  Delete the "id" standard.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
