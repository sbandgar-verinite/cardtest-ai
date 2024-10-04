package com.verinite.cla.controller;

import java.text.ParseException;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.verinite.cla.dto.ProjectDto;
import com.verinite.cla.entity.Project;
import com.verinite.cla.service.ProjectService;
import com.verinite.cla.service.RunDataService;
import com.verinite.cla.service.SetupService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private SetupService setupService;

	@Autowired
	private RunDataService runDataService;

	@Autowired
	private ModelMapper mapper;
	
	@PostMapping
	public Project addNewProject(@RequestBody ProjectDto projectDto) throws ParseException {
		Project project = projectService.addProject(projectDto);
		setupService.createRunPlanForProject(project);
		runDataService.generateData(project.getId());
		return project;
	}

	@GetMapping
	public List<Project> fetchAllProjects() {
		return projectService.findAllProject();
	}

	@GetMapping(value = "/{id}")
	public ProjectDto fetchProjectById(@PathVariable("id") String projectId) {
		Project project = projectService.findProjectById(projectId);
		ProjectDto projectDto = mapper.map(project, ProjectDto.class);
		return projectDto;
	}

	@PutMapping
	public Project updateProject(@RequestBody ProjectDto project) {
		return projectService.updateProject(project);
	}

	@DeleteMapping("/project/{id}")
	public void deleteProject(@PathVariable String id) {
		projectService.deleteProject(id);
	}

}
