package com.example.familyhelpuae.controller;

import com.example.familyhelpuae.service.HelpService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelpController {

    private final HelpService helpService;

    public HelpController(HelpService helpService) {
        /**
         * POST /api/help/offer: Create a help offer (e.g., "Offering childcare on Friday").
         * POST /api/help/request: Create a help request (e.g., "Need help with grocery shopping").
         * GET /api/help/search: Search for available offers/requests in a specific city (e.g., Abu Dhabi).
         * GET /api/help/my-activity: List all posts created by logged-in user
         */
        this.helpService = helpService;
    }


    @PostMapping("/api/help/offer")
    public void offerHelp(){

    }
    @PostMapping("/api/help/request")
    public void requestHelp(){
    }

    @GetMapping("/api/help/search")
    public void searchHelp(){
    }

    @GetMapping("/api/help/my-activity")
    public void myActivityHelp(){
    }
}
