package com.example.familyhelpuae.service;


import com.example.familyhelpuae.dto.CreateOfferDto;
import com.example.familyhelpuae.dto.CreateRequestDto;
import com.example.familyhelpuae.dto.MyActivityResponse;
import com.example.familyhelpuae.model.Family;
import com.example.familyhelpuae.model.HelpOffer;
import com.example.familyhelpuae.model.HelpRequest;
import com.example.familyhelpuae.model.SupportTask;
import com.example.familyhelpuae.model.User;
import com.example.familyhelpuae.repository.HelpOfferRepository;
import com.example.familyhelpuae.repository.HelpRequestRepository;
import com.example.familyhelpuae.repository.SupportTaskRepository;
import com.example.familyhelpuae.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HelpService{
    private final HelpRequestRepository helpRequestRepo;
    private final HelpOfferRepository helpOfferRepo;
    private final UserRepository userRepo;
    private final SupportTaskRepository supportTaskRepo;

    public HelpService(HelpRequestRepository helpRequestRepository, HelpOfferRepository helpOfferRepository, UserRepository userRepo, SupportTaskRepository supportTaskRepository) {
        this.helpRequestRepo = helpRequestRepository;
        this.helpOfferRepo = helpOfferRepository;
        this.userRepo = userRepo;
        this.supportTaskRepo = supportTaskRepository;
    }

    public List<HelpRequest> findAll(){
        return helpRequestRepo.findAll();
    }

    public List<HelpOffer> findAllByHelpRequestId(Long helpRequestId){
        // find all support tasks connected to this help request and extract their offers
        List<SupportTask> tasks = supportTaskRepo.findByRequest_Id(helpRequestId);
        return tasks.stream()
                .map(SupportTask::getOffer)
                .collect(Collectors.toList());
    }


    public HelpOffer createOffer(@Valid CreateOfferDto createOfferDto, String email) {

        HelpOffer newOffer = new HelpOffer();
        boolean exists = helpOfferRepo.existsByProviderFamily_FamilyNameAndServiceCategoryAndStatus(
                createOfferDto.getFamilyName(),
                createOfferDto.getServiceCategory(),
                createOfferDto.getStatus()
        );

        if (exists) {
            throw new IllegalStateException("You already have an active offer for this category!");
        }
                User user =userRepo.findByEmail(email).orElseThrow(()->new IllegalStateException("User not found!"));
                Family family = user.getFamily();

        newOffer.setDescription(createOfferDto.getDescription());
        newOffer.setServiceCategory(createOfferDto.getServiceCategory());
        newOffer.setStatus(createOfferDto.getStatus());
        newOffer.setProviderFamily(family);
         helpOfferRepo.save(newOffer);
         return  newOffer;
    }

    public HelpRequest requestHelp(@Valid CreateRequestDto createRequestDto, String email) {
        HelpRequest newRequest = new HelpRequest();

        boolean exists = helpRequestRepo.existsByRequesterFamily_FamilyNameAndCategoryAndStatus(
                createRequestDto.getFamilyName(),
                createRequestDto.getCategory(),
                createRequestDto.getStatus()
        );

        if(exists){
            throw new IllegalStateException("You already have an active request for this category!");
        }

        User user =userRepo.findByEmail(email).orElseThrow(()->new IllegalStateException("User not found!"));
        Family family = user.getFamily();

        newRequest.setCategory(createRequestDto.getCategory());
        newRequest.setStatus(createRequestDto.getStatus());
        newRequest.setRequesterFamily(family);
        newRequest.setUrgent(createRequestDto.getUrgent());
        helpRequestRepo.save(newRequest);

        return newRequest;
    }

    public List<MyActivityResponse> getMyActivity(String email) {
        List<MyActivityResponse> myActivities = new ArrayList<>();

        User user = userRepo.findByEmail(email).orElseThrow(()->new IllegalStateException("User not found!"));

        Family family = user.getFamily();
        // get my activity by family id for help offer
        List<HelpOffer> offers = helpOfferRepo.findByProviderFamily(family);
        for (HelpOffer offer : offers) {
            myActivities.add(MyActivityResponse.builder()
                            .type("OFFER")
                            .id(offer.getId())
                            .category(offer.getServiceCategory())
                            .status(offer.getStatus())
                            .description(offer.getDescription())
                            .familyName(family.getFamilyName())
                    .build());
        }

        // get my activity by family id for help request

        List<HelpRequest> requests = helpRequestRepo.findByRequesterFamily(family);

        for(HelpRequest request : requests){
            myActivities.add(MyActivityResponse.builder()
                            .type("REQUEST")
                            .id(request.getId())
                            .category(request.getCategory())
                            .status(request.getStatus())
                            .familyName(family.getFamilyName())
                            .description(request.getDetails())
                    .build());
        }


        return myActivities;

    }
}