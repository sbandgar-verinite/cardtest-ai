package com.verinite.cla.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.verinite.cla.dto.RunPlanDto;
import com.verinite.cla.dto.StatusDto;
import com.verinite.cla.service.RunPlanService;
import com.verinite.cla.service.SetupService;
import com.verinite.commons.dto.StatusResponse;

import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class SetupController {

	@Autowired
	private SetupService setupService;

	@Autowired
	private RunPlanService runPlanService;

//	@RequestMapping(value = "/tenants", method = RequestMethod.POST)
//	public String setupNewTenant(TenantDto tenant) {
//		setupService.addNewTenant(tenant);
//		return "";
//	}
//
//	@RequestMapping(value = "/tenants", method = RequestMethod.GET)
//	public String getAllTenant() {
//		setupService.getAllTenants();
//		return "";
//	}
//
//	@RequestMapping(value = "/tenants/{tenantId}", method = RequestMethod.GET)
//	public String getTenantById(@PathVariable String tenantId) {
//		setupService.getTenantById(tenantId);
//		return "";
//	}
//
//	@RequestMapping(value = "/tenants/{tenantId}/projects", method = RequestMethod.POST)
//	public String addNewProject(@PathVariable String tenantId, @RequestBody ProjectDto project) throws ParseException {
//		setupService.addNewProject(project);
//		return "";
//	}

	@PostMapping(value = "/projects/{projectId}/runplans")
	public StatusResponse createRunPLan(@PathVariable String projectId) throws ParseException {
		return setupService.createRunPlanForProject(projectId);
	}

	@GetMapping(value = "/projects/{projectId}/runplans")
	public List<RunPlanDto> getAllRunPLan(@PathVariable String projectId) throws ParseException {
		return runPlanService.findAllRunPlanByProjectId(projectId);
	}

	@PostMapping(value = "/projects/{projectId}/runplans/{runPlanId}/feature-files")
	public ResponseEntity<StatusResponse> createFeatureFileForCurrentRun(@PathVariable String projectId,
			@PathVariable String runPlanId) throws TemplateNotFoundException, MalformedTemplateNameException,
			freemarker.core.ParseException, IOException, TemplateException {
		return ResponseEntity.ok(setupService.createFeatureFile(projectId, runPlanId));
	}

	@GetMapping(value = "/projects/{projectId}/runplans/{runPlanId}/feature-files/upload-git")
	public ResponseEntity<Object> uploadFeatureFileForCurrentRunToGit(@PathVariable String projectId,
			@PathVariable String runPlanId, @RequestParam String type) throws TemplateNotFoundException,
			MalformedTemplateNameException, freemarker.core.ParseException, IOException, TemplateException {
		return ResponseEntity.ok(setupService.uploadFeatureFileToGit(runPlanId, type));
	}

	@GetMapping(value = "/projects/{projectId}/runplans/{runPlanId}/feature-files/build")
	public ResponseEntity<StatusDto> triggerRunForCurrentFeatureFile(@PathVariable String projectId,
			@PathVariable String runPlanId, @RequestParam String type) throws InterruptedException {
		return ResponseEntity.ok(setupService.buildJenkinsJob(runPlanId, type));
	}

	@GetMapping(value = "/runplans/{runPlanId}/execution")
	public ResponseEntity<StatusDto> execution(@PathVariable String runPlanId, @RequestParam String type)
			throws InterruptedException {
		return ResponseEntity.ok(setupService.execution(runPlanId, type));
	}

	@GetMapping(value = "/runplans/{runPlanId}/verification")
	public ResponseEntity<StatusDto> verification(@PathVariable String runPlanId, @RequestParam String type,
			@RequestParam Boolean verificationStatus) {
		return ResponseEntity.ok(setupService.verification(runPlanId, type, verificationStatus));
	}

}
