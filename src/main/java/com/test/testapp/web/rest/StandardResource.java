package com.test.testapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.test.testapp.domain.Standard;
import com.test.testapp.service.StandardService;
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
 * REST controller for managing Standard.
 */
@RestController
@RequestMapping("/api")
public class StandardResource {

    private final Logger log = LoggerFactory.getLogger(StandardResource.class);
        
    @Inject
    private StandardService standardService;

    /**
     * POST  /standards : Create a new standard.
     *
     * @param standard the standard to create
     * @return the ResponseEntity with status 201 (Created) and with body the new standard, or with status 400 (Bad Request) if the standard has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/standards",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Standard> createStandard(@RequestBody Standard standard) throws URISyntaxException {
        log.debug("REST request to save Standard : {}", standard);
        if (standard.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("standard", "idexists", "A new standard cannot already have an ID")).body(null);
        }
        Standard result = standardService.save(standard);
        return ResponseEntity.created(new URI("/api/standards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("standard", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /standards : Updates an existing standard.
     *
     * @param standard the standard to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated standard,
     * or with status 400 (Bad Request) if the standard is not valid,
     * or with status 500 (Internal Server Error) if the standard couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/standards",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Standard> updateStandard(@RequestBody Standard standard) throws URISyntaxException {
        log.debug("REST request to update Standard : {}", standard);
        if (standard.getId() == null) {
            return createStandard(standard);
        }
        Standard result = standardService.save(standard);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("standard", standard.getId().toString()))
            .body(result);
    }

    /**
     * GET  /standards : get all the standards.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of standards in body
     */
    @RequestMapping(value = "/standards",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Standard> getAllStandards() {
        log.debug("REST request to get all Standards");
        return standardService.findAll();
    }

    /**
     * GET  /standards/:id : get the "id" standard.
     *
     * @param id the id of the standard to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the standard, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/standards/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Standard> getStandard(@PathVariable Long id) {
        log.debug("REST request to get Standard : {}", id);
        Standard standard = standardService.findOne(id);
        return Optional.ofNullable(standard)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /standards/:id : delete the "id" standard.
     *
     * @param id the id of the standard to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/standards/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStandard(@PathVariable Long id) {
        log.debug("REST request to delete Standard : {}", id);
        standardService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("standard", id.toString())).build();
    }

}
