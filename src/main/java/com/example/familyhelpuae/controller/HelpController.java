package com.example.familyhelpuae.controller;

import com.example.familyhelpuae.dto.CreateOfferDto;
import com.example.familyhelpuae.dto.MyActivityResponse;
import com.example.familyhelpuae.model.HelpOffer;
import com.example.familyhelpuae.model.HelpRequest;
import com.example.familyhelpuae.service.HelpService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class HelpController {
    private HelpService helpService;

    public HelpController( HelpService helpService) {
        this.helpService =  helpService;
        /**
         * POST /api/help/offer: Create a help offer (e.g., "Offering childcare on Friday").
         * POST /api/help/request: Create a help request (e.g., "Need help with grocery shopping").
         * GET /api/help/search: Search for available offers/requests in a specific city (e.g., Abu Dhabi).
         * GET /api/help/my-activity: List all posts created by logged-in user
         */
    }


    @PostMapping("/api/help/offer")
    public ResponseEntity<HelpOffer> createOffer(@Valid @RequestBody CreateOfferDto helpOffer, Principal principal) {
        return new ResponseEntity<>(helpService.createOffer(helpOffer, principal.getName()), HttpStatus.CREATED);
    }
    @PostMapping("/api/help/request")
    public ResponseEntity<HelpRequest> requestHelp(@Valid @RequestBody HelpRequest helpRequest) {
        return new  ResponseEntity<>(helpService.requestHelp(helpRequest), HttpStatus.CREATED);
    }

    @GetMapping("/api/help/search")
    public ResponseEntity<List<HelpRequest>> searchHelp(){
        return  new ResponseEntity<>(helpService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/api/help/my-activity")
    public ResponseEntity<List<MyActivityResponse>> getMyActivity(Principal principal){
        return new ResponseEntity<>(helpService.getMyActivity(principal.getName()), HttpStatus.OK);
    }
}
