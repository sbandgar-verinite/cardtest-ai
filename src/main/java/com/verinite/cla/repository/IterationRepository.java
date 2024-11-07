package com.verinite.cla.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.verinite.cla.entity.Iteration;

import java.util.List;

public interface IterationRepository extends JpaRepository<Iteration, String> {

   List<Iteration> findAllByProjectId(String projectId);

}
