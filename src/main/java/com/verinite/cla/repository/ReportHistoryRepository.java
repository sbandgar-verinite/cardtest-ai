package com.verinite.cla.repository;

import java.util.List;

import com.verinite.cla.dto.ReportHistoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import com.verinite.cla.entity.ReportHistory;

@Repository
public interface ReportHistoryRepository extends JpaRepository<ReportHistory, String> {
	
//	public List<ReportHistory> findByRunPlanId(String runPlanId);
	@Query("select count(r) from ReportHistory r where r.runPlanId = :runPlanId")
	Long countByRunPlanId(@Param("runPlanId") String runPlanId);


	List<ReportHistory> findAllByRunPlanId(String runplanId);
}
