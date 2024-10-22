package com.verinite.cla.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.verinite.cla.config.Constants;
import com.verinite.cla.dto.CamundaRequest;
import com.verinite.cla.dto.CamundaResponse;
import com.verinite.cla.dto.ProjectDto;
import com.verinite.cla.dto.StatusDto;
import com.verinite.cla.entity.Feature;
import com.verinite.cla.entity.Iteration;
import com.verinite.cla.entity.Project;
import com.verinite.cla.entity.RunPlan;
import com.verinite.cla.entity.Scenario;
import com.verinite.cla.model.RunConfig;
import com.verinite.cla.model.RunScenario;
import com.verinite.cla.service.impl.FileGenerationService;
import com.verinite.cla.util.PropertiesConfig;
import com.verinite.cla.util.RunPlanStatus;
import com.verinite.cla.util.Status;
import com.verinite.commons.controlleradvice.BadRequestException;
import com.verinite.commons.dto.StatusResponse;
import com.verinite.commons.model.Config;
import com.verinite.commons.repo.ConfigurationRepository;
import com.verinite.commons.service.ExtService;

import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@Service
public class SetupService {

//	@Autowired
//	private TenantService tenantService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private RunPlanService runPlanService;

	@Autowired
	private FeatureService featureService;

	@Autowired
	private DateService dateService;

	@Autowired
	private ScenarioService scenarioService;

	@Autowired
	private TemplateService templateService;

	@Autowired
	private DataService dataService;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ConfigurationRepository configRepo;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private ExtService extService;

	@Autowired
	private PropertiesConfig propsConfig;

	@Autowired
	private FileGenerationService fileGenService;

	@Autowired
	private ObjectMapper objectMapper;

	private final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

	@Autowired
	private ResourceLoader resourceLoader;

	public JsonNode readFromJson() {
		Resource resource = resourceLoader.getResource("classpath:data.json");
		try {
			return objectMapper.readTree(resource.getInputStream());
		} catch (IOException e) {
			throw new BadRequestException("Config file not found");
		}
	}

//	public Tenant addNewTenant(TenantDto tenantDto) {
//		Tenant tenant = new Tenant();
//		tenant.setName(tenantDto.getName());
//		tenant.setProduct(tenantDto.getProduct());
//		tenant.setFeatures(tenantDto.getFeatures());
//		tenantService.addTenant(tenant);
//		return tenant;
//	}
//
//	public Tenant getTenantById(String tenantId) {
//		return tenantService.findTenantById(tenantId);
//	}
//
//	public List<Tenant> getAllTenants() {
//		return tenantService.findAllTenant();
//	}

//	public void addNewProject(ProjectDto projectDto) throws ParseException {
//		Project project = new Project();
//		project.setName(projectDto.getName());
//		project.setTenantId(projectDto.getTenantId());
//		if (projectDto.getStartDate() != null) {
//			project.setStartDate(projectDto.getStartDate());
//		}
////		project.setValidBillingCycles(projectDto.getValidBillingCycles());
//		project.setFeatures(projectDto.getFeatures());
////		project.setBillingCycleSelectionCriteria(projectDto.getBillingCycleSelectionCriteria());
////		project.setDueDays(projectDto.getDueDays());
//		project.setAttributes(projectDto.getAttributes());
//
//		project = projectService.addProject(project);
//
//	}

