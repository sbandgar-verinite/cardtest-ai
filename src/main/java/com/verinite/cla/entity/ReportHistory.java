package com.verinite.cla.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import java.time.LocalDate;

@Entity
@Table(name = "report_history")
public class ReportHistory {

    @Id
	@GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private String id;

    @Column(name = "sequence")
    private Long sequence;  

    @Column(name = "url")
    private String url;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "run_plan_id")  
    private String runPlanId;

    @Column(name = "type")  
    private String type;

    public ReportHistory() {
        super();
    }

    public ReportHistory(String id, Long sequence, String url, LocalDate date, String runPlanId, String type) {
        super();
        this.id = id;
        this.sequence = sequence;
        this.url = url;
        this.date = date;
        this.runPlanId = runPlanId;
        this.type = type;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


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
