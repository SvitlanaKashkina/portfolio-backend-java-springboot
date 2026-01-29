package com.kashkina.portfolio.repository.skills;

import com.kashkina.portfolio.entity.skills.TechStackItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechStackItemRepository extends JpaRepository<TechStackItem, Integer> {
    List<TechStackItem> findByCategoryIdOrderByOrderIndexAsc(Integer categoryId);
}

