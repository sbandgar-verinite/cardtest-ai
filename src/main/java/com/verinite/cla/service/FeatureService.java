package com.verinite.cla.service;

import java.util.List;

import com.verinite.cla.entity.Feature;


public interface FeatureService {

	public Feature addFeature(Feature feature);
	
	public Feature updateFeature(Feature feature);
	
	//public Feature findFeatureById(String id);
		
	public List<Feature> findAllFeature();
	
	public Feature findFeatureByCode(String featureCode);
}
