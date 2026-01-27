package com.kashkina.portfolio.repository.projects;

import com.kashkina.portfolio.entity.projects.Project;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Override
    @EntityGraph(attributePaths = {"technologies", "features", "screenshots"})
    List<Project> findAll();
}
