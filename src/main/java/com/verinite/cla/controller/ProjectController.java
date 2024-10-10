package com.verinite.cla.controller;

import java.text.ParseException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.verinite.cla.dto.IterationDto;
import com.verinite.cla.dto.ProjectDto;
import com.verinite.cla.entity.Project;
import com.verinite.cla.service.ProjectService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping
    public Project addNewProject(@RequestBody ProjectDto projectDto) throws ParseException {
        Project project = projectService.addProject(projectDto);
        return project;
    }

//    @GetMapping
//    public List<ProjectDto> fetchAllProjects() {
//        List<ProjectDto> projects = projectService.findAllProject();
//        return projects.stream().map(project -> {
//            ProjectDto projectDto = mapper.map(project, ProjectDto.class);
//            List<IterationDto> iterationDtos = project.getIterations().stream()
//                .map(iteration -> mapper.map(iteration, IterationDto.class))
//                .sorted((iteration1, iteration2) -> iteration1.getSequence().compareTo(iteration2.getSequence()))
//                .collect(Collectors.toList());
//            projectDto.setIterations(iterationDtos);
//            projectDto.setAttributes(project.getAttributes()); 
//            return projectDto;
//        }).collect(Collectors.toList());
//    }
//  
    
    @GetMapping
    public List<ProjectDto> fetchAllProjects() {
        List<ProjectDto> projects = projectService.findAllProject();
        return projects.stream().map(project -> {
            ProjectDto projectDto = mapper.map(project, ProjectDto.class);
            
            List<IterationDto> iterationDtos = project.getIterations().stream()
                .map(iteration -> mapper.map(iteration, IterationDto.class))
                .sorted(Comparator.comparing(IterationDto::getSequence, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());
            
            projectDto.setIterations(iterationDtos);
            projectDto.setAttributes(project.getAttributes());
            return projectDto;
        }).collect(Collectors.toList());
    }

  
    @GetMapping(value = "/{id}")
    public ProjectDto fetchProjectById(@PathVariable("id") String projectId) {
        Project project = projectService.findProjectById(projectId);
        ProjectDto projectDto = mapper.map(project, ProjectDto.class);
        
        List<IterationDto> iterationDtos = project.getIterations().stream()
            .map(iteration -> mapper.map(iteration, IterationDto.class))
            .sorted((iteration1, iteration2) -> iteration1.getSequence().compareTo(iteration2.getSequence()))
            .collect(Collectors.toList());
            
        projectDto.setIterations(iterationDtos);
        projectDto.setAttributes(project.getAttributes());
        
        return projectDto;
    }
     
  
    @PutMapping("/{id}")
    public Project updateProject(@PathVariable String id, @RequestBody ProjectDto projectDto) {
        return projectService.updateProject(id, projectDto);
        
//        if (updatedProject == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found");
//        }

//        ProjectDto updatedProjectDto = mapper.map(updatedProject, ProjectDto.class);
//        List<IterationDto> iterationDtos = updatedProject.getIterations().stream()
//            .map(iteration -> mapper.map(iteration, IterationDto.class))
//            .collect(Collectors.toList());
//        updatedProjectDto.setIterations(iterationDtos);
//        updatedProjectDto.setAttributes(updatedProject.getAttributes()); 
//        return updatedProjectDto;
    }

  
    @PutMapping
    public Project updateProject(@RequestBody ProjectDto projectDto) {
        return projectService.updateProject(projectDto.getId(), projectDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
    }
}


