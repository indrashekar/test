package com.test.testapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.test.testapp.service.UserProfileService;
import com.test.testapp.web.rest.util.HeaderUtil;
import com.test.testapp.service.dto.UserProfileDTO;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing UserProfile.
 */
@RestController
@RequestMapping("/api")
public class UserProfileResource {

    private final Logger log = LoggerFactory.getLogger(UserProfileResource.class);
        
    @Inject
    private UserProfileService userProfileService;

    /**
     * POST  /user-profiles : Create a new userProfile.
     *
     * @param userProfileDTO the userProfileDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userProfileDTO, or with status 400 (Bad Request) if the userProfile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/user-profiles",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserProfileDTO> createUserProfile(@RequestBody UserProfileDTO userProfileDTO) throws URISyntaxException {
        log.debug("REST request to save UserProfile : {}", userProfileDTO);
        if (userProfileDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("userProfile", "idexists", "A new userProfile cannot already have an ID")).body(null);
        }
        UserProfileDTO result = userProfileService.save(userProfileDTO);
        return ResponseEntity.created(new URI("/api/user-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("userProfile", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-profiles : Updates an existing userProfile.
     *
     * @param userProfileDTO the userProfileDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userProfileDTO,
     * or with status 400 (Bad Request) if the userProfileDTO is not valid,
     * or with status 500 (Internal Server Error) if the userProfileDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/user-profiles",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserProfileDTO> updateUserProfile(@RequestBody UserProfileDTO userProfileDTO) throws URISyntaxException {
        log.debug("REST request to update UserProfile : {}", userProfileDTO);
        if (userProfileDTO.getId() == null) {
            return createUserProfile(userProfileDTO);
        }
        UserProfileDTO result = userProfileService.save(userProfileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("userProfile", userProfileDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-profiles : get all the userProfiles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userProfiles in body
     */
    @RequestMapping(value = "/user-profiles",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<UserProfileDTO> getAllUserProfiles() {
        log.debug("REST request to get all UserProfiles");
        return userProfileService.findAll();
    }

    /**
     * GET  /user-profiles/:id : get the "id" userProfile.
     *
     * @param id the id of the userProfileDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userProfileDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/user-profiles/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserProfileDTO> getUserProfile(@PathVariable Long id) {
        log.debug("REST request to get UserProfile : {}", id);
        UserProfileDTO userProfileDTO = userProfileService.findOne(id);
        return Optional.ofNullable(userProfileDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /user-profiles/:id : delete the "id" userProfile.
     *
     * @param id the id of the userProfileDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/user-profiles/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUserProfile(@PathVariable Long id) {
        log.debug("REST request to delete UserProfile : {}", id);
        userProfileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("userProfile", id.toString())).build();
    }

}
