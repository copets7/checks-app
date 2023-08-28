package com.yarosh.library.repository.reports.local;

import com.yarosh.library.repository.reports.api.CheckRecord;
import com.yarosh.library.repository.reports.api.ReportsService;

import java.util.List;

public class CheckReportsService <R extends CheckRecord> implements ReportsService<CheckRecord> {

    private final String reportsDir;

    public CheckReportsService(String reportsDir) {
        this.reportsDir = reportsDir;
    }

    @Override
    public void storeReport(List<CheckRecord> records) {

    }

}
