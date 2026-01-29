package com.kashkina.portfolio.repository.about;

import com.kashkina.portfolio.entity.about.AboutMe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AboutMeRepository extends JpaRepository<AboutMe, Integer> {

    Optional<AboutMe> findBySectionKey(String sectionKey);
}
