package com.verinite.cla.controller;

import com.verinite.cla.entity.ReportHistory;
import com.verinite.cla.service.ReportHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report-history")
public class ReportHistoryController {

    @Autowired
    private ReportHistoryService reportHistoryService;

    @PostMapping
    public ResponseEntity<ReportHistory> createReportHistory(@RequestBody ReportHistory reportHistory) {
        ReportHistory created = reportHistoryService.saveReportHistory(reportHistory);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<ReportHistory>> getAllReportHistories() {
        List<ReportHistory> reportHistories = reportHistoryService.getAllReportHistories();
        return ResponseEntity.ok(reportHistories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportHistory> getReportHistoryById(@PathVariable String id) {
        ReportHistory reportHistory = reportHistoryService.getReportHistoryById(id);
        return reportHistory != null ? ResponseEntity.ok(reportHistory) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReportHistory> updateReportHistory(@PathVariable String id, @RequestBody ReportHistory updatedReportHistory) {
        ReportHistory existingReportHistory = reportHistoryService.getReportHistoryById(id);
        if (existingReportHistory == null) {
            return ResponseEntity.notFound().build();
        }
        updatedReportHistory.setId(id); 
        ReportHistory savedReportHistory = reportHistoryService.saveReportHistory(updatedReportHistory);
        return ResponseEntity.ok(savedReportHistory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReportHistory(@PathVariable String id) {
        reportHistoryService.deleteReportHistory(id);
        return ResponseEntity.noContent().build();
    }
}
