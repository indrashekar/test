package com.test.testapp.service.impl;

import com.test.testapp.service.StandardService;
import com.test.testapp.domain.Standard;
import com.test.testapp.repository.StandardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Standard.
 */
@Service
@Transactional
public class StandardServiceImpl implements StandardService{

    private final Logger log = LoggerFactory.getLogger(StandardServiceImpl.class);
    
    @Inject
    private StandardRepository standardRepository;

    /**
     * Save a standard.
     *
     * @param standard the entity to save
     * @return the persisted entity
     */
    public Standard save(Standard standard) {
        log.debug("Request to save Standard : {}", standard);
        Standard result = standardRepository.save(standard);
        return result;
    }

    /**
     *  Get all the standards.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Standard> findAll() {
        log.debug("Request to get all Standards");
        List<Standard> result = standardRepository.findAll();

        return result;
    }

    /**
     *  Get one standard by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Standard findOne(Long id) {
        log.debug("Request to get Standard : {}", id);
        Standard standard = standardRepository.findOne(id);
        return standard;
    }

    /**
     *  Delete the  standard by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Standard : {}", id);
        standardRepository.delete(id);
    }
}
