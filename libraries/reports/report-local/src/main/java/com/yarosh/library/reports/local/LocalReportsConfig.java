package com.yarosh.library.reports.local;

import com.yarosh.library.reports.api.CheckRecord;
import com.yarosh.library.reports.api.ReportService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocalReportsConfig {

    @Bean
    public ReportService<CheckRecord> checkReportsService(@Value("${reports.directory}") String reportsDir) {
        return new CheckReportsService(reportsDir);
    }
}
