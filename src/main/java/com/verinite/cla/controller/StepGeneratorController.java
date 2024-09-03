package com.verinite.cla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.verinite.cla.service.impl.StepDefinitionGeneratorService;
import com.verinite.commons.dto.StatusResponse;

@RestController
public class StepGeneratorController {

	@Autowired
	StepDefinitionGeneratorService generatorService;
	
    @GetMapping("/{fileName}/generate/steps")
    public StatusResponse stepGeneration(@PathVariable String fileName) throws Exception {
            return generatorService.uploadFeatureFileToGit(fileName);
    }
	
}
