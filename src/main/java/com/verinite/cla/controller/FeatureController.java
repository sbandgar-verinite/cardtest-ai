package com.verinite.cla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.verinite.cla.entity.Feature;
import com.verinite.cla.service.FeatureService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/features")
public class FeatureController {

	@Autowired
	private FeatureService featureService;
	
	@PostMapping
	public Feature addNewFeature(@RequestBody Feature feature) {
		return featureService.addFeature(feature);
	}
	
	@GetMapping
	public List<Feature> fetchAllFeatures() {
		return featureService.findAllFeature();
	}
	
	/*
	 * @RequestMapping(value = "/{id}", method=RequestMethod.GET) public Feature
	 * fetchFeatureById(@PathVariable ("id") String featureId) { return
	 * featureService.findFeatureById(featureId); }
	 */
	
	@GetMapping(value = "/{featureCode}")
	public Feature fetchFeatureByCode(@PathVariable ("featureCode") String featureCode) {
		return featureService.findFeatureByCode(featureCode);
	}
	
	@PutMapping
	public Feature updateFeature(@RequestBody Feature feature) {
		return featureService.updateFeature(feature);
	}
}
