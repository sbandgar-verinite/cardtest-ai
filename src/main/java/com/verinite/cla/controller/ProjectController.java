package com.verinite.cla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<Project> fetchAllProjects() {
		return projectService.findAllProject();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Project fetchProjectById(@PathVariable("id") String projectId) {
		return projectService.findProjectById(projectId);
	}

	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public Project updateProject(@RequestBody Project project) {
		return projectService.updateProject(project);
	}

	@DeleteMapping("/project/{id}")
	public void deleteProject(@PathVariable String id) {
		projectService.deleteProject(id);
	}

}
