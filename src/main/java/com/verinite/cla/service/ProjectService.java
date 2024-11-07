package com.verinite.cla.service;

import java.util.List;

import com.verinite.cla.dto.ProjectDto;
import com.verinite.cla.entity.Project;

public interface ProjectService {

	public Project addProject(ProjectDto projectDto);

	public Project updateProject(String id, ProjectDto project);

	public Project findProjectById(String id);

	public List<ProjectDto> findAllProject();

	public void deleteProject(String id);
}
