package com.verinite.cla.controller;

import java.text.ParseException;
import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.verinite.cla.config.Constants;
import com.verinite.cla.dto.FunctionalityDto;
import com.verinite.cla.model.Functionality;
import com.verinite.cla.service.FunctionalityService;
import com.verinite.commons.dto.StatusResponse;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/repo")
public class FunctionalityController {

	@Autowired
	private FunctionalityService functionalityService;

	@PostMapping
	public StatusResponse addNewFunctionality(@RequestBody FunctionalityDto functDto) throws ParseException {
		functionalityService.addFunctionality(functDto);
		return new StatusResponse(Constants.SUCCESS, HttpStatus.OK.value(), "Functionality Created Successfully");
	}

	@PatchMapping
	public StatusResponse updateExistingFunctionality(@RequestBody FunctionalityDto functDto)
			throws ParseException, BadRequestException {
		functionalityService.updateFunctionality(functDto);
		return new StatusResponse(Constants.SUCCESS, HttpStatus.OK.value(), "Functionality Updated Successfully");
	}

	@GetMapping
	public List<FunctionalityDto> fetchAllFunctionalitys() {
		return functionalityService.findAllFunctionality();
	}

	@GetMapping(value = "/{id}")
	public Functionality findFunctionalityById(@PathVariable String id) throws BadRequestException {
		return functionalityService.findFunctionalityById(id);
	}

	@GetMapping(value = "/tag")
	public List<Functionality> findByTag(@RequestParam String tag) {
		return functionalityService.findByTag(tag);
	}

	@DeleteMapping("/{id}")
	public void deleteFunctionality(@PathVariable String id) {
		functionalityService.deleteFunctionalityById(id);
	}

	@PostMapping("/json")
	public StatusResponse add(@RequestBody JsonNode details) throws ParseException {
		functionalityService.add(details);
		return new StatusResponse(Constants.SUCCESS, HttpStatus.OK.value(), "Functionality Created Successfully");
	}
}
