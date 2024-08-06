package com.verinite.cla.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.verinite.cla.entity.Scenario;

public interface ScenarioRepository extends JpaRepository<Scenario, String>{

	Scenario getScenarioByCode(String code);

	Scenario findByCode(String code);

	@Query("select s from Scenario s where s.code IN (?1)")
	List<Scenario> findAllByScenarios(List<String> scenarios);

}
