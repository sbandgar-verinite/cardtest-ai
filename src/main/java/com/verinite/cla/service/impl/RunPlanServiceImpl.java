package com.verinite.cla.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.verinite.cla.dto.RunPlanDto;
import com.verinite.cla.dto.StatusDto;
import com.verinite.cla.entity.RunPlan;
import com.verinite.cla.repository.RunPlanRepository;
import com.verinite.cla.service.ProjectService;
import com.verinite.cla.service.RunPlanService;
import com.verinite.commons.controlleradvice.BadRequestException;

@Service
public class RunPlanServiceImpl implements RunPlanService {

	private static final Logger logger = Logger.getLogger(RunPlanServiceImpl.class.getName());

	@Autowired
	private RunPlanRepository runPlanRepository;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private ProjectService projService;

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
		Optional<RunPlan> runPlan = runPlanRepository.findById(id);
		if (runPlan.isEmpty()) {
			throw new BadRequestException("RunPlan Not Found");
		}
		return runPlan.get();
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
		htmlContent.append("<html><body>");
		try (BufferedReader reader = new BufferedReader(byteArrayToBufferedReader(Files.readAllBytes(path)))) {
			String line;
			while ((line = reader.readLine()) != null) {
				// Apply color formatting to Gherkin keywords (Case-sensitive) with dark colors,
				// font size, and weight
				line = line.replaceAll("<", "&lt;") // Replace '<' with HTML entity
						.replaceAll(">", "&gt;") // Replace '>' with HTML entity
						.replaceAll("(?<!\\S)(Feature:)(?!\\S)",
								"<span style='color: #8e44ad; font-weight: 500; font-size: 16px;'>$1</span>") // Dark
																												// Blue
						.replaceAll("(?<!\\S)(Scenario Outline:)(?!\\S)",
								"<span style='color: #52be80; font-weight: 500; font-size: 16px;'>$1</span>") // Dark
																												// Green
						.replaceAll("(?<!\\S)(Examples:)(?!\\S)",
								"<span style='color: #e74c3c; font-weight: 500; font-size: 16px;'>$1</span>") // Dark
																												// Red
						.replaceAll("(?<!\\S)(Given|When|Then|And|But)(?!\\S)",
								"<span style='color: #f39c12; font-weight: 500; font-size: 16px;'>$1</span>"); // Dark
																												// Yellow
				htmlContent.append("<p>").append(line).append("</p>");
			}
		}
		htmlContent.append("</body></html>");
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

	@Override
	public List<RunPlanDto> findRunPlanByProjectIdAndIterationId(String projectId, Integer iterationId) {
		projService.findProjectById(projectId);
		List<RunPlan> runPlans = runPlanRepository.findByProjectId(projectId);
		if (!CollectionUtils.isEmpty(runPlans)) {
			if (Objects.isNull(iterationId) || iterationId <= 0) {
				return runPlans.stream().map(x -> mapper.map(x, RunPlanDto.class))
						.sorted((x, y) -> x.getSequenceNumber() - y.getSequenceNumber()).toList();
			} else {
				return runPlans.stream().filter(x -> x.getItnSeq().equals(iterationId))
						.map(x -> mapper.map(x, RunPlanDto.class))
						.sorted((x, y) -> x.getSequenceNumber() - y.getSequenceNumber()).toList();
			}
		}
		return new ArrayList<>();
	}
}
