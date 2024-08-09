package com.verinite.cla.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verinite.cla.entity.RunPlan;
import com.verinite.cla.exception.BadRequestException;
import com.verinite.cla.repository.RunPlanRepository;
import com.verinite.cla.service.RunPlanService;

@Service
public class RunPlanServiceImpl implements RunPlanService {

	@Autowired
	private RunPlanRepository runPlanRepository;

	@Override
	public RunPlan addRunPlan(RunPlan runPlan) {
		return runPlanRepository.save(runPlan);
	}

	@Override
	public RunPlan updateRunPlan(RunPlan runPlan) {
		return runPlanRepository.save(runPlan);
	}

	@Override
	public RunPlan findRunPlanById(String id) {
		return runPlanRepository.findById(id).orElse(null);
	}

	@Override
	public List<RunPlan> findAllRunPlan() {
		return runPlanRepository.findAll();
	}

	@Override
	public List<RunPlan> findAllRunPlanByProjectId(String projectId) {
		return runPlanRepository.getAllRunPlanByProjectId(projectId);
	}

	@Override
	public String fetchRunPlan(String runPlanId, String scenarioType) throws Exception {
		Optional<RunPlan> runPlan = runPlanRepository.findById(runPlanId);
		if (runPlan.isEmpty()) {
			throw new BadRequestException("Run Plan Not Found");
		}

		Path path = Paths.get(runPlanId + "-" + scenarioType + ".txt");
		StringBuilder htmlContent = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(byteArrayToBufferedReader(Files.readAllBytes(path)))) {
			String line;
			while ((line = reader.readLine()) != null) {
				htmlContent.append("<p>").append(line).append("</p>");
			}
		}

		return htmlContent.toString();
	}

	public static InputStreamReader byteArrayToBufferedReader(byte[] byteArray) {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
		return new InputStreamReader(byteArrayInputStream);
//		return new BufferedReader(inputStreamReader);
	}
}
