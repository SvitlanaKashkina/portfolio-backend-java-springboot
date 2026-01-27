package com.kashkina.portfolio.service;

import com.kashkina.portfolio.dto.skills.*;
import com.kashkina.portfolio.entity.skills.SoftSkill;
import com.kashkina.portfolio.entity.skills.TechStackCategory;
import com.kashkina.portfolio.entity.skills.TechnicalSkillCategory;
import com.kashkina.portfolio.exception.DataNotFoundException;
import com.kashkina.portfolio.repository.skills.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class SkillsPageService {

    private static final Logger log = LoggerFactory.getLogger(SkillsPageService.class);

    private final SoftSkillRepository softSkillRepo;
    private final TechStackCategoryRepository techStackCatRepo;
    private final TechStackItemRepository techStackItemRepo;
    private final TechnicalSkillCategoryRepository techCatRepo;
    private final TechnicalSkillItemRepository techItemRepo;

    public SkillsPageDTO getAllSkills() {
        log.info("Fetching skills page data from the database");

        // --- Tech Stack ---
        List<TechStackCategory> categories = techStackCatRepo.findAll();
        if (categories.isEmpty()) {
            log.error("Tech stack categories not found");
            throw new DataNotFoundException("Tech stack categories not found");
        }
        log.info("Found {} tech stack categories", categories.size());
        log.debug("TechStackCategories: {}", categories);

        List<TechStackCategoryDTO> techStack = categories.stream()
                .sorted(Comparator.comparingInt(TechStackCategory::getOrderIndex))
                .map(c -> {
                    List<TechStackItemDTO> items = Optional.ofNullable(
                                    techStackItemRepo.findByCategoryIdOrderByOrderIndexAsc(c.getId()))
                            .orElse(List.of())
                            .stream()
                            .map(i -> new TechStackItemDTO(i.getId(), i.getName(), i.getOrderIndex(), c.getId()))
                            .toList();
                    return new TechStackCategoryDTO(c.getId(), c.getName(), c.getOrderIndex(), items);
                })
                .toList();
        log.info("Tech stack mapping completed");

        // --- Technical Skills ---
        List<TechnicalSkillCategory> techSkillCategories = techCatRepo.findAll();
        if (techSkillCategories.isEmpty()) {
            log.error("Technical skill categories not found");
            throw new DataNotFoundException("Technical skill categories not found");
        }
        log.info("Found {} technical skill categories", techSkillCategories.size());
        log.debug("TechnicalSkillCategories: {}", techSkillCategories);

        List<TechnicalSkillCategoryDTO> technicalSkills = techSkillCategories.stream()
                .sorted(Comparator.comparingInt(TechnicalSkillCategory::getOrderIndex))
                .map(cat -> {
                    List<TechnicalSkillItemDTO> items = Optional.ofNullable(
                                    techItemRepo.findByCategoryIdOrderByOrderIndexAsc(cat.getId()))
                            .orElse(List.of())
                            .stream()
                            .map(i -> new TechnicalSkillItemDTO(i.getId(), i.getDescription(), i.getOrderIndex(), cat.getId()))
                            .toList();
                    return new TechnicalSkillCategoryDTO(cat.getId(), cat.getName(), cat.getOrderIndex(), items);
                })
                .toList();
        log.info("Technical skills mapping completed");

        // --- Soft Skills ---
        List<SoftSkill> softSkillEntities = softSkillRepo.findAll();
        if (softSkillEntities.isEmpty()) {
            log.error("Soft skills not found");
            throw new DataNotFoundException("Soft skills not found");
        }
        log.info("Found {} soft skills", softSkillEntities.size());
        log.debug("SoftSkills: {}", softSkillEntities);

        List<SoftSkillDTO> softSkills = softSkillEntities.stream()
                .sorted(Comparator.comparingInt(SoftSkill::getOrderIndex))
                .map(s -> new SoftSkillDTO(s.getId(), s.getName(), s.getOrderIndex()))
                .toList();

        // --- Collect all in SkillsPageDTO ---
        SkillsPageDTO skillsPageDTO = new SkillsPageDTO(techStack, technicalSkills, softSkills);
        log.info("SkillsPageDTO prepared successfully");
        log.debug("SkillsPageDTO details: {}", skillsPageDTO);

        return skillsPageDTO;
    }
}
