package com.verinite.cla.service;

import java.util.List;

import com.verinite.cla.entity.Scenario;

public interface ScenarioService {

public Scenario addScenario(Scenario scenario);
	
	public Scenario updateScenario(Scenario scenario);
	
	public Scenario findScenarioById(String id);
	
	public List<Scenario> findAllScenario();

	public Scenario findScenarioByCode(String code);
}
