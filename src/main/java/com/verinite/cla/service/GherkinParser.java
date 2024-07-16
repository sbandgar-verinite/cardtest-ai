package com.verinite.cla.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.verinite.cla.dto.GherkinFormat;

public class GherkinParser {

	public static GherkinFormat parseGherkin(String gherkinText) {
		GherkinFormat gherkinFormat = new GherkinFormat();
		String[] lines = gherkinText.split("\n");
		boolean inScenario = false;
		String scenario = null;
		Map<String, List<String>> stepsList = new HashMap<>();
		List<String> steps = new ArrayList<>();
		for (String line : lines) {
			line = line.trim();
			if (line.startsWith("Feature:") || line.startsWith("**Feature:")) {
				line = line.replace("*", "");
				gherkinFormat.setFeature(line.substring("Feature:".length()).trim());
				inScenario = false;
			} else if (line.startsWith("Scenario:") || line.startsWith("**Scenario:")) {
				if (scenario != null && !steps.isEmpty()) {
					stepsList.put(scenario, steps);
					steps = new ArrayList<>();
					scenario = null;
				}
				line = line.replace("*", "");
				scenario = line.substring("Scenario:".length()).trim();
				inScenario = true;
				steps = new ArrayList<>();
			} else if (line.startsWith("Given") || line.startsWith("When") || line.startsWith("Then")
					|| line.startsWith("And") || line.startsWith("GIVEN") || line.startsWith("WHEN")
					|| line.startsWith("THEN") || line.startsWith("AND")) {
				if (inScenario) {
					steps.add(line.trim());
				}
			}
		}

		if (scenario != null && !steps.isEmpty()) {
			stepsList.put(scenario, steps);
			gherkinFormat.setScenarios(stepsList);
		}

		return gherkinFormat;
	}

}
