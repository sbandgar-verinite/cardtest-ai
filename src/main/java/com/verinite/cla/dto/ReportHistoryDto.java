package com.verinite.cla.dto;

import jakarta.persistence.Column;

import java.time.LocalDate;

public class ReportHistoryDto {
    private Long sequence;
    private String url;

    private LocalDate date;

    private String runPlanId;

    private String type;


    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getRunPlanId() {
        return runPlanId;
    }

    public void setRunPlanId(String runPlanId) {
        this.runPlanId = runPlanId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
