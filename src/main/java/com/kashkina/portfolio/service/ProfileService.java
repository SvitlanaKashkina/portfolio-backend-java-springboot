package com.kashkina.portfolio.service;

import com.kashkina.portfolio.dto.ProfileDTO;
import com.kashkina.portfolio.entity.Profile;
import com.kashkina.portfolio.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileDTO getProfileDTO() {
        Profile profile = profileRepository.findAll().stream().findFirst().orElse(null);
        if (profile == null) return null;

        return new ProfileDTO(
                profile.getFullName(),
                profile.getRole(),
                profile.getShortBio(),
                profile.getAboutText(),
                profile.getLocation(),
                profile.getEmail(),
                profile.getGithubUrl(),
                profile.getLinkedinUrl()
        );
    }
}

