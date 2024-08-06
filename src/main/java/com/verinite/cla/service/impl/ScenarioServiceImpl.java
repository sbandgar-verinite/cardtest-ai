package com.verinite.cla.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verinite.cla.entity.Scenario;
import com.verinite.cla.repository.ScenarioRepository;
import com.verinite.cla.service.ScenarioService;

@Service
public class ScenarioServiceImpl implements ScenarioService {

	@Autowired
	private ScenarioRepository scenarioRepository;
	
	@Override
	public Scenario addScenario(Scenario scenario) {
		return scenarioRepository.save(scenario);
	}

	@Override
	public Scenario updateScenario(Scenario scenario) {
		return scenarioRepository.save(scenario);
	}
	
	@Override
	public Scenario findScenarioById(String id) {
		return scenarioRepository.findById(id).orElse(null);
	}

	@Override
	public List<Scenario> findAllScenario() {
		return scenarioRepository.findAll();
	}
	
	@Override
	public Scenario findScenarioByCode(String code) {
		return scenarioRepository.getScenarioByCode(code);
	}

	@Override
	public List<Scenario> findAllScenarios(List<String> scenarios) {
		return scenarioRepository.findAllByScenarios(scenarios);
	}

}