	public StatusResponse createRunPlanForProject(Project project)
			throws ParseException {
//		Project project = new Project();
//		project = projectService.findProjectById(project);
		Optional<Config> runConf = configRepo.findByKeyName("RUN_CONFIG");
		Feature feature = new Feature();
		for (Iteration iteration : project.getIterations()) {
			Long startDate = iteration.getStartDate();
			Multimap<Date, Map<String, RunScenario>> listOfRunScenarios = LinkedHashMultimap.create();
			if(iteration.getGenerated())
			{
				continue;
			}
			for (String featureCode : iteration.getFeatures()) {
				feature = featureService.findFeatureByCode(featureCode);
				List<RunConfig> runConfigs = feature.getRunConfigs();
				for (RunConfig runConfig : runConfigs) {
					RunScenario preRunScenario = new RunScenario();
					preRunScenario.setFeatureCode(feature.getCode());

					RunScenario postRunScenario = new RunScenario();
					postRunScenario.setFeatureCode(feature.getCode());
					Map<String, RunScenario> featureRunScenario = new HashMap<>();

//				if (runConfig.getRunType().equals("Normal")) {
					JsonNode node = calculateRunTime(runConfig.getRunType(), runConf.get());
					Date newDate = calculateDate(startDate.toString(), node);
//					runCalendar.add(Calendar.DATE, (runConfig.getRunNumber().intValue() - 1));
//				}
					startDate = newDate.toInstant().toEpochMilli();
					preRunScenario.setScenarios(runConfig.getPreRunScripts());
					featureRunScenario.put("PrerunScenarios", preRunScenario);
					postRunScenario.setScenarios(runConfig.getPostRunScripts());
					featureRunScenario.put("PostrunScenarios", postRunScenario);
//				Date date = new Date(runCalendar.getTime().getTime());
//				startDate = new Date(date.getTime());
					listOfRunScenarios.put(newDate, featureRunScenario);
				}
			}

			int runCounter = 0;

			for (Date runDate : listOfRunScenarios.keySet()) {
				RunPlan runPlan = new RunPlan();
				runPlan.setProjectId(project.getId());
				runPlan.setRunDate(runDate.toInstant().toEpochMilli());
				runCounter++;
				runPlan.setSequenceNumber(runCounter);
				runPlan.setDescription("Run Number: #" + runCounter);
//				runPlan.setBillingCycleConsidered(selectedBC);

				Collection<Map<String, RunScenario>> runScenarios = listOfRunScenarios.get(runDate);
				List<RunScenario> prerunScenarios = new ArrayList<>();
				List<RunScenario> postrunScenarios = new ArrayList<>();

				for (Map<String, RunScenario> runScenario : runScenarios) {
					prerunScenarios.add(runScenario.get("PrerunScenarios"));
					postrunScenarios.add(runScenario.get("PostrunScenarios"));
				}

				runPlan.setPreRunScripts(prerunScenarios);
				runPlan.setPostRunScripts(postrunScenarios);
				runPlan.setItnSeq(iteration.getId());
				runPlan.setStatus(Status.CREATED.getStatus());
				runPlanService.addRunPlan(runPlan);
			}
		}

		return new StatusResponse("Success", HttpStatus.OK.value(), "RunPlans Created Successfully");
	}

	private Date calculateDate(String date, JsonNode node) throws ParseException {
		String jsFunc = node.get("js_func").asText();
		String functionName = node.get("function_name").asText();

		StringBuilder str = new StringBuilder(jsFunc);
		str.append(functionName);
		str.append("(").append(date).append(");");
		String calDate = extService.executeJs(str.toString());
		return formatter.parse(calDate);

//		try (Context context = Context.create()) {
//			context.eval("js", jsFunc);
//			org.graalvm.polyglot.Value greetFunction = context.getBindings("js").getMember(functionName);
//	        String result = greetFunction.execute(startDate).asString();
//	        return Date.valueOf(result);
//		}

//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(startDate);
//		calendar.add(Calendar.DAY_OF_MONTH, node.get("no_of_days_req").asInt());
//		return calendar.getTime();
	}

