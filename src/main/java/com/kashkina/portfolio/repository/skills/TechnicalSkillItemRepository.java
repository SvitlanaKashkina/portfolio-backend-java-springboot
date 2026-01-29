package com.kashkina.portfolio.repository.skills;

import com.kashkina.portfolio.entity.skills.TechnicalSkillItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnicalSkillItemRepository extends JpaRepository<TechnicalSkillItem, Integer> {
    List<TechnicalSkillItem> findByCategoryIdOrderByOrderIndexAsc(Integer categoryId);
}

