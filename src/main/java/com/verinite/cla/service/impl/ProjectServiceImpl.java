package com.verinite.cla.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.verinite.cla.entity.RunPlan;
import com.verinite.cla.service.SetupService;
import com.verinite.commons.dto.StatusResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.verinite.cla.dto.IterationDto;
import com.verinite.cla.dto.ProjectDto;
import com.verinite.cla.entity.Iteration;
import com.verinite.cla.entity.Project;
import com.verinite.cla.repository.IterationRepository;
import com.verinite.cla.repository.ProjectRepository;
import com.verinite.cla.service.ProjectService;
import com.verinite.commons.controlleradvice.BadRequestException;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private IterationRepository iterationRepo;

	@Autowired
	@Lazy
	SetupService setupService;

	@Override
	public Project addProject(ProjectDto projectDto) {
		Project project = modelMapper.map(projectDto, Project.class);
		project.setStartDate(Instant.now().toEpochMilli());

		List<Iteration> iterations = Optional.ofNullable(projectDto.getIterations()).orElseGet(List::of).stream()
				.map(iterationDto -> {
					Iteration iteration = modelMapper.map(iterationDto, Iteration.class);
					iteration.setProject(project);
					return iteration;
				}).collect(Collectors.toList());

		project.setIterations(iterations);
		return projectRepository.save(project);
	}

	@Override
	public Project updateProject(String id, ProjectDto projectDto) {
		if (projectDto == null || id == null) {
			throw new BadRequestException("Project Not Found");
		}

		Project proj = findProjectById(id);

		if (projectDto.getName() != null) {
			proj.setName(projectDto.getName());
		}
//		if (projectDto.getTenantId() != null) {
//			proj.setTenantId(projectDto.getTenantId());
//		}
//		if (projectDto.getStartDate() != null) {
//			proj.setStartDate(projectDto.getStartDate());
//		}
		if (projectDto.getBatchJob() != null) {
			proj.setBatchJob(projectDto.getBatchJob());
		}
		if (projectDto.getIsFlowAuto() != null) {
			proj.setIsFlowAuto(projectDto.getIsFlowAuto());
		}
		if (projectDto.getFeatures() != null) {
			proj.setFeatures(projectDto.getFeatures());
		}
		if (projectDto.getAttributes() != null) {
			proj.setAttributes(projectDto.getAttributes());
		}

		// if (project.getBillingCycleSelectionCriteria() != null) {
		// proj.setBillingCycleSelectionCriteria(project.getBillingCycleSelectionCriteria());
		// }
		// if (!Objects.isNull(project.getDueDays())) {
		// proj.setDueDays(project.getDueDays());
		// }

		// if (project.getValidBillingCycles() != null &&
		// !project.getValidBillingCycles().isEmpty()) {
		// proj.setValidBillingCycles(project.getValidBillingCycles());
		// }

//		List<Iteration> iterationList = new ArrayList<>();
//		if (projectDto.getIterations() != null) {
//		//	proj.getIterations().clear();
//			iterationList = projectDto.getIterations().stream().map(iterationDto -> {
//				Iteration iteration = modelMapper.map(iterationDto, Iteration.class);
//				iteration.setProject(proj);
//				return iteration;
//			}).collect(Collectors.toList());
//			//List<Iteration> iterationList1 = iterationRepo.saveAll(iterationList);
//			proj.setIterations(iterationList);
//		}
//		
		
		List<IterationDto> iterationListNew = projectDto.getIterations();
		List<Iteration> existingIterartionList = proj.getIterations();
		List<Iteration> iterationList = new ArrayList<>();

		if (!existingIterartionList.isEmpty()) {
			for (IterationDto iteration : iterationListNew) {
				if(iteration.getId() == null)
				{
					Iteration it = modelMapper.map(iteration, Iteration.class);
					it.setGenerated(Boolean.FALSE);
					it.setProject(proj);
					iterationList.add(it);
					continue;
				}
				for(Iteration existingIteration : existingIterartionList)
				{
					existingIteration.setGenerated(Boolean.TRUE);
					if(iteration.getId().equalsIgnoreCase(existingIteration.getId()))
					{
						iteration.setGeneraeted(Boolean.TRUE);
						break;
					}
				}
				Iteration existingIteration = iterationRepo.getById(iteration.getId());
				existingIteration.setProject(proj);
				existingIteration.setFeatures(iteration.getFeatures());
				existingIteration.setGenerated(Boolean.TRUE);
				existingIteration.setSequence(iteration.getSequence());
				iterationList.add(existingIteration);
			}
			proj.setIterations(iterationList);
		}
		else
		{	
			for(IterationDto iteration : iterationListNew)
			{
				Iteration it = modelMapper.map(iteration, Iteration.class);
				it.setGenerated(Boolean.FALSE);
				it.setProject(proj);
				iterationList.add(it);
			}
			proj.setIterations(iterationList);
		}
		projectRepository.save(proj);
		proj.setIterations(iterationList);

		try {
			StatusResponse runPlanForProject = setupService.createRunPlanForProject(proj);
			if (runPlanForProject.getStatus().equalsIgnoreCase("Success")) {
				System.out.println("Runplan Created for New Iterations");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

//		if(projectDto.getRemoveIterationSequence() ! == null) {
//			List<Integer> sequencesToRemove = projectDto.getRemoveIterationSequences();
//			proj.setIterations(proj.setIteration);
//		}
		
		

		return findProjectById(id);
	}
	
	

	@Override
	public Project findProjectById(String id) {
		Optional<Project> project = projectRepository.findById(id);
		if (project.isEmpty()) {
			throw new BadRequestException("Project not Found");
		}
		return project.get();
	}

//	@Override
//	public ProjectDto findProjectById(String id) {
//	    Optional<Project> project = projectRepository.findById(id);
//	    if (project.isEmpty()) {
//	        throw new BadRequestException("Project not Found");
//	    }
//
//	    ProjectDto projectDto = modelMapper.map(project.get(), ProjectDto.class);
//
//	    List<IterationDto> iterationDtos = project.get().getIterations().stream()
//	            .map(iteration -> modelMapper.map(iteration, IterationDto.class))
//	            .collect(Collectors.toList());
//	    projectDto.setIterations(iterationDtos);
//	    
//	    return projectDto;
//	}

	@Override
	public List<ProjectDto> findAllProject() {
		List<Project> projects = projectRepository.findAll();
		return projects.stream().map(project -> {
			ProjectDto projectDto = modelMapper.map(project, ProjectDto.class);
			List<IterationDto> iterationDtos = project.getIterations().stream()
					.map(iteration -> modelMapper.map(iteration, IterationDto.class)).collect(Collectors.toList());
			projectDto.setIterations(iterationDtos);
			return projectDto;
		}).collect(Collectors.toList());
	}

	@Override
	public void deleteProject(String id) {
		projectRepository.deleteById(id);
	}

}