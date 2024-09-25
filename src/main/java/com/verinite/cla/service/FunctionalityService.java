package com.verinite.cla.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.verinite.cla.dto.FunctionalityDto;
import com.verinite.cla.model.Functionality;
import com.verinite.commons.controlleradvice.BadRequestException;

public interface FunctionalityService {

	void updateFunctionality(FunctionalityDto functDto) throws BadRequestException;

	Functionality findFunctionalityById(String id) throws BadRequestException;

	List<FunctionalityDto> findAllFunctionality();

	List<Functionality> findByTag(String tag);

	void deleteFunctionalityById(String id);

	void addFunctionality(FunctionalityDto functDto);

	void add(JsonNode details);

}
