package com.verinite.cla.service.impl;

import com.verinite.cla.entity.ReportHistory;
import com.verinite.cla.repository.ReportHistoryRepository;
import com.verinite.cla.service.ReportHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportHistoryServiceImpl implements ReportHistoryService {

    @Autowired
    private ReportHistoryRepository reportHistoryRepository;

    @Override
    public ReportHistory saveReportHistory(ReportHistory reportHistory) {
        return reportHistoryRepository.save(reportHistory);
    }

    @Override
    public List<ReportHistory> getAllReportHistories() {
        return reportHistoryRepository.findAll();
    }

    @Override
    public ReportHistory getReportHistoryById(String id) {
        return reportHistoryRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteReportHistory(String id) {
        reportHistoryRepository.deleteById(id);
    }

    @Override
    public ReportHistory updateReportHistory(String id, ReportHistory updatedReportHistory) {
        if (!reportHistoryRepository.existsById(id)) {
            return null;  
        }
        updatedReportHistory.setId(id);  
        return reportHistoryRepository.save(updatedReportHistory);
    }
}
