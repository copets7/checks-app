package com.yarosh.library.reports.local;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReportsConfig {

    @Bean
    public CheckReportsService checkReportsService(@Value("${reports.directory}")String reportsDir) {
        return new CheckReportsService(reportsDir);
    }
}
