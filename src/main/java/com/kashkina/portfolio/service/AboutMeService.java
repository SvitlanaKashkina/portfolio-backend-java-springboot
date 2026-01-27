package com.kashkina.portfolio.service;

import com.kashkina.portfolio.dto.about.AboutMeDTO;
import com.kashkina.portfolio.dto.about.AboutMeResponseDTO;
import com.kashkina.portfolio.dto.about.CertificateDTO;
import com.kashkina.portfolio.exception.DataNotFoundException;
import com.kashkina.portfolio.repository.about.AboutMeRepository;
import com.kashkina.portfolio.repository.about.CertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
@RequiredArgsConstructor
public class AboutMeService {

    private static final Logger log = LoggerFactory.getLogger(AboutMeService.class);

    private final AboutMeRepository repository;
    private final CertificateRepository certificateRepository;

    public AboutMeResponseDTO getAboutMeContent() {
        log.info("Fetching About Me sections from the database");

        List<AboutMeDTO> sections = repository.findAll().stream()
                .map(a -> new AboutMeDTO(
                        a.getSectionKey(),
                        a.getTitle(),
                        a.getContent()))
                .toList();

        log.debug("Found {} About Me sections", sections.size());

        if (sections.isEmpty()) {
            log.error("No About Me sections found");
            throw new DataNotFoundException("About Me sections not found");
        }

        log.info("Fetching certificates from database");
        List<CertificateDTO> certificates = certificateRepository.findAll().stream()
                .map(c -> new CertificateDTO(
                        c.getName(),
                        c.getIssuer(),
                        c.getYear()))
                .toList();
        log.debug("Found {} certificates", certificates.size());

        if (certificates.isEmpty()) {
            log.error("No certificates found");
            throw new DataNotFoundException("Certificates not found");
        }

        log.info("Successfully mapped About Me sections to DTOs");
        return new AboutMeResponseDTO(sections, certificates);
    }
}
