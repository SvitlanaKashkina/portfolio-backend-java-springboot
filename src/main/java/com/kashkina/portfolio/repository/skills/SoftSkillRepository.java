package com.kashkina.portfolio.repository.skills;

import com.kashkina.portfolio.entity.skills.SoftSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoftSkillRepository extends JpaRepository<SoftSkill, Integer> {}

