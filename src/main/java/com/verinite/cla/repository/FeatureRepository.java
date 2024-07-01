package com.verinite.cla.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.verinite.cla.entity.Feature;

public interface FeatureRepository extends JpaRepository<Feature, String> {

	Feature getFeatureByCode(String featureCode);

}
