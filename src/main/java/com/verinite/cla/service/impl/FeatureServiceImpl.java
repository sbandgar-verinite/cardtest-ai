package com.verinite.cla.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verinite.cla.entity.Feature;
import com.verinite.cla.repository.FeatureRepository;
import com.verinite.cla.service.FeatureService;

@Service
public class FeatureServiceImpl implements FeatureService {

	@Autowired
	private FeatureRepository featureRepository;
	
	@Override
	public Feature addFeature(Feature feature) {
		return featureRepository.save(feature);
	}

	@Override
	public Feature updateFeature(Feature feature) {
		return featureRepository.save(feature);
	}

	/*
	 * @Override public Feature findFeatureById(String id) { return
	 * featureRepository.findById(id).orElse(null); }
	 */

	@Override
	public List<Feature> findAllFeature() {
		return featureRepository.findAll();
	}

	@Override
	public Feature findFeatureByCode(String featureCode) {
		return featureRepository.getFeatureByCode(featureCode);
	}

}
