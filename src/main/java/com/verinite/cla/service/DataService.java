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
	
	public Map<String, Object> generateData(List<String> entitiesRequired, String featureCode, String scenarioCode) {
		
		List<RunData> runData = new ArrayList<>();
//		runData = runDataService.findRunDataByCode(featureCode);
		runData = runDataService.findRunDataByCodeAndScenarioCode(featureCode, scenarioCode);
		Map<String, Object> featureData = new HashMap<>();
		if (runData != null) {
			System.out.println("Rundata list: " + runData.size());
			for(RunData runDatum: runData) {
				for (String entityName : entitiesRequired) {
					System.out.println("Entity Name: " + entityName + " " + runDatum.getEntityName());
					if (runDatum.getEntityName().equals(entityName)) {
						featureData.put(entityName, runDatum.getAttributes());
					}
				}
				
			}
		}
		System.out.println("Feature data: " + featureData.get("customer"));
		System.out.println("Feature data: " + featureData.get("account"));
		System.out.println("Feature data: " + featureData.get("card"));


		return featureData;
	}
}
