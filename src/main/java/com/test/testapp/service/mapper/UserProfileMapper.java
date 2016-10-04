package com.test.testapp.service.mapper;


import com.test.testapp.domain.User;
import com.test.testapp.domain.UserProfile;
import com.test.testapp.service.dto.UserProfileDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity UserProfile and its DTO UserProfileDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface UserProfileMapper {

    @Mapping(source = "user.id", target = "userId")
    UserProfileDTO userProfileToUserProfileDTO(UserProfile userProfile);

    List<UserProfileDTO> userProfilesToUserProfileDTOs(List<UserProfile> userProfiles);

    @Mapping(source = "userId", target = "user")
    UserProfile userProfileDTOToUserProfile(UserProfileDTO userProfileDTO);

    List<UserProfile> userProfileDTOsToUserProfiles(List<UserProfileDTO> userProfileDTOs);

//    default User UserFromId(Long id) {
//        if (id == null) {
//            return null;
//        }
//        User User = new User();
//        User.setId(id);
//        return User;
//    }
}
