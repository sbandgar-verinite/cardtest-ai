package com.verinite.cla.service;

import java.util.List;

import com.verinite.cla.entity.Defect;

public interface DefectService {

	public Defect createDefect(Defect defect);
	
	public Defect getDefectById(String id);
	
	public Defect updateDefect(Defect defect);
	
	public List<Defect>getAllDefects();
	
	public void deleteDefect(String id);
}
