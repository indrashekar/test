package com.test.testapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.test.testapp.domain.Son;
import com.test.testapp.service.SonService;
import com.test.testapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Son.
 */
@RestController
@RequestMapping("/api")
public class SonResource {

    private final Logger log = LoggerFactory.getLogger(SonResource.class);
        
    @Inject
    private SonService sonService;

    /**
     * POST  /sons : Create a new son.
     *
     * @param son the son to create
     * @return the ResponseEntity with status 201 (Created) and with body the new son, or with status 400 (Bad Request) if the son has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/sons",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Son> createSon(@RequestBody Son son) throws URISyntaxException {
        log.debug("REST request to save Son : {}", son);
        if (son.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("son", "idexists", "A new son cannot already have an ID")).body(null);
        }
        Son result = sonService.save(son);
        return ResponseEntity.created(new URI("/api/sons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("son", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sons : Updates an existing son.
     *
     * @param son the son to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated son,
     * or with status 400 (Bad Request) if the son is not valid,
     * or with status 500 (Internal Server Error) if the son couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/sons",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Son> updateSon(@RequestBody Son son) throws URISyntaxException {
        log.debug("REST request to update Son : {}", son);
        if (son.getId() == null) {
            return createSon(son);
        }
        Son result = sonService.save(son);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("son", son.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sons : get all the sons.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sons in body
     */
    @RequestMapping(value = "/sons",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Son> getAllSons() {
        log.debug("REST request to get all Sons");
        return sonService.findAll();
    }

    /**
     * GET  /sons/:id : get the "id" son.
     *
     * @param id the id of the son to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the son, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/sons/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Son> getSon(@PathVariable Long id) {
        log.debug("REST request to get Son : {}", id);
        Son son = sonService.findOne(id);
        return Optional.ofNullable(son)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sons/:id : delete the "id" son.
     *
     * @param id the id of the son to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/sons/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSon(@PathVariable Long id) {
        log.debug("REST request to delete Son : {}", id);
        sonService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("son", id.toString())).build();
    }

}
