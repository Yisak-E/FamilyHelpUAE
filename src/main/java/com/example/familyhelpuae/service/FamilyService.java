package com.example.familyhelpuae.service;

import com.example.familyhelpuae.dto.ProfileDto;
import com.example.familyhelpuae.model.Family;
import com.example.familyhelpuae.model.User;
import com.example.familyhelpuae.repository.FamilyRepository;
import com.example.familyhelpuae.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class FamilyService {
    FamilyRepository familyRepository;
    UserRepository userRepository;

    public FamilyService( FamilyRepository familyRepository,  UserRepository userRepository){
        this.familyRepository = familyRepository;
        this.userRepository = userRepository;
    }

    public ProfileDto getMyFamilyProfile(Long familyId){
        Family family = familyRepository.findById(familyId).orElse(null);

        if(family == null){
            return null;
        }
        User user = userRepository.findByEmail(family.getEmail()).orElse(null);
        if(user == null){
            return null;
        }
        return ProfileDto.builder()
                .familyName(family.getFamilyName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .address(family.getAddress())
                .build();
    }

}

