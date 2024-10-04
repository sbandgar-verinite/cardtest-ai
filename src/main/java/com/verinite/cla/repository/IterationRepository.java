package com.verinite.cla.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.verinite.cla.entity.Iteration;

public interface IterationRepository extends JpaRepository<Iteration, String> {

}
