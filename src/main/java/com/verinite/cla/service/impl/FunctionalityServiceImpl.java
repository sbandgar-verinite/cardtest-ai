package com.verinite.cla.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.verinite.cla.dto.FunctionalityDto;
import com.verinite.cla.entity.Feature;
import com.verinite.cla.entity.Scenario;
import com.verinite.cla.model.Functionality;
import com.verinite.cla.model.RunConfig;
import com.verinite.cla.repository.FunctionalityRepository;
import com.verinite.cla.service.FeatureService;
import com.verinite.cla.service.FunctionalityService;
import com.verinite.cla.service.ScenarioService;
import com.verinite.commons.controlleradvice.BadRequestException;

import jakarta.transaction.Transactional;

@Service
public class FunctionalityServiceImpl implements FunctionalityService {

	@Autowired
	private FunctionalityRepository functionalityRepo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private ScenarioService scenarioService;

	@Autowired
	private FeatureService featureService;

	@Override
	public void addFunctionality(FunctionalityDto funcDto) 
{
		//Functionality func = mapper.map(funcDto, Functionality.class);
		ObjectMapper mapper = new ObjectMapper();
 
		try {
	        String jsonString = "{ \"scenario\": { \"given\": [\"precondition1\"], \"when\": [\"event1\"], \"then\": [\"outcome1\"] }, \"feature_name\": \"My Feature\", \"functionality_name\": \"My Functionality\", \"steps\": \"step1\", \"test_case_description\": \"Description\", \"FN\":\" case id with date\",\"tags\": \"tag1\" }";

	        JsonNode details = mapper.readTree(jsonString);
	        
	        add(details);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		//add(details);
	
//		func.setFeatureId(funcDto.getFeatureId());
//		func.setFeatureName(funcDto.getFeatureName());
//		func.setFunctionality(funcDto.getFunctionality());
//		func.setSteps(funcDto.getSteps());
//		func.setTestCaseDescription(funcDto.getTestCaseDescription());
//		func.setTags(funcDto.getTags());
		//functionalityRepo.save(func);
//		return new FunctionalityDto(func.getId(), func.getName(), func.getTags(), func.getFeatureName());
	}

	@Override
	public void updateFunctionality(FunctionalityDto funcDto) throws BadRequestException {
		Functionality func = findFunctionalityById(funcDto.getId());
		mapper.map(funcDto, func);
//		func.setTestCaseDescription(funcDto.getTestCaseDescription());
//		func.setTags(funcDto.getTags());
//		func.setFunctionality(funcDto.getFunctionality());
//		func.setFeatureName(funcDto.getFeatureName());
		functionalityRepo.save(func);
//		return new FunctionalityDto(func.getId(), func.getName(), func.getTags(), func.getFeatureName());
	}

	@Override
	public Functionality findFunctionalityById(String id) throws BadRequestException {
		Optional<Functionality> response = functionalityRepo.findById(id);
		if (response.isEmpty()) {
			throw new BadRequestException("Case Not Found");
		}
		return response.get();
	}

	@Override
	public List<FunctionalityDto> findAllFunctionality() {
		List<Functionality> cases = functionalityRepo.findAll();
//		Functionality abv = mapper.map(new FunctionalityDto(), Functionality.class);
//		List<FunctionalityDto> funcList = new ArrayList<>();
//		for (Functionality cas : cases) {
//			funcList.add(mapper.map(cas, FunctionalityDto.class));
//		}
		return cases.stream().map(x -> mapper.map(x, FunctionalityDto.class)).collect(Collectors.toList());
//		return funcList;
//		return cases.stream()
//				.map(x -> mapper.map(x, Functionality.class))
//				.toList();
	}

	@Override
	public List<Functionality> findByTag(String tag) {
		return functionalityRepo.findByTag(tag);
	}

	@Override
	public void deleteFunctionalityById(String id) {
		functionalityRepo.deleteById(id);
	}

	@Transactional
	@Override
	public void add(JsonNode details) {
		if (details == null) {
			throw new BadRequestException("Request Body looks empty");
		}
		Feature feature = new Feature();
		if (details.get("scenario") != null) {
			saveScenario(details);
			feature = saveFeature(details);
		}

		Functionality func = new Functionality();
		func.setFeatureId(feature.getId());
		func.setFeatureName(feature.getCode());
		func.setFuncName(details.get("functionality_name").asText());
		func.setCaseDescription(details.get("test_case_description").asText());
        func.setSteps(Arrays.asList(details.get("steps").asText()));
		func.setCaseId("FN" + new Date().getYear() + (00000 + (functionalityRepo.count() + 1)));
		func.setTags(Arrays.asList(details.get("tags").asText()));
		functionalityRepo.save(func);
	}

	private Feature saveFeature(JsonNode details) {
		Feature feature = new Feature();
		feature.setCode(details.get("feature_name").asText().trim().replace(" ", ""));
//		feature.setDescription(null);
		feature.setNumberOfRunsRequired(1);

		RunConfig runConfig = new RunConfig();
		runConfig.setRunNumber(1L);
//		runConfig.setDescription(null);
		runConfig.setRunType("Normal");
		runConfig.setPreRunScripts(Arrays.asList(feature.getCode()));
		runConfig.setPostRunScripts(Arrays.asList(feature.getCode()));
		feature.setRunConfigs(Arrays.asList(runConfig));
		return featureService.addFeature(feature);
	}

	private Scenario saveScenario(JsonNode details) {
		Scenario scenario = new Scenario();
		scenario.setCode(details.get("feature_name").asText().trim().replace(" ", ""));
		scenario.getEntitiesRequired().add(scenario.getCode());
		if (details.get("scenario").get("given").isArray()) {
			ArrayNode givenArrayNode = (ArrayNode) details.get("scenario").get("given");
			Iterator<JsonNode> givenIterator = givenArrayNode.elements();
			while (givenIterator.hasNext())
				scenario.getGivenStatements().add(givenIterator.next().asText());
		}

		if (details.get("scenario").get("when").isArray()) {
			ArrayNode whenNode = (ArrayNode) details.get("scenario").get("when");
			Iterator<JsonNode> whenIterator = whenNode.elements();
			while (whenIterator.hasNext())
				scenario.getGivenStatements().add(whenIterator.next().asText());
		}

		if (details.get("scenario").get("then").isArray()) {
			ArrayNode thenNode = (ArrayNode) details.get("scenario").get("then");
			Iterator<JsonNode> thenIterator = thenNode.elements();
			while (thenIterator.hasNext())
				scenario.getGivenStatements().add(thenIterator.next().asText());
		}
		return scenarioService.addScenario(scenario);
	}
}
