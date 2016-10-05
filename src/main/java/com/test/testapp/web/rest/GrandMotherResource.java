package com.test.testapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.test.testapp.domain.GrandMother;
import com.test.testapp.service.GrandMotherService;
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
 * REST controller for managing GrandMother.
 */
@RestController
@RequestMapping("/api")
public class GrandMotherResource {

    private final Logger log = LoggerFactory.getLogger(GrandMotherResource.class);
        
    @Inject
    private GrandMotherService grandMotherService;

    /**
     * POST  /grand-mothers : Create a new grandMother.
     *
     * @param grandMother the grandMother to create
     * @return the ResponseEntity with status 201 (Created) and with body the new grandMother, or with status 400 (Bad Request) if the grandMother has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/grand-mothers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GrandMother> createGrandMother(@RequestBody GrandMother grandMother) throws URISyntaxException {
        log.debug("REST request to save GrandMother : {}", grandMother);
        if (grandMother.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("grandMother", "idexists", "A new grandMother cannot already have an ID")).body(null);
        }
        GrandMother result = grandMotherService.save(grandMother);
        return ResponseEntity.created(new URI("/api/grand-mothers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("grandMother", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /grand-mothers : Updates an existing grandMother.
     *
     * @param grandMother the grandMother to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated grandMother,
     * or with status 400 (Bad Request) if the grandMother is not valid,
     * or with status 500 (Internal Server Error) if the grandMother couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/grand-mothers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GrandMother> updateGrandMother(@RequestBody GrandMother grandMother) throws URISyntaxException {
        log.debug("REST request to update GrandMother : {}", grandMother);
        if (grandMother.getId() == null) {
            return createGrandMother(grandMother);
        }
        GrandMother result = grandMotherService.save(grandMother);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("grandMother", grandMother.getId().toString()))
            .body(result);
    }

    /**
     * GET  /grand-mothers : get all the grandMothers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of grandMothers in body
     */
    @RequestMapping(value = "/grand-mothers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<GrandMother> getAllGrandMothers() {
        log.debug("REST request to get all GrandMothers");
        return grandMotherService.findAll();
    }

    /**
     * GET  /grand-mothers/:id : get the "id" grandMother.
     *
     * @param id the id of the grandMother to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the grandMother, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/grand-mothers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GrandMother> getGrandMother(@PathVariable Long id) {
        log.debug("REST request to get GrandMother : {}", id);
        GrandMother grandMother = grandMotherService.findOne(id);
        return Optional.ofNullable(grandMother)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /grand-mothers/:id : delete the "id" grandMother.
     *
     * @param id the id of the grandMother to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/grand-mothers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGrandMother(@PathVariable Long id) {
        log.debug("REST request to delete GrandMother : {}", id);
        grandMotherService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("grandMother", id.toString())).build();
    }

}
