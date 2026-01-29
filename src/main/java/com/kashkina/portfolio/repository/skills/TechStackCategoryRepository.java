package com.kashkina.portfolio.repository.skills;

import com.kashkina.portfolio.entity.skills.TechStackCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechStackCategoryRepository extends JpaRepository<TechStackCategory, Integer> {}

