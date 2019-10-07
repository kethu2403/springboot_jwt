package com.hexaware.jumbo.repo;

import com.hexaware.jumbo.model.Project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * ProjectRepository.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
