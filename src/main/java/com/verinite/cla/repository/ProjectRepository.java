package com.verinite.cla.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.verinite.cla.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, String> {

}
