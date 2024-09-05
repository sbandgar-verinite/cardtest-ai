package com.verinite.cla.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.verinite.cla.entity.Feature;
import com.verinite.cla.entity.Project;
import com.verinite.cla.entity.RunData;
import com.verinite.cla.entity.Scenario;
import com.verinite.cla.repository.RunDataRepository;
import com.verinite.cla.service.FeatureService;
import com.verinite.cla.service.ProjectService;
import com.verinite.cla.service.RunDataService;
import com.verinite.cla.service.ScenarioService;
import com.verinite.commons.controlleradvice.BadRequestException;

import jakarta.transaction.Transactional;

@Service
public class RunDataServiceImpl implements RunDataService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private RunDataRepository runDataRepository;

	@Autowired
	private FeatureService featureService;

	@Autowired
	private ScenarioService scenarioService;

	@Autowired
	private ProjectService projectService;

	@Value("${data.gen.service.baseUrl}")
	private String dgBaseUrl;

	@Override
	public RunData addRunData(RunData runData) {
		return runDataRepository.save(runData);
	}

	@Override
	public RunData updateRunData(RunData runData) {
		return runDataRepository.save(runData);
	}

	@Override
	public RunData findRunDataById(String id) {
		return runDataRepository.findById(id).orElse(null);
	}

	@Override
	public List<RunData> findAllRunData() {
		return runDataRepository.findAll();
	}

	@Override
	public List<RunData> findRunDataByCode(String code) {
		return runDataRepository.getRunDataByCode(code);
	}

//	@Override
//	public List<RunData> findRunDataByCodeAndScenarioCode(String code, String scenarioCode) {
//		return runDataRepository.getRunDataByCodeAndScenarioCode(code, scenarioCode);
//	}

	@Override
	@Async
	@Transactional
	public void generateData(String projectId) {
		Project project = projectService.findProjectById(projectId);

		for (String feat : project.getFeatures()) {
			Feature feature = featureService.findFeatureByCode(feat);
			if (feature == null) {
				throw new BadRequestException("Feature Not Found");
			}

			List<String> scenarios = new ArrayList<>();
			if (!CollectionUtils.isEmpty(feature.getRunConfigs())) {
				feature.getRunConfigs().stream().forEach(x -> {
					scenarios.addAll(x.getPreRunScripts());
					scenarios.addAll(x.getPostRunScripts());
				});
			}

			List<Scenario> scenarioList = scenarioService.findAllScenarios(scenarios);
			Set<String> entities = new HashSet<>();
//			String entity = "";
			String regex = "<([^%>\s]+)>";
			Pattern pattern = Pattern.compile(regex);
			Map<String, JsonNode> reqObj = new HashMap<>();
			for (Scenario x : scenarioList) {
				List<String> steps = new ArrayList<>();
				steps.addAll(x.getGivenStatements());
				steps.addAll(x.getThenOutcomes());
				steps.addAll(x.getWhenConditions());
//				entity = x.getEntitiesRequired().get(0);
				entities.addAll(x.getEntitiesRequired());
				createInputRequestForDG(steps, pattern, x.getEntitiesRequired().get(0), reqObj);
			}

//			Map<String, JsonNode> reqObj = createInputRequestForDG(steps, pattern, entity);
			JsonNode response = restTemplate.postForObject(dgBaseUrl + "/api/dg/v1/project/1/generate?output=json",
					reqObj, JsonNode.class);

			runDataRepository.deleteByCode(feature.getCode());
			for (String ent : entities) {
				if (response.get(ent) != null) {
					RunData rd = new RunData();
					rd.setAttributes(convertToHashMap(response.get(ent)));
					rd.setCode(feature.getCode());
					rd.setEntityName(ent);
					rd.setCreatedOn(new Date().toInstant().toEpochMilli());
					rd.setProjectName(project.getName());
					runDataRepository.save(rd);
				}
			}
		}
	}

	private Map<String, String> convertToHashMap(JsonNode jsonNode) {
		Map<String, String> map = new HashMap<>();
		if (jsonNode.isObject()) {
			Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
			while (fields.hasNext()) {
				Map.Entry<String, JsonNode> field = fields.next();
				map.put(field.getKey(), field.getValue().asText());
			}
		}
		return map;
	}

	private Map<String, JsonNode> createInputRequestForDG(List<String> steps, Pattern pattern, String entity,
			Map<String, JsonNode> reqObj) {
		steps.stream().forEach(x -> {
			Matcher matcher = pattern.matcher(x);
			while (matcher.find()) {
				String enclosedText = matcher.group(1);
//				String[] splitedString = enclosedText.split("\\.");
				if (reqObj.containsKey(entity)) {
					JsonNode value = reqObj.get(entity);
					if (value instanceof ObjectNode) {
						ObjectNode objectNode = (ObjectNode) value;
						objectNode.put(enclosedText, "String");
						reqObj.put(entity, objectNode);
					}
				} else {
					ObjectNode objectNode = new ObjectMapper().createObjectNode();
					objectNode.put(enclosedText, "String");
					reqObj.put(entity, objectNode);
				}
			}
		});
		return reqObj;
	}
}
