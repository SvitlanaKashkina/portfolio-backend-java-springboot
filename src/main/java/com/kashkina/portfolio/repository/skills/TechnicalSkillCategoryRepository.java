package com.kashkina.portfolio.repository.skills;

import com.kashkina.portfolio.entity.skills.TechnicalSkillCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnicalSkillCategoryRepository extends JpaRepository<TechnicalSkillCategory, Integer> {}

