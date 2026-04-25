package com.example.familyhelpuae.service;


import com.example.familyhelpuae.model.HelpOffer;
import com.example.familyhelpuae.model.HelpRequest;
import com.example.familyhelpuae.repository.HelpOfferRepository;
import com.example.familyhelpuae.repository.HelpRequestRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class HelpService{
    private final HelpRequestRepository helpRequestRepo;
    private final HelpOfferRepository helpOfferRepo;

    public HelpService(HelpRequestRepository helpRequestRepository, HelpOfferRepository helpOfferRepository) {
        this.helpRequestRepo = helpRequestRepository;
        this.helpOfferRepo = helpOfferRepository;
    }

    public List<HelpRequest> findAll(){
        return helpRequestRepo.findAll();
    }

    public List<HelpOffer> findAllByHelpRequestId(Long helpRequestId){
        return helpOfferRepo.findAllById(Collections.singleton(helpRequestId));
    }


    public HelpOffer offerHelp(@Valid HelpOffer helpOffer) {
        return helpOfferRepo.save(helpOffer);
    }

    public HelpRequest requestHelp(@Valid HelpRequest helpRequest) {
        return helpRequestRepo.save(helpRequest);
    }
}