package com.verinite.cla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.verinite.cla.dto.ProjectDto;
import com.verinite.cla.entity.Project;
import com.verinite.cla.service.ProjectService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@PostMapping
	public Project addNewProject(@RequestBody Project project) {
		return projectService.addProject(project);
	}

	@GetMapping
	public List<Project> fetchAllProjects() {
		return projectService.findAllProject();
	}

	@GetMapping(value = "/{id}")
	public Project fetchProjectById(@PathVariable("id") String projectId) {
		return projectService.findProjectById(projectId);
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
