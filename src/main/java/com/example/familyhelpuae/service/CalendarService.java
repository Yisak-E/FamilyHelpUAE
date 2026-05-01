package com.example.familyhelpuae.service;

import com.example.familyhelpuae.dto.CalendarEventDto;
import com.example.familyhelpuae.model.PostApplication;
import com.example.familyhelpuae.repository.PostApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final PostApplicationRepository applicationRepository;

    public List<CalendarEventDto> getWeeklyCalendar(Long familyId) {
        // Fetch all accepted applications where the family is either the creator or the helper
        List<PostApplication> apps = applicationRepository.findAllActiveInteractions(familyId);

        return apps.stream().map(app -> {
            boolean isOwner = app.getPost().getFamily().getId().equals(familyId);
            
            return new CalendarEventDto(
                app.getPost().getId(),
                app.getPost().getTitle(),
                app.getPost().getCategory(),
                app.getPost().getNeededBy(), // The date/time for the grid
                app.getPost().getStatus(),
                isOwner ? "RECEIVER" : "HELPER",
                isOwner ? app.getApplicantFamily().getFamilyName() : app.getPost().getFamily().getFamilyName()
            );
        }).collect(Collectors.toList());
    }
}