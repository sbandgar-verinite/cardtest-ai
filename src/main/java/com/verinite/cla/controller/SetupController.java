package com.verinite.cla.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.verinite.cla.dto.ProjectDto;
import com.verinite.cla.dto.TenantDto;
import com.verinite.cla.entity.RunPlan;
import com.verinite.cla.service.RunPlanService;
import com.verinite.cla.service.SetupService;
import com.verinite.commons.dto.StatusResponse;

import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/v1")
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

	@RequestMapping(value = "/tenants/{tenantId}/projects", method = RequestMethod.POST)
	public String addNewProject(@PathVariable String tenantId, @RequestBody ProjectDto project) throws ParseException {
		setupService.addNewProject(project);
		return "";
	}

	@RequestMapping(value = "/tenants/{tenantId}/projects/{projectId}/runplans", method = RequestMethod.POST)
	public String createRunPLan(@PathVariable String tenantId, @PathVariable String projectId) throws ParseException {
		setupService.createRunPlanForProject(projectId);
		return "";
	}

	@RequestMapping(value = "/tenants/{tenantId}/projects/{projectId}/runplans", method = RequestMethod.GET)
	public List<RunPlan> getAllRunPLan(@PathVariable String tenantId, @PathVariable String projectId)
			throws ParseException {
		return runPlanService.findAllRunPlanByProjectId(projectId);
	}

	@RequestMapping(value = "/tenants/{tenantId}/projects/{projectId}/runplans/{runPlanId}/feature-files", method = RequestMethod.POST)
	public String createFeatureFileForCurrentRun(@PathVariable String tenantId, @PathVariable String projectId,
			@PathVariable String runPlanId) throws TemplateNotFoundException, MalformedTemplateNameException,
			freemarker.core.ParseException, IOException, TemplateException {
		setupService.createFeatureFile(tenantId, projectId, runPlanId);
		return "Success";
	}

	@RequestMapping(value = "/tenants/{tenantId}/projects/{projectId}/runplans/{runPlanId}/feature-files/upload-git", method = RequestMethod.GET)
	public StatusResponse uploadFeatureFileForCurrentRunToGit(@PathVariable String tenantId, @PathVariable String projectId,
			@PathVariable String runPlanId, @RequestParam String type) throws TemplateNotFoundException,
			MalformedTemplateNameException, freemarker.core.ParseException, IOException, TemplateException {
		return setupService.uploadFeatureFileToGit(runPlanId, type);
	}

	@RequestMapping(value = "/tenants/{tenantId}/projects/{projectId}/runplans/{runPlanId}/feature-files/build", method = RequestMethod.GET)
	public StatusResponse triggerRunForCurrentFeatureFile(@PathVariable String tenantId, @PathVariable String projectId,
			@PathVariable String runPlanId, @RequestParam String type) throws InterruptedException {
		return setupService.buildJenkinsJob(runPlanId, type);
	}

}
