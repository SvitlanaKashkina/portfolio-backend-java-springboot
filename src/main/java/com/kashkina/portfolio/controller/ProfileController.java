package com.kashkina.portfolio.controller;

import com.kashkina.portfolio.dto.ProfileDTO;
import com.kashkina.portfolio.service.ProfileService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ProfileDTO getProfile() {
        return profileService.getProfileDTO();
    }

}

