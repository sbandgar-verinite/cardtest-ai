package com.verinite.cla.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.verinite.cla.entity.Scenario;

public interface ScenarioRepository extends JpaRepository<Scenario, String>{

	Scenario getScenarioByCode(String code);

}
