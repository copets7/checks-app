package com.yarosh.library.reports.local;

import com.opencsv.CSVWriter;
import com.yarosh.library.reports.api.CheckRecord;
import com.yarosh.library.reports.api.ReportService;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CheckReportsService implements ReportService<CheckRecord> {

    private static final String[] CSV_HEADER = {"Market", "Cashier", "Date", "Time", "Products", "Discount", "Total price"};
    private static final String NO_DISCOUNT = "";
    private static final int CSV_HEADER_INDEX = 0;

    private final String reportsDir;

    public CheckReportsService(String reportDir) {
        this.reportsDir = reportDir;
    }

    @Override
    public void storeReport(List<CheckRecord> records) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(reportsDir))) {
            final List<String[]> content = convertToCsvFormat(records);
            writer.writeAll(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String[]> convertToCsvFormat(List<CheckRecord> records) {
        final List<String[]> content = records.stream().map(this::convertToArray).collect(Collectors.toList());
        content.add(CSV_HEADER_INDEX, CSV_HEADER);
        return content;
    }

    private String[] convertToArray(CheckRecord record) {
        return new String[]{
                record.marketName(),
                record.cashierName(),
                record.date().toString(),
                record.time().toString(),
                record.products().toString(),
                record.discount().map(Object::toString).orElse(NO_DISCOUNT),
                String.valueOf(record.totalPrice())
        };
    }
}
