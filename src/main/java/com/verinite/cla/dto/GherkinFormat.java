package com.verinite.cla.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GherkinFormat {

	private String feature;
	private Map<String, List<String>> scenarios;

	public GherkinFormat() {
	}

	public GherkinFormat(String feature) {
		this.feature = feature;
		this.scenarios = new HashMap<>();
	}

	public void addScenario(String scenarioName, List<String> steps) {
		scenarios.put(scenarioName, new ArrayList<>(steps));
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public Map<String, List<String>> getScenarios() {
		return new HashMap<>(scenarios);
	}

	public void setScenarios(Map<String, List<String>> scenarios) {
		this.scenarios = new HashMap<>(scenarios);
	}

}
