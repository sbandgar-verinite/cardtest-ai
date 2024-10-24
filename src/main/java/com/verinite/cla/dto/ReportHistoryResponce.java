package com.verinite.cla.dto;

import java.util.List;

public class ReportHistoryResponce {

    List<ReportHistoryDto> preReports ;

    List<ReportHistoryDto> postReports ;

    public List<ReportHistoryDto> getPreReports() {
        return preReports;
    }

    public void setPreReports(List<ReportHistoryDto> preReports) {
        this.preReports = preReports;
    }

    public List<ReportHistoryDto> getPostReports() {
        return postReports;
    }

    public void setPostReports(List<ReportHistoryDto> postReports) {
        this.postReports = postReports;
    }
}
