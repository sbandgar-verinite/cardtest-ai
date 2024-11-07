package com.verinite.cla.service;

import com.verinite.cla.entity.ReportHistory;

import java.util.List;

public interface ReportHistoryService {
    ReportHistory saveReportHistory(ReportHistory reportHistory);
    List<ReportHistory> getAllReportHistories();
    ReportHistory getReportHistoryById(String id);
    void deleteReportHistory(String id);
    ReportHistory updateReportHistory(String id, ReportHistory reportHistory);  
}
