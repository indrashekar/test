package com.test.testapp.service.impl;

import com.test.testapp.service.GrandMotherService;
import com.test.testapp.domain.GrandMother;
import com.test.testapp.repository.GrandMotherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing GrandMother.
 */
@Service
@Transactional
public class GrandMotherServiceImpl implements GrandMotherService{

    private final Logger log = LoggerFactory.getLogger(GrandMotherServiceImpl.class);
    
    @Inject
    private GrandMotherRepository grandMotherRepository;

    /**
     * Save a grandMother.
     *
     * @param grandMother the entity to save
     * @return the persisted entity
     */
    public GrandMother save(GrandMother grandMother) {
        log.debug("Request to save GrandMother : {}", grandMother);
        GrandMother result = grandMotherRepository.save(grandMother);
        return result;
    }

    /**
     *  Get all the grandMothers.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<GrandMother> findAll() {
        log.debug("Request to get all GrandMothers");
        List<GrandMother> result = grandMotherRepository.findAll();

        return result;
    }

    /**
     *  Get one grandMother by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public GrandMother findOne(Long id) {
        log.debug("Request to get GrandMother : {}", id);
        GrandMother grandMother = grandMotherRepository.findOne(id);
        return grandMother;
    }

    /**
     *  Delete the  grandMother by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GrandMother : {}", id);
        grandMotherRepository.delete(id);
    }
}
