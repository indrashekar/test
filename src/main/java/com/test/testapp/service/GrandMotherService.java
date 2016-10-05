package com.test.testapp.service;

import com.test.testapp.domain.GrandMother;

import java.util.List;

/**
 * Service Interface for managing GrandMother.
 */
public interface GrandMotherService {

    /**
     * Save a grandMother.
     *
     * @param grandMother the entity to save
     * @return the persisted entity
     */
    GrandMother save(GrandMother grandMother);

    /**
     *  Get all the grandMothers.
     *  
     *  @return the list of entities
     */
    List<GrandMother> findAll();

    /**
     *  Get the "id" grandMother.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    GrandMother findOne(Long id);

    /**
     *  Delete the "id" grandMother.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
