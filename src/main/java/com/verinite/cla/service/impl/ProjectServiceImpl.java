package com.verinite.cla.service.impl;

import java.time.Instant;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verinite.cla.dto.ProjectDto;
import com.verinite.cla.entity.Project;
import com.verinite.cla.repository.ProjectRepository;
import com.verinite.cla.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Project addProject(Project project) {
		project.setStartDate(Instant.now().toEpochMilli());
		return projectRepository.save(project);
	}

	@Override
	public Project updateProject(ProjectDto project) {
		if (project != null && project.getId() != null) {
			Project proj = findProjectById(project.getId());
			if (project.getAttributes() != null) {
				proj.setAttributes(project.getAttributes());
			}
//			if (project.getBillingCycleSelectionCriteria() != null) {
//				proj.setBillingCycleSelectionCriteria(project.getBillingCycleSelectionCriteria());
//			}
//			if (!Objects.isNull(project.getDueDays())) {
//				proj.setDueDays(project.getDueDays());
//			}
			if (project.getFeatures() != null) {
				proj.setFeatures(project.getFeatures());
			}
			if (project.getName() != null) {
				proj.setName(project.getName());
			}
			if (project.getStartDate() != null) {
				proj.setStartDate(project.getStartDate());
			}
			if (project.getTenantId() != null) {
				proj.setTenantId(project.getTenantId());
			}
//			if (project.getValidBillingCycles() != null && !project.getValidBillingCycles().isEmpty()) {
//				proj.setValidBillingCycles(project.getValidBillingCycles());
//			}
			return projectRepository.save(proj);
		}
		return null;
	}

	@Override
	public Project findProjectById(String id) {
		return projectRepository.findById(id).orElse(null);
	}

	@Override
	public List<Project> findAllProject() {
		return projectRepository.findAll();
	}

	@Override
	public void deleteProject(String id) {
		projectRepository.deleteById(id);
	}

}
