package com.verinite.cla.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.verinite.cla.entity.Defect;
import com.verinite.cla.repository.DefectRepository;
import com.verinite.cla.service.ExternalService;
import com.verinite.commons.controlleradvice.BadRequestException;

@Service
public class ExternalServiceImpl implements ExternalService {

	@Autowired
	private DefectRepository defectRepository;

	@Override
	public void storeDefect(JsonNode defectDetails) {
		Defect defect = new Defect();
		Boolean missing = Boolean.FALSE;
		if (defectDetails.get("test_case_id").asText() != null) {
			defect.setCaseId(defectDetails.get("test_case_id").asText());
			missing = Boolean.TRUE;
		}
		if (defectDetails.get("case_module").asText() != null) {
			defect.setCaseModule(defectDetails.get("case_module").asText());
			missing = Boolean.TRUE;
		}
		if (defectDetails.get("defect_id").asText() != null) {
			defect.setDefectId(defectDetails.get("defect_id").asText());
			missing = Boolean.TRUE;
		}
		if (defectDetails.get("severity").asText() != null) {
			defect.setSeverity(defectDetails.get("severity").asText());
			missing = Boolean.TRUE;
		}
		if (defectDetails.get("details").asText() != null) {
			defect.setDetails(defectDetails.get("details").asText());
			missing = Boolean.TRUE;
		}
		if (missing) {
			throw new BadRequestException("Defect Information Missing");
		}
		defectRepository.save(defect);
	}
}
