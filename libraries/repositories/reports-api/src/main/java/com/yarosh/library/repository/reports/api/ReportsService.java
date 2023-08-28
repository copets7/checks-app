package com.yarosh.library.repository.reports.api;

import java.util.List;

public interface ReportsService<R extends Record> {

    void storeReport(List<R> records);
}
