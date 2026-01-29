package com.kashkina.portfolio.repository.home;

import com.kashkina.portfolio.entity.home.HomeContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeContentRepository extends JpaRepository<HomeContent, Integer> {
}

