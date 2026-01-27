package com.kashkina.portfolio.service;

import com.kashkina.portfolio.dto.home.HomeContentDTO;
import com.kashkina.portfolio.entity.home.HomeContent;
import com.kashkina.portfolio.exception.DataNotFoundException;
import com.kashkina.portfolio.repository.home.HomeContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class HomeContentService {

    private static final Logger log = LoggerFactory.getLogger(HomeContentService.class);

    private final HomeContentRepository homeContentRepository;

    public HomeContentDTO getHomeContentDTO() {
        log.info("Fetching Home content from database");

        HomeContent content = homeContentRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> {
                    log.error("Home content not found in database");
                    return new DataNotFoundException("Home content not found in database");
                });

        log.debug("Home content found: {}", content);

        HomeContentDTO dto = HomeContentDTO.builder()
                .fullName(content.getFullName())
                .roleTitle(content.getRoleTitle())
                .roleType(content.getRoleType())
                .shortBio(content.getShortBio())
                .githubUrl(content.getGithubUrl())
                .linkedinUrl(content.getLinkedinUrl())
                .build();

        log.info("Returning HomeContentDTO successfully");
        log.debug("HomeContentDTO: {}", dto);

        return dto;
    }
}


