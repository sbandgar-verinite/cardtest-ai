package com.verinite.cla.service;

import java.util.List;

import com.verinite.cla.entity.RunData;


public interface RunDataService {

	public RunData addRunData(RunData runData);
	
	public RunData updateRunData(RunData runData);
	
	public RunData findRunDataById(String id);
	
	public List<RunData> findAllRunData();
	
	public List<RunData> findRunDataByCode(String code);
}
