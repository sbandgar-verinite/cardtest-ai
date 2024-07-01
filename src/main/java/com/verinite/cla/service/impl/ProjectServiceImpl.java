package com.verinite.cla.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verinite.cla.entity.Project;
import com.verinite.cla.repository.ProjectRepository;
import com.verinite.cla.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	@Override
	public Project addProject(Project project) {
		return projectRepository.save(project);
	}

	@Override
	public Project updateProject(Project project) {
		return projectRepository.save(project);
	}

	@Override
	public Project findProjectById(String id) {
		return projectRepository.findById(id).orElse(null);
	}

	@Override
	public List<Project> findAllProject() {
		return projectRepository.findAll();
	}

}
