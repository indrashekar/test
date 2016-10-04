package com.test.testapp.service.impl;

import com.test.testapp.service.UserProfileService;
import com.test.testapp.domain.UserProfile;
import com.test.testapp.repository.UserProfileRepository;
import com.test.testapp.service.dto.UserProfileDTO;
import com.test.testapp.service.mapper.UserProfileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing UserProfile.
 */
@Service
@Transactional
public class UserProfileServiceImpl implements UserProfileService{

    private final Logger log = LoggerFactory.getLogger(UserProfileServiceImpl.class);
    
    @Inject
    private UserProfileRepository userProfileRepository;

    @Inject
    private UserProfileMapper userProfileMapper;

    /**
     * Save a userProfile.
     *
     * @param userProfileDTO the entity to save
     * @return the persisted entity
     */
    public UserProfileDTO save(UserProfileDTO userProfileDTO) {
        log.debug("Request to save UserProfile : {}", userProfileDTO);
        UserProfile userProfile = userProfileMapper.userProfileDTOToUserProfile(userProfileDTO);
        userProfile = userProfileRepository.save(userProfile);
        UserProfileDTO result = userProfileMapper.userProfileToUserProfileDTO(userProfile);
        return result;
    }

    /**
     *  Get all the userProfiles.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<UserProfileDTO> findAll() {
        log.debug("Request to get all UserProfiles");
        List<UserProfileDTO> result = userProfileRepository.findAll().stream()
            .map(userProfileMapper::userProfileToUserProfileDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one userProfile by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public UserProfileDTO findOne(Long id) {
        log.debug("Request to get UserProfile : {}", id);
        UserProfile userProfile = userProfileRepository.findOne(id);
        UserProfileDTO userProfileDTO = userProfileMapper.userProfileToUserProfileDTO(userProfile);
        return userProfileDTO;
    }

    /**
     *  Delete the  userProfile by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserProfile : {}", id);
        userProfileRepository.delete(id);
    }
}
