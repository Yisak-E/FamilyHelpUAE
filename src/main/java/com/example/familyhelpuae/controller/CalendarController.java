package com.example.familyhelpuae.controller;

import com.example.familyhelpuae.dto.CalendarEventDto;
import com.example.familyhelpuae.model.User;
import com.example.familyhelpuae.repository.UserRepository;
import com.example.familyhelpuae.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/calendar")
@RequiredArgsConstructor
public class CalendarController {
    private final CalendarService calendarService;
    private final UserRepository userRepository;

    @GetMapping("/weekly")
    public ResponseEntity<List<CalendarEventDto>> getMyCalendar(Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        return ResponseEntity.ok(calendarService.getWeeklyCalendar(user.getFamily().getId()));
    }
}