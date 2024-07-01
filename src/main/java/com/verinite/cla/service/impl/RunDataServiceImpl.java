package com.verinite.cla.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verinite.cla.entity.RunData;
import com.verinite.cla.repository.RunDataRepository;
import com.verinite.cla.service.RunDataService;

@Service
public class RunDataServiceImpl implements RunDataService {

	@Autowired
	private RunDataRepository runDataRepository;
	
	@Override
	public RunData addRunData(RunData runData) {
		return runDataRepository.save(runData);
	}

	@Override
	public RunData updateRunData(RunData runData) {
		return runDataRepository.save(runData);
	}

	@Override
	public RunData findRunDataById(String id) {
		return runDataRepository.findById(id).orElse(null);
	}

	@Override
	public List<RunData> findAllRunData() {
		return runDataRepository.findAll();
	}

	@Override
	public List<RunData> findRunDataByCode(String code) {
		return runDataRepository.getRunDataByCode(code);
	}

}