	private JsonNode calculateRunTime(String runType, Config config) {
		JsonNode node = null;
		if (config.getData() != null) {
			try {
				node = mapper.readTree(config.getData());
				JsonNode array = node.get("runConfigs");
				if (array.isArray()) {
					ArrayNode arrayNode = (ArrayNode) array;
					for (JsonNode jNode : arrayNode) {
						if (jNode.get("run_type").asText().equalsIgnoreCase(runType)) {
							return jNode;
						}
					}
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public StatusResponse createFeatureFile(String projectId, String runPlanId) throws TemplateNotFoundException,
			MalformedTemplateNameException, ParseException, IOException, TemplateException {

		RunPlan runPlan = new RunPlan();
		runPlan = runPlanService.findRunPlanById(runPlanId);

		List<RunScenario> preRunScenarios = new ArrayList<>();
		preRunScenarios = runPlan.getPreRunScripts();
		String outFeatureCode = "";

		List<RunScenario> postRunScenarios = new ArrayList<>();
		postRunScenarios = runPlan.getPostRunScripts();

		deleteFileIfExists(runPlan.getId());
		genScenarioFile(runPlan, preRunScenarios, outFeatureCode, "pre");
		runPlan.setIsPreUploadEnable(Boolean.TRUE);
		genScenarioFile(runPlan, postRunScenarios, outFeatureCode, "post");
		runPlan.setIsPostUploadEnable(Boolean.TRUE);

//		for (RunScenario postRunScenario : postRunScenarios) {
//			List<String> scenarioCodes = postRunScenario.getScenarios();
//			if (!CollectionUtils.isEmpty(scenarioCodes)) {
//				for (String scenarioCode : scenarioCodes) {
//					Scenario scenario = scenarioService.findScenarioByCode(scenarioCode);
//					Map<String, Object> featureData = new HashMap<>();
//					featureData = dataService.generateData(scenario.getEntitiesRequired(), postRunScenario.getFeatureCode(),
//							scenarioCode);
//					for (String givenTemplate : scenario.getGivenStatements()) {
//						String templateName = postRunScenario.getFeatureCode() + "-" + runPlan.getSequenceNumber() + "-"
//								+ scenarioCode + "-GIVEN";
//						templateService.createRunScenarioFile(runPlan.getId() + "-post", templateName, givenTemplate,
//								featureData);
//					}
//					for (String whenTemplate : scenario.getWhenConditions()) {
//						String templateName = postRunScenario.getFeatureCode() + "-" + runPlan.getSequenceNumber() + "-"
//								+ scenarioCode + "-WHEN";
//						templateService.createRunScenarioFile(runPlan.getId() + "-post", templateName, whenTemplate,
//								featureData);
//					}
//					for (String thenTemplate : scenario.getThenOutcomes()) {
//						String templateName = postRunScenario.getFeatureCode() + "-" + runPlan.getSequenceNumber() + "-"
//								+ scenarioCode + "-THEN";
//						templateService.createRunScenarioFile(runPlan.getId() + "-post", templateName, thenTemplate,
//								featureData);
//					}
//				}
//			}
//		}
		runPlanService.addRunPlan(runPlan);
		return new StatusResponse(Constants.SUCCESS, HttpStatus.OK.value(),
				propsConfig.getFeatureFileCreatedSuccessfully());
	}

	private void deleteFileIfExists(String runPlanId) {
		File preFile = new File(runPlanId + "-pre.txt");
		File postFile = new File(runPlanId + "-post.txt");
		if (preFile.exists()) {
			preFile.delete();
		}
		if (postFile.exists()) {
			postFile.delete();
		}
	}

	private void genScenarioFile(RunPlan runPlan, List<RunScenario> runScenarios, String outFeatureCode, String type)
			throws TemplateNotFoundException, MalformedTemplateNameException, freemarker.core.ParseException,
			IOException, TemplateException {
		for (RunScenario runScenario : runScenarios) {
			List<String> scenarioCodes = runScenario.getScenarios();
			if (!outFeatureCode.equals(runScenario.getFeatureCode()) & scenarioCodes.size() > 0) {
				outFeatureCode = runScenario.getFeatureCode();
				String templateName = runScenario.getFeatureCode() + "-" + runPlan.getSequenceNumber() + "-FEATURE";
				templateService.createRunScenarioFile(runPlan.getId() + "-" + type, templateName,
						"Feature: " + runScenario.getFeatureCode());
			}

			String outScenarioCode = "";
			List<String> examples = new ArrayList<>();
			List<Map<String, String>> featureData = new ArrayList<>();
			for (String scenarioCode : scenarioCodes) {
				examples = new ArrayList<>();
				featureData = new ArrayList<>();
				if (!outScenarioCode.equals(scenarioCode)) {
					outScenarioCode = scenarioCode;
					String templateName = runScenario.getFeatureCode() + "-" + runPlan.getSequenceNumber() + "-"
							+ scenarioCode + "-SCENARIO";
					templateService.createRunScenarioFile(runPlan.getId() + "-" + type, templateName,
							"Scenario Outline: " + scenarioCode);
				}
				Scenario scenario = scenarioService.findScenarioByCode(scenarioCode);
				featureData = dataService.generateData(scenario.getEntitiesRequired(), runScenario.getFeatureCode(),
						scenarioCode);

				int givenCount = 0;
				int whenCount = 0;
				int thenCount = 0;

				for (String givenTemplate : scenario.getGivenStatements()) {
					examples.add(givenTemplate);
					givenTemplate = replacePercentEnclosed(givenTemplate);
					if (givenCount == 0) {
						givenCount++;
						givenTemplate = "    Given " + givenTemplate;
					} else {
						givenTemplate = "    And " + givenTemplate;
					}

					String templateName = runScenario.getFeatureCode() + "-" + runPlan.getSequenceNumber() + "-"
							+ scenarioCode + "-GIVEN";
					templateService.createRunScenarioFile(runPlan.getId() + "-" + type, templateName, givenTemplate);
				}
				for (String whenTemplate : scenario.getWhenConditions()) {
					examples.add(whenTemplate);
					whenTemplate = replacePercentEnclosed(whenTemplate);
					if (whenCount == 0) {
						whenCount++;
						whenTemplate = "    When " + whenTemplate;
					} else {
						whenTemplate = "    And " + whenTemplate;
					}

					String templateName = runScenario.getFeatureCode() + "-" + runPlan.getSequenceNumber() + "-"
							+ scenarioCode + "-WHEN";
					templateService.createRunScenarioFile(runPlan.getId() + "-" + type, templateName, whenTemplate);
				}
				for (String thenTemplate : scenario.getThenOutcomes()) {
					examples.add(thenTemplate);
					thenTemplate = replacePercentEnclosed(thenTemplate);
					if (thenCount == 0) {
						thenCount++;
						thenTemplate = "    Then " + thenTemplate;
					} else {
						thenTemplate = "    And " + thenTemplate;
					}
					String templateName = runScenario.getFeatureCode() + "-" + runPlan.getSequenceNumber() + "-"
							+ scenarioCode + "-THEN";
					templateService.createRunScenarioFile(runPlan.getId() + "-" + type, templateName, thenTemplate);
				}
				String keys = appendData(featureData, examples);
				templateService.createRunScenarioFile(runPlan.getId() + "-" + type,
						String.valueOf(new Date().toInstant().toEpochMilli()), keys.toString().replace("%", ""));
			}
		}
	}

	public String appendData(List<Map<String, String>> featureData, List<String> examples) {
		String regex = "%(.*?)%";
		Pattern pattern = Pattern.compile(regex);
		List<String> attributeName = new ArrayList<>();
		StringBuilder keys = new StringBuilder();
//		if (!CollectionUtils.isEmpty(featureData)) {
		keys.append("     Examples:     \n");
		keys.append("          |  ");
		for (String statement : examples) {
			fetchBracesData(statement, attributeName);
		}
		attributeName.forEach(x -> keys.append(x + "  |  "));
		keys.append("\n");
//		JsonNode node = readFromJson();
		JsonNode node = readFromConfig(Constants.DATA_CONFIG);
		for (Map<String, String> feature : featureData) {
			keys.append("          |  ");
			for (String statement : attributeName) {
				Matcher matcher = pattern.matcher(statement);
				Boolean isExists = Boolean.FALSE;
				while (matcher.find()) {
					isExists = Boolean.TRUE;
					keys.append(node.get(matcher.group(1)).asText() + "  |  ");
				}
				if (!isExists && feature.containsKey(statement)) {
					keys.append(feature.get(statement) + "  |  ");
				}
			}
			keys.append("\n");
		}
//		}
		return keys.toString();
	}

//	public void fetch(String input, List<String> attributeName) {
//		String regex = "\\$\\{([^}]+)\\}";
//		Pattern pattern = Pattern.compile(regex);
//		Matcher matcher = pattern.matcher(input);
//		while (matcher.find()) {
//			String variableContent = matcher.group(1);
//			String[] parts = variableContent.split("\\.");
//			if (parts.length == 2) {
////				String firstPart = parts[0];
//				String secondPart = parts[1];
////				attributeName.add(firstPart);
//				attributeName.add(secondPart);
//			} else {
//				System.out.println("Variable format is not as expected: " + variableContent);
//			}
//		}
//	}

	private JsonNode readFromConfig(String dataConfig) {
		Optional<Config> config = configRepo.findByKeyName(dataConfig);
		if (config.isEmpty()) {
			throw new BadRequestException("Data Config Not Present");
		}
		try {
			return mapper.readTree(config.get().getData());
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void fetchBracesData(String element, List<String> attributeName) {
		String regex = "<(.*?)>";
//		String regex = "<([^%>\\s]+)>";
//		String regex = "<%?([^%>\\s]+)%?>";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(element);
		while (matcher.find()) {
			attributeName.add(matcher.group(1));
		}
	}

	public String replacePercentEnclosed(String input) {
		String regex = "%(.*?)%";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		StringBuffer result = new StringBuffer();
		while (matcher.find()) {
			String value = matcher.group(1);
			matcher.appendReplacement(result, value);
		}
		matcher.appendTail(result);
		return result.toString();
	}

	public Object uploadFeatureFileToGit(String runPlanId, String type) throws IOException {
		RunPlan runPlan = runPlanService.findRunPlanById(runPlanId);
		if (Objects.isNull(runPlan)) {
			throw new BadRequestException("Run Plan Not Found");
		}

		String fileName = runPlanId + "-" + type + ".txt";
		File file = new File(fileName);
		byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file), false);
		String encodedFileContent = new String(encoded, StandardCharsets.UTF_8);
		System.out.println("Base64 encoded: " + encodedFileContent);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setBearerAuth(propsConfig.getGitFeatureRepoAuthToken());
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		String sha = "";
		String url = "";
		ResponseEntity<String> alreadyExists = new ResponseEntity<String>(HttpStatus.OK);
		try {
			alreadyExists = restTemplate.exchange(
					propsConfig.getGitFeatureRepoUrl() + "/" + runPlanId + "-" + type + ".feature", HttpMethod.GET,
					entity, String.class);
		} catch (Exception ex) {
			ex.getStackTrace();
		}

		if (alreadyExists != null && alreadyExists.hasBody() && alreadyExists.getStatusCode().is2xxSuccessful()) {
			JsonNode root = objectMapper.readTree(alreadyExists.getBody());
			sha = root.path("sha").asText();
			url = root.path("url").asText();
		} else {
			ResponseEntity<String> response = restTemplate.exchange(propsConfig.getGitFeatureRepoUrl(), HttpMethod.GET,
					entity, String.class);
//					new ParameterizedTypeReference<List<Map<String, Object>>>() {
//					});
//			List<Map<String, Object>> entities = responseEntity.getBody();
			if (response.getStatusCode().is2xxSuccessful()) {
				JsonNode root = objectMapper.readTree(response.getBody());
				sha = root.path("sha").asText();
				url = root.path("url").asText();
			}
		}

		System.out.println("SHA & URL: " + sha + " " + url);

//		System.out.println("entities: " + entities);
//		String sha = (String) entities.get(0).get("sha");
//		String url = (String) entities.get(0).get("url");
//		System.out.println("SHA & URL: " + sha + " " + url);

		String baseURL = propsConfig.getGitFeatureRepoUrl() + "/" + runPlanId + "-" + type + ".feature?ref="
				+ propsConfig.getGitFeatureRepobranch();

		JSONObject bodyParam = new JSONObject();
		JSONObject committer = new JSONObject();
//		committer.put("name", "Sankhadeep Chakraborty");
//		committer.put("email", "sankhadeep.chakraborty@verinite.com");
//		bodyParam.put("message", "Commit for Project" + Math.random());
//		bodyParam.put("content", encodedFileContent);
//		bodyParam.put("sha", sha);
//		bodyParam.put("branch", "main");
//		bodyParam.put("committer", committer);
		committer.put("name", "Sumeet Bandgar");
		committer.put("email", "sumeet.bandgar@verinite.com");
		bodyParam.put("message", "Commit for Project" + Math.random());
		bodyParam.put("content", encodedFileContent);
		bodyParam.put("sha", sha);
		bodyParam.put("branch", "main");
		bodyParam.put("committer", committer);

		System.out.println("Body param: " + bodyParam.toString());

		HttpHeaders headers1 = new HttpHeaders();
		headers1.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers1.setBearerAuth(propsConfig.getGitFeatureRepoAuthToken());
		HttpEntity<String> entity1 = new HttpEntity<String>(bodyParam.toString(), headers1);

		ResponseEntity<Object> responseEntity1 = restTemplate.exchange(baseURL, HttpMethod.PUT, entity1,
				new ParameterizedTypeReference<Object>() {
				});

		System.out.println("Response Entity: " + responseEntity1);

		if (responseEntity1.getStatusCode().is2xxSuccessful()) {
			if (type.equalsIgnoreCase(Constants.PRE_RUN_PLAN))
				runPlan.setIsPreExecEnable(Boolean.TRUE);
			else if (type.equalsIgnoreCase(Constants.POST_RUN_PLAN))
				runPlan.setIsPostExecEnable(Boolean.TRUE);
			runPlanService.addRunPlan(runPlan);
			return runPlanService.checkStatus(runPlanId);
		} else {
			return new StatusResponse("Upload Failed");
		}
	}

	public StatusDto buildJenkinsJob(String runPlanId, String type) throws InterruptedException {
		RunPlan runPlan = runPlanService.findRunPlanById(runPlanId);
		if (Objects.isNull(runPlan)) {
			throw new BadRequestException("Run Plan Not Found");
		}
		/* Issue Crumb */

		byte[] plainCredsBytes = propsConfig.getJenkinsCreds().getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes, false);
		String base64Creds = new String(base64CredsBytes);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(
				propsConfig.getJenkinsUrl() + "/crumbIssuer/api/json", HttpMethod.GET, entity,
				new ParameterizedTypeReference<Map<String, String>>() {
				});
		Map<String, String> entities = responseEntity.getBody();
		Map<String, List<String>> respHeader = responseEntity.getHeaders();

		String crumb = entities.get("crumb");
		String crumbRequestField = entities.get("crumbRequestField");
		System.out.println("Crumb: " + crumb);

		/* Trigger Build */

		HttpHeaders headerst = new HttpHeaders();
		headerst.add("Authorization", "Basic " + base64Creds);
		headerst.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headerst.add(crumbRequestField, crumb);
		headerst.set("Cookie", respHeader.get("Set-Cookie").get(0));
		HttpEntity<String> entityt = new HttpEntity<String>(headerst);

		String fullUrl = propsConfig.getJenkinsUrl() + "/job/" + propsConfig.getJenkinsJobName()
				+ "/buildWithParameters?RUN_PLAN_ID=" + runPlanId + "&SCENARIO_TYPE=" + type;
		ResponseEntity<String> responseEntityt = restTemplate.exchange(fullUrl, HttpMethod.POST, entityt, String.class);

		HttpHeaders respHeaders = responseEntityt.getHeaders();
		String location = respHeaders.getLocation().toString();
		System.out.println("Location: " + location + "api/json");

		/* Get Build number */

		HttpHeaders headersbn = new HttpHeaders();
		headersbn.add("Authorization", "Basic " + base64Creds);
		headersbn.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entitybn = new HttpEntity<String>(headersbn);

		ResponseEntity<Map<String, Object>> responseEntitybn = restTemplate.exchange(location + "api/json",
				HttpMethod.GET, entitybn, new ParameterizedTypeReference<Map<String, Object>>() {
				});

		System.out.println("Response: " + responseEntitybn.getStatusCode());

		Map<String, Object> entitiesbn = responseEntitybn.getBody();
		JSONObject jo = new JSONObject(entitiesbn);

		System.out.println("Entity bn: " + jo.toString());
		runPlan.setStatus(Status.INPROGRESS.getStatus());
		if (type.equalsIgnoreCase("pre")) {
			runPlan.setPreRunStatus(RunPlanStatus.BUILD_TRIGGERED.getStatus());
		} else if (type.equalsIgnoreCase("post")) {
			runPlan.setPostRunStatus(RunPlanStatus.BUILD_TRIGGERED.getStatus());
		}
		runPlanService.updateRunPlan(runPlan);
		return runPlanService.checkStatus(runPlanId);
	}

	public StatusDto execution(String runPlanId, String type) throws InterruptedException {
		RunPlan runPlan = runPlanService.findRunPlanById(runPlanId);
		if (Objects.isNull(runPlan))
			throw new BadRequestException("Run Plan Not Found");
		if (type == null)
			throw new BadRequestException("Type Not Specified");
		Project project = projectService.findProjectById(runPlan.getProjectId());
		if (project.getIsFlowAuto()) {
			coreExecutionAsync(runPlan, type);
			return runPlanService.checkStatus(runPlanId);
		} else
			return coreExecution(runPlan, type);
	}

	private StatusDto coreExecution(RunPlan runPlan, String type) throws InterruptedException {
		CamundaRequest request = new CamundaRequest();
//		request.setProjectId(runPlan.getProjectId());
//		request.setRunPlanId(runPlan.getId());
		request.setType(type.toLowerCase());
		CamundaResponse response = null;
		StatusDto statusDto = new StatusDto();
		if (type.equalsIgnoreCase(Constants.PRE_RUN_PLAN)) {
			response = startCamundaWorkflow(request, runPlan);
			statusDto = buildJenkinsJob(runPlan.getId(), type);
			runPlan.setInstanceId(response.getProcessInstanceKey());
			runPlan.setPreRunTaskId(response.getNextTask().getTaskId());
			runPlan.setIsPreUploadEnable(Boolean.FALSE);
		} else if (type.equalsIgnoreCase(Constants.POST_RUN_PLAN)) {
//			response = completeCamundaTask(request, runPlan.getInstanceId(), runPlan.getPostConfigRunTaskId());
//			runPlan.setPostRunTaskId(response.getNextTask().getTaskId());
			statusDto = buildJenkinsJob(runPlan.getId(), type);
			runPlan.setIsPostUploadEnable(Boolean.FALSE);
		}
		runPlanService.addRunPlan(runPlan);
		return statusDto;
	}

	@Async
	private void coreExecutionAsync(RunPlan runPlan, String type) throws InterruptedException {
		CamundaRequest request = new CamundaRequest();
//		request.setProjectId(runPlan.getProjectId());
//		request.setRunPlanId(runPlan.getId());
		request.setType(type.toLowerCase());
		CamundaResponse response = null;
		if (type.equalsIgnoreCase(Constants.PRE_RUN_PLAN)) {
			response = startCamundaWorkflow(request, runPlan);
			buildJenkinsJob(runPlan.getId(), type);
			runPlan.setInstanceId(response.getProcessInstanceKey());
			runPlan.setPreRunTaskId(response.getNextTask().getTaskId());
			runPlan.setIsPreUploadEnable(Boolean.FALSE);
		} else if (type.equalsIgnoreCase(Constants.POST_RUN_PLAN)) {
//			response = completeCamundaTask(request, runPlan.getInstanceId(), runPlan.getPostConfigRunTaskId());
//			runPlan.setPostRunTaskId(response.getNextTask().getTaskId());
			buildJenkinsJob(runPlan.getId(), type);
			runPlan.setIsPostUploadEnable(Boolean.FALSE);
		}
		runPlanService.addRunPlan(runPlan);
	}

	private CamundaResponse startCamundaWorkflow(CamundaRequest camundaRequest, RunPlan runPlan) {
		System.out.println("In Start Flow");
		String url = propsConfig.getCamundaHostId() + "/processes/bpmn/" + propsConfig.getCamundaBpmnProcessId()
				+ "/start";
		System.out.println("URL : " + url);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setConnection("keep-alive");
		HttpEntity<CamundaRequest> entity = new HttpEntity<CamundaRequest>(camundaRequest, headers);
		ResponseEntity<CamundaResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity,
				CamundaResponse.class);
		System.out.print("Response Received ");
		if (response.hasBody()) {
			return response.getBody();
		}
		throw new BadRequestException("Invalid Response from Camunda Server");
	}

	public StatusDto verification(String runPlanId, String type, Boolean verificationStatus) {
		RunPlan runPlan = runPlanService.findRunPlanById(runPlanId);
		if (Objects.isNull(runPlan))
			throw new BadRequestException("Run Plan Not Found");
		if (type == null)
			throw new BadRequestException("Type Not Specified");

		Project projectDetails = projectService.findProjectById(runPlan.getProjectId());
		if (Objects.isNull(projectDetails))
			throw new BadRequestException("Project Not Found");

		CamundaRequest request = new CamundaRequest();
//		request.setProjectId(runPlan.getProjectId());
//		request.setRunPlanId(runPlan.getId());
		request.setType(type);
		request.setIsBatch(projectDetails.getBatchJob());
		Long taskId = 0L;
		if (type.equalsIgnoreCase(Constants.PRE_RUN_PLAN)) {
			taskId = runPlan.getPreRunTaskId();
			request.setPreApproved(verificationStatus);
		} else if (type.equalsIgnoreCase(Constants.BATCH_RUN_PLAN)) {
			taskId = runPlan.getBatchRunTaskId();
			request.setBatchApproved(verificationStatus);
		} else if (type.equalsIgnoreCase(Constants.POST_RUN_PLAN)) {
			taskId = runPlan.getPostRunTaskId();
			request.setPostApproved(verificationStatus);
		}

		CamundaResponse response = completeCamundaTask(request, runPlan.getInstanceId(), taskId);
		if (type.equalsIgnoreCase(Constants.PRE_RUN_PLAN)) {
			if (verificationStatus) {
				runPlan.setPreRunStatus(RunPlanStatus.VERIFICATION_SUCCESS.getStatus());
				runPlan.setIsPreExecEnable(Boolean.FALSE);
			} else {
				runPlan.setIsPreUploadEnable(Boolean.TRUE);
				runPlan.setPreRunTaskId(response.getNextTask().getTaskId());
				runPlan.setPreRunStatus(RunPlanStatus.VERIFICATION_FAILURE.getStatus());
			}
			if (projectDetails.getBatchJob()) {
				runPlan.setBatchRunTaskId(response.getNextTask().getTaskId());
			} else {
				runPlan.setIsPostExecEnable(verificationStatus ? Boolean.TRUE : Boolean.FALSE);
				runPlan.setPostRunTaskId(response.getNextTask().getTaskId());
			}
		} else if (type.equalsIgnoreCase(Constants.BATCH_RUN_PLAN)) {
			if (!projectDetails.getBatchJob()) {
				throw new BadRequestException("Batch Run Not Enabled");
			}
			if (!runPlan.getPreRunStatus().equalsIgnoreCase(RunPlanStatus.VERIFICATION_SUCCESS.getStatus())) {
				throw new BadRequestException("Pre-Run Status Not Verified");
			}
			if (verificationStatus) {
				runPlan.setPostRunTaskId(response.getNextTask().getTaskId());
				runPlan.setBatchRunStatus(RunPlanStatus.VERIFICATION_SUCCESS.getStatus());
			} else {
				runPlan.setIsPreUploadEnable(Boolean.TRUE);
				runPlan.setBatchRunTaskId(response.getNextTask().getTaskId());
				runPlan.setBatchRunStatus(RunPlanStatus.VERIFICATION_FAILURE.getStatus());
			}

		} else if (type.equalsIgnoreCase(Constants.POST_RUN_PLAN)) {
			if (!runPlan.getPreRunStatus().equalsIgnoreCase(RunPlanStatus.VERIFICATION_SUCCESS.getStatus())) {
				throw new BadRequestException("Pre-Run Status Not Verified");
			}
			if (projectDetails.getBatchJob()
					&& !runPlan.getBatchRunStatus().equalsIgnoreCase(RunPlanStatus.VERIFICATION_SUCCESS.getStatus())) {
				throw new BadRequestException("Batch-Run Status Not Verified");
			}
			if (verificationStatus) {
				runPlan.setIsPostExecEnable(Boolean.FALSE);
				runPlan.setIsPostUploadEnable(Boolean.FALSE);
				runPlan.setIsPreExecEnable(Boolean.FALSE);
				runPlan.setIsPreUploadEnable(Boolean.FALSE);
				runPlan.setPostRunStatus(RunPlanStatus.VERIFICATION_SUCCESS.getStatus());
			} else {
				runPlan.setIsPostUploadEnable(Boolean.TRUE);
				runPlan.setPostRunTaskId(response.getNextTask().getTaskId());
				runPlan.setPostRunStatus(RunPlanStatus.VERIFICATION_FAILURE.getStatus());
			}
			runPlan.setStatus(Status.COMPLETED.getStatus());
		} else {
			throw new BadRequestException("No Such Type Found");
		}
		runPlanService.addRunPlan(runPlan);
		return runPlanService.checkStatus(runPlanId);
	}

	private CamundaResponse completeCamundaTask(CamundaRequest request, Long instanceId, Long taskId) {
		String url = propsConfig.getCamundaHostId() + "/processes/" + instanceId + "/task/" + taskId + "/complete";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CamundaRequest> entity = new HttpEntity<CamundaRequest>(request, headers);
		ResponseEntity<CamundaResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity,
				CamundaResponse.class);
		if (response.hasBody()) {
			return response.getBody();
		}
		throw new BadRequestException("Invalid Response from Camunda Server");
	}

	public Object generateAutomationCases(String runPlanId, String type) throws IOException {
		RunPlan runPlan = runPlanService.findRunPlanById(runPlanId);
		if (Objects.isNull(runPlan))
			throw new BadRequestException("Run Plan Not Found");
		if (type == null)
			throw new BadRequestException("Type Not Specified");

		Map<String, String> templates = fileGenService.loadTemplates();
		String baseTemplate = templates.getOrDefault("StepDefinitionsTemplate.txt", "Template not found");
		return baseTemplate.format(baseTemplate, "stepDefinitions", runPlanId, "CODE");
//		return fileGenService.formatFileContent("StepDefinitionsTemplate", "stepDefinitions", runPlanId, "CODE");

	}

}
