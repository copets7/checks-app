package com.yarosh.library.reports.api;

import java.util.List;

public interface ReportService<R extends Record> {

    void storeReport(List<R> records);
}
