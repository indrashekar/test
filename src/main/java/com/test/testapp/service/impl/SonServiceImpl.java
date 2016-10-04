package com.test.testapp.service.impl;

import com.test.testapp.service.SonService;
import com.test.testapp.domain.Son;
import com.test.testapp.repository.SonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Son.
 */
@Service
@Transactional
public class SonServiceImpl implements SonService{

    private final Logger log = LoggerFactory.getLogger(SonServiceImpl.class);
    
    @Inject
    private SonRepository sonRepository;

    /**
     * Save a son.
     *
     * @param son the entity to save
     * @return the persisted entity
     */
    public Son save(Son son) {
        log.debug("Request to save Son : {}", son);
        Son result = sonRepository.save(son);
        return result;
    }

    /**
     *  Get all the sons.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Son> findAll() {
        log.debug("Request to get all Sons");
        List<Son> result = sonRepository.findAll();

        return result;
    }

    /**
     *  Get one son by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Son findOne(Long id) {
        log.debug("Request to get Son : {}", id);
        Son son = sonRepository.findOne(id);
        return son;
    }

    /**
     *  Delete the  son by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Son : {}", id);
        sonRepository.delete(id);
    }
}
