package com.verinite.cla.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.verinite.cla.entity.RunPlan;

public interface RunPlanRepository extends JpaRepository<RunPlan, String> {

	List<RunPlan> getAllRunPlanByProjectId(String featureCode);

	List<RunPlan> findByProjectId(String projectId);
}
