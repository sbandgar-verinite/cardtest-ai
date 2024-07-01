package com.verinite.cla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.verinite.cla.entity.Feature;
import com.verinite.cla.service.FeatureService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/features")
public class FeatureController {

	@Autowired
	private FeatureService featureService;
	
	@RequestMapping(value = "/", method=RequestMethod.POST)
	public Feature addNewFeature(@RequestBody Feature feature) {
		return featureService.addFeature(feature);
	}
	
	@RequestMapping(value = "/", method=RequestMethod.GET)
	public List<Feature> fetchAllFeatures() {
		return featureService.findAllFeature();
	}
	
	/*
	 * @RequestMapping(value = "/{id}", method=RequestMethod.GET) public Feature
	 * fetchFeatureById(@PathVariable ("id") String featureId) { return
	 * featureService.findFeatureById(featureId); }
	 */
	
	@RequestMapping(value = "/{featureCode}", method=RequestMethod.GET)
	public Feature fetchFeatureByCode(@PathVariable ("featureCode") String featureCode) {
		return featureService.findFeatureByCode(featureCode);
	}
	
	@RequestMapping(value = "/", method=RequestMethod.PUT)
	public Feature updateFeature(@RequestBody Feature feature) {
		return featureService.updateFeature(feature);
	}
}
