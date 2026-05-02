package com.example.familyhelpuae.controller;


import com.example.familyhelpuae.service.FamilyService;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/families")
public class FamilyController {
    FamilyService familyService ;

    public FamilyController(FamilyService familyService){
        this.familyService=familyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfile(@PathVariable Long id) {
        return ResponseEntity.ok(familyService.getMyFamilyProfile(id));
    }
}
