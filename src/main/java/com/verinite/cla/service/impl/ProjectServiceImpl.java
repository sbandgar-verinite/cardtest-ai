package com.verinite.cla.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verinite.cla.dto.ProjectDto;
import com.verinite.cla.dto.IterationDto; 
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

    @Override
    public Project addProject(ProjectDto projectDto) {
        Project project = modelMapper.map(projectDto, Project.class);
        project.setStartDate(Instant.now().toEpochMilli());

        List<Iteration> iterations = Optional.ofNullable(projectDto.getIterations())
            .orElseGet(List::of)
            .stream()
            .map(iterationDto -> {
                Iteration iteration = modelMapper.map(iterationDto, Iteration.class);
                iteration.setProject(project); 
                return iteration;
            })
            .collect(Collectors.toList());

        
        iterationRepo.saveAll(iterations);
        project.setIterations(iterations); 

        return projectRepository.save(project);
    }


    @Override
    public Project updateProject(ProjectDto project) {
        if (project != null && project.getId() != null) {
            Project proj = findProjectById(project.getId());
            if (project.getAttributes() != null) {
                proj.setAttributes(project.getAttributes());
            }
            // if (project.getBillingCycleSelectionCriteria() != null) {
            //     proj.setBillingCycleSelectionCriteria(project.getBillingCycleSelectionCriteria());
            // }
            // if (!Objects.isNull(project.getDueDays())) {
            //     proj.setDueDays(project.getDueDays());
            // }
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
            // if (project.getValidBillingCycles() != null && !project.getValidBillingCycles().isEmpty()) {
            //     proj.setValidBillingCycles(project.getValidBillingCycles());
            // }

            
            if (project.getIterations() != null) {
                List<Iteration> iterations = project.getIterations().stream()
                    .map(iterationDto -> {
                        Iteration iteration = modelMapper.map(iterationDto, Iteration.class);
                        iteration.setProject(proj); 
                        return iteration;
                    })
                    .collect(Collectors.toList());
                proj.setIterations(iterations);
            }

            return projectRepository.save(proj);
        }
        return null;
    }

    @Override
    public Project findProjectById(String id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isEmpty()) {
            throw new BadRequestException("Project not Found");
        }
        return project.get();
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