package com.example.familyhelpuae.controller;

import com.example.familyhelpuae.service.HelpService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String offerHelp(){
        helpService.findAll();
        return "this is a help offer call";
    }
    @PostMapping("/api/help/request")
    public String requestHelp(){
        return "this is a help request call";
    }

    @GetMapping("/api/help/search")
    public String searchHelp(){
        return "this is search help call";
    }

    @GetMapping("/api/help/my-activity")
    public String myActivityHelp(){
        return "this is my-activity call";
    }
}
