package com.verinite.cla.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.verinite.cla.entity.RunData;

public interface RunDataRepository extends JpaRepository<RunData, String> {

	List<RunData> getRunDataByCode(String code);
	
	List<RunData> getRunDataByCodeAndScenarioCode(String code, String scenarioCode);

}
