package com.test.testapp.service;

import com.test.testapp.service.dto.UserProfileDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing UserProfile.
 */
public interface UserProfileService {

    /**
     * Save a userProfile.
     *
     * @param userProfileDTO the entity to save
     * @return the persisted entity
     */
    UserProfileDTO save(UserProfileDTO userProfileDTO);

    /**
     *  Get all the userProfiles.
     *  
     *  @return the list of entities
     */
    List<UserProfileDTO> findAll();

    /**
     *  Get the "id" userProfile.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UserProfileDTO findOne(Long id);

    /**
     *  Delete the "id" userProfile.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
