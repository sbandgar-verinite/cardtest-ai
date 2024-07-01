package com.verinite.cla.service;

import java.util.List;

import com.verinite.cla.entity.Project;


public interface ProjectService {

	public Project addProject(Project project);
	
	public Project updateProject(Project project);
	
	public Project findProjectById(String id);
	
	public List<Project> findAllProject();
}
