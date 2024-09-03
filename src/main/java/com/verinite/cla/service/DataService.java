package com.verinite.cla.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verinite.cla.entity.RunData;

@Service
public class DataService {

//	@Autowired
//	private EntityDefinitionService entityDefinitionService;

	@Autowired
	private RunDataService runDataService;

	public List<Map<String, String>> generateData(List<String> entitiesRequired, String featureCode,
			String scenarioCode) {

		List<RunData> runData = new ArrayList<>();
		runData = runDataService.findRunDataByCode(featureCode);
//		runData = runDataService.findRunDataByCodeAndScenarioCode(featureCode, scenarioCode);
//		Map<String, List<Map<String, String>>> featureData = new HashMap<>();
		List<Map<String, String>> feature = new ArrayList<>();
		if (runData != null) {
			System.out.println("Rundata list: " + runData.size());
			for (RunData runDatum : runData) {
				if (entitiesRequired.contains(runDatum.getEntityName())) {
					System.out.println("Entity Name: " + runDatum.getEntityName() + " " + runDatum.getEntityName());
					if (runDatum.getEntityName().equals(runDatum.getEntityName())) {
//						featureData.computeIfAbsent(runDatum.getEntityName(), x -> new ArrayList<>()).add(runDatum.getAttributes());
						feature.add(runDatum.getAttributes());
					}
				}
			}
		}

		return feature;
	}
}
