package com.verinite.cla.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verinite.cla.dto.RunPlanDto;
import com.verinite.cla.dto.StatusDto;
import com.verinite.cla.entity.RunPlan;
import com.verinite.cla.repository.RunPlanRepository;
import com.verinite.cla.service.RunPlanService;
import com.verinite.cla.util.Status;
import com.verinite.commons.controlleradvice.BadRequestException;

@Service
public class RunPlanServiceImpl implements RunPlanService {

	private static final Logger logger = Logger.getLogger(RunPlanServiceImpl.class.getName());

	@Autowired
	private RunPlanRepository runPlanRepository;

	@Autowired
	private ModelMapper mapper;

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
	public List<RunPlanDto> findAllRunPlanByProjectId(String projectId) {
		List<RunPlan> runPlanList = runPlanRepository.getAllRunPlanByProjectId(projectId);
		List<RunPlanDto> runPlanDtoList = new ArrayList<>();
		for (RunPlan runPlan : runPlanList) {
			runPlanDtoList.add(mapper.map(runPlan, RunPlanDto.class));
		}
		return runPlanDtoList;
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

	@Override
	public void updateStatus(String runPlanId, String status, String url, String type) {
		Optional<RunPlan> runPlan = runPlanRepository.findById(runPlanId);
		if (runPlan.isEmpty()) {
			throw new BadRequestException("Run Plan Not Found : " + runPlanId);
		}

		logger.info("Update status for runPlanId : " + runPlanId + "type : " + type);
		if (type.equalsIgnoreCase("pre")) {
			runPlan.get().setPreRunStatus(status);
			runPlan.get().setPreReportUrl(url);
		} else if (type.equalsIgnoreCase("post")) {
			runPlan.get().setPostRunStatus(status);
			runPlan.get().setStatus(Status.COMPLETED.getStatus());
			runPlan.get().setPostReportUrl(url);
		}
		runPlanRepository.save(runPlan.get());
	}

	@Override
	public StatusDto checkStatus(String runPlanId) {
		Optional<RunPlan> runPlan = runPlanRepository.findById(runPlanId);
		if (runPlan.isEmpty()) {
			throw new BadRequestException("Run Plan Not Found : " + runPlanId);
		}
		return mapper.map(runPlan, StatusDto.class);
	}
}
