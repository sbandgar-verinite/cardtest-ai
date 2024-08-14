package com.verinite.cla.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.verinite.cla.dto.ProjectDto;
import com.verinite.cla.entity.Feature;
import com.verinite.cla.entity.Project;
import com.verinite.cla.entity.RunPlan;
import com.verinite.cla.entity.Scenario;
import com.verinite.cla.model.RunConfig;
import com.verinite.cla.model.RunScenario;
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

	@Value("${jenkins.baseUrl}")
	private String jenkinsUrl;

	@Autowired
	private ConfigurationRepository configRepo;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private ExtService extService;

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

	public void addNewProject(ProjectDto projectDto) throws ParseException {
		Project project = new Project();
		project.setName(projectDto.getName());
		project.setTenantId(projectDto.getTenantId());
		SimpleDateFormat df = new SimpleDateFormat("dd-mm-yyyy");
		if (projectDto.getStartDate() != null) {
			project.setStartDate(new Date(projectDto.getStartDate().getTime()));
		}
//		project.setValidBillingCycles(projectDto.getValidBillingCycles());
		project.setFeatures(projectDto.getFeatures());
//		project.setBillingCycleSelectionCriteria(projectDto.getBillingCycleSelectionCriteria());
//		project.setDueDays(projectDto.getDueDays());
		project.setAttributes(projectDto.getAttributes());

		project = projectService.addProject(project);

	}

	public void createRunPlanForProject(String projectId) throws ParseException {
		Project project = new Project();
		project = projectService.findProjectById(projectId);
		Optional<Config> runConf = configRepo.findByKeyName("RUN_CONFIG");
		Feature feature = new Feature();

		Multimap<Date, Map<String, RunScenario>> listOfRunScenarios = LinkedHashMultimap.create();

//		Date runStartDate = project.getStartDate();
//		Calendar runCalendar = Calendar.getInstance();
//		runCalendar.setTime(runStartDate);
//		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//		String nextStatementDateSt = df.format(runStartDate);

//		int daysFromRunStart = 0;
//		int selectedBC = 0;
//		Date nextStatementDate = null;
//		Date nextDueDate = null;

//		for (Integer billingCycle : project.getValidBillingCycles()) {
//			String formattedBC = String.format("%02d", billingCycle);
//			nextStatementDateSt = formattedBC + nextStatementDateSt.substring(2);
//			try {
//				Date dateForBC = new Date(df.parse(nextStatementDateSt).getTime());
//				if (dateForBC.before(runStartDate)) {
//					Calendar bcCalendar = Calendar.getInstance();
//					bcCalendar.setTime(dateForBC);
//					bcCalendar.add(Calendar.MONTH, 1);
//				}
//
//				int daysTillStatement = dateService.numberOfDays(runStartDate, dateForBC);
//				if (project.getBillingCycleSelectionCriteria().equals("FARTHEST")
//						& daysFromRunStart < daysTillStatement) {
//					daysFromRunStart = daysTillStatement;
//					nextStatementDate = dateForBC;
//					selectedBC = billingCycle;
//
//					Calendar dueDateCalendar = Calendar.getInstance();
//					dueDateCalendar.setTime(nextStatementDate);
//					dueDateCalendar.add(Calendar.DATE, project.getDueDays());
//					nextDueDate = new Date(dueDateCalendar.getTime().getTime());
//				}
//
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//		}

		Date startDate = project.getStartDate();
		for (String featureCode : project.getFeatures()) {

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
				Date date = calculateDate(startDate, node);
//					runCalendar.add(Calendar.DATE, (runConfig.getRunNumber().intValue() - 1));
//				}

//				if (runConfig.getRunType().equals("Statement")) {
//					if (nextStatementDate.after(runCalendar.getTime())) {
//						runCalendar.setTime(nextStatementDate);
//					} else {
//						runCalendar.setTime(nextStatementDate);
//						runCalendar.add(Calendar.MONTH, 1);
//						nextStatementDate = new Date(runCalendar.getTime().getTime());
//					}
//				}

//				if (runConfig.getRunType().equals("Due")) {
//					if (nextDueDate.after(runCalendar.getTime())) {
//						runCalendar.setTime(nextDueDate);
//					} else {
//						runCalendar.setTime(nextStatementDate);
////						runCalendar.add(Calendar.DATE, project.getDueDays());
//						nextDueDate = new Date(runCalendar.getTime().getTime());
//					}
//				}

				preRunScenario.setScenarios(runConfig.getPreRunScripts());
				featureRunScenario.put("PrerunScenarios", preRunScenario);
				postRunScenario.setScenarios(runConfig.getPostRunScripts());
				featureRunScenario.put("PostrunScenarios", postRunScenario);
//				Date date = new Date(runCalendar.getTime().getTime());
				startDate = new Date(date.getTime());
				listOfRunScenarios.put(startDate, featureRunScenario);
			}

//			runStartDate = project.getStartDate();
//			runCalendar.setTime(runStartDate);
//			String formattedBC = String.format("%02d", selectedBC);
//			nextStatementDateSt = formattedBC + nextStatementDateSt.substring(2);
//			nextStatementDate = new Date(df.parse(nextStatementDateSt).getTime());

//			if (nextStatementDate.before(runStartDate)) {
//				Calendar nextStmtCalendar = Calendar.getInstance();
//				nextStmtCalendar.setTime(nextStatementDate);
//				nextStmtCalendar.add(Calendar.MONTH, 1);
//			}

		}
		int runCounter = 0;

		for (Date runDate : listOfRunScenarios.keySet()) {
			RunPlan runPlan = new RunPlan();
			runPlan.setProjectId(project.getId());
			runPlan.setRunDate(runDate);
			runCounter++;
			runPlan.setSequenceNumber(runCounter);
			runPlan.setDescription("Run Number: #" + runCounter);
//			runPlan.setBillingCycleConsidered(selectedBC);

			Collection<Map<String, RunScenario>> runScenarios = listOfRunScenarios.get(runDate);
			List<RunScenario> prerunScenarios = new ArrayList<>();
			List<RunScenario> postrunScenarios = new ArrayList<>();

			for (Map<String, RunScenario> runScenario : runScenarios) {
				prerunScenarios.add(runScenario.get("PrerunScenarios"));
				postrunScenarios.add(runScenario.get("PostrunScenarios"));

			}

			runPlan.setPreRunScripts(prerunScenarios);
			runPlan.setPostRunScripts(postrunScenarios);

			runPlan.setStatus(Status.CREATED.getStatus());
			runPlanService.addRunPlan(runPlan);
		}
	}

	private Date calculateDate(Date startDate, JsonNode node) {
		String jsFunc = node.get("js_func").asText();
		String functionName = node.get("function_name").asText();

		StringBuilder str = new StringBuilder(jsFunc);
		str.append(functionName);
		str.append("(").append(startDate.toString()).append(");");
		String calDate = extService.executeJs(str.toString());
		return new Date(calDate);

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

	public void createFeatureFile(String tenantId, String projectId, String runPlanId) throws TemplateNotFoundException,
			MalformedTemplateNameException, freemarker.core.ParseException, IOException, TemplateException {

		RunPlan runPlan = new RunPlan();
		runPlan = runPlanService.findRunPlanById(runPlanId);

		List<RunScenario> preRunScenarios = new ArrayList<>();
		preRunScenarios = runPlan.getPreRunScripts();
		String outFeatureCode = "";

		List<RunScenario> postRunScenarios = new ArrayList<>();
		postRunScenarios = runPlan.getPostRunScripts();

		genScenarioFile(runPlan, preRunScenarios, outFeatureCode, "pre");
		genScenarioFile(runPlan, postRunScenarios, outFeatureCode, "post");

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
						"Feature: " + runScenario.getFeatureCode(), null);
			}

			String outScenarioCode = "";

			for (String scenarioCode : scenarioCodes) {
				if (!outScenarioCode.equals(scenarioCode)) {
					outScenarioCode = scenarioCode;
					String templateName = runScenario.getFeatureCode() + "-" + runPlan.getSequenceNumber() + "-"
							+ scenarioCode + "-SCENARIO";
					templateService.createRunScenarioFile(runPlan.getId() + "-" + type, templateName,
							"Scenario: " + scenarioCode, null);
				}
				Scenario scenario = scenarioService.findScenarioByCode(scenarioCode);
				Map<String, Object> featureData = new HashMap<>();
				featureData = dataService.generateData(scenario.getEntitiesRequired(), runScenario.getFeatureCode(),
						scenarioCode);

				int givenCount = 0;
				int whenCount = 0;
				int thenCount = 0;

				for (String givenTemplate : scenario.getGivenStatements()) {
					if (givenCount == 0) {
						givenCount++;
						givenTemplate = "    Given " + givenTemplate;
					} else {
						givenTemplate = "    And " + givenTemplate;
					}

					String templateName = runScenario.getFeatureCode() + "-" + runPlan.getSequenceNumber() + "-"
							+ scenarioCode + "-GIVEN";
					templateService.createRunScenarioFile(runPlan.getId() + "-" + type, templateName, givenTemplate,
							featureData);
				}
				for (String whenTemplate : scenario.getWhenConditions()) {
					if (whenCount == 0) {
						whenCount++;
						whenTemplate = "    When " + whenTemplate;
					} else {
						whenTemplate = "    And " + whenTemplate;
					}

					String templateName = runScenario.getFeatureCode() + "-" + runPlan.getSequenceNumber() + "-"
							+ scenarioCode + "-WHEN";
					templateService.createRunScenarioFile(runPlan.getId() + "-" + type, templateName, whenTemplate,
							featureData);
				}
				for (String thenTemplate : scenario.getThenOutcomes()) {
					if (thenCount == 0) {
						thenCount++;
						thenTemplate = "    Then " + thenTemplate;
					} else {
						thenTemplate = "    And " + thenTemplate;
					}
					String templateName = runScenario.getFeatureCode() + "-" + runPlan.getSequenceNumber() + "-"
							+ scenarioCode + "-THEN";
					templateService.createRunScenarioFile(runPlan.getId() + "-" + type, templateName, thenTemplate,
							featureData);
				}
			}
		}
		if (type.equalsIgnoreCase("pre")) {

		} else if (type.equalsIgnoreCase("pre")) {

		}
	}

	public StatusResponse uploadFeatureFileToGit(String runPlanId, String type) throws IOException {
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
//		headers.setBearerAuth("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");
		headers.setBearerAuth("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");
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		String baseURL = "https://api.github.com/repos/sbandgar-verinite/automation-scripts/contents/src/test/resources/features";
		ResponseEntity<List<Map<String, Object>>> responseEntity = restTemplate.exchange(baseURL, HttpMethod.GET,
				entity, new ParameterizedTypeReference<List<Map<String, Object>>>() {
				});
		List<Map<String, Object>> entities = responseEntity.getBody();

		String sha = (String) entities.get(0).get("sha");
		String url = (String) entities.get(0).get("url");
		System.out.println("SHA & URL: " + sha + " " + url);

		baseURL += "/" + runPlanId + "-" + type + ".feature?ref=main";

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
//		headers1.setBearerAuth("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");
		headers1.setBearerAuth("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");
		HttpEntity<String> entity1 = new HttpEntity<String>(bodyParam.toString(), headers1);

		ResponseEntity<Object> responseEntity1 = restTemplate.exchange(baseURL, HttpMethod.PUT, entity1,
				new ParameterizedTypeReference<Object>() {
				});

		if (responseEntity1.getStatusCode().is2xxSuccessful()) {
			return new StatusResponse("Upload Successful");
		} else {
			return new StatusResponse("Upload Failed");
		}
	}

	public StatusResponse buildJenkinsJob(String runPlanId, String type) throws InterruptedException {
		RunPlan runPlan = runPlanService.findRunPlanById(runPlanId);
		if (Objects.isNull(runPlan)) {
			throw new BadRequestException("Run Plan Not Found");
		}
		/* Issue Crumb */

		// String plainCreds = "sankha:1155b93dd2fba10d968714ad841c910c59";
		String plainCreds = "admin:0d9722b0993b43b594727e3a7c73737b";
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes, false);
		String base64Creds = new String(base64CredsBytes);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(jenkinsUrl + "/crumbIssuer/api/json",
				HttpMethod.GET, entity, new ParameterizedTypeReference<Map<String, String>>() {
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

//		ResponseEntity<Map<String,Object>> responseEntityt = restTemplate.exchange("http://localhost:8080/job/CARDTEST.AI/buildWithParameters?RUN_PLAN_ID=" + runPlanId, 
//				HttpMethod.POST, 
//				entityt, 
//				new ParameterizedTypeReference<Map<String,Object>>() {}
//		);

		ResponseEntity<String> responseEntityt = restTemplate.exchange(
				jenkinsUrl + "/job/CARDTEST.AI/buildWithParameters?RUN_PLAN_ID=" + runPlanId + "&SCENARIO_TYPE=" + type,
				HttpMethod.POST, entityt, String.class);

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
		return new StatusResponse(Status.INPROGRESS.name());
	}
}
