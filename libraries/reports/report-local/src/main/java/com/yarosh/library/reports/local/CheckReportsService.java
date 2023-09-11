package com.yarosh.library.reports.local;

import com.opencsv.CSVWriter;
import com.yarosh.library.reports.api.CheckRecord;
import com.yarosh.library.reports.api.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CheckReportsService implements ReportService<CheckRecord> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckReportsService.class);

    private static final String[] CSV_HEADER = {"Market", "Cashier", "Date", "Time", "Products", "Discount", "Total price"};
    private static final String NO_DISCOUNT = "";
    private static final int CSV_HEADER_INDEX = 0;
    private static final String REPORTS_DIR_PATTERN = "{0}/{1}";
    private static final String REPORTS_FILE_PATTERN = "{0}/ report - {1}.csv";

    private final String reportsDir;

    public CheckReportsService(String reportsDir) {
        this.reportsDir = performReportsDir(reportsDir);
    }

    @Override
    public void storeReport(List<CheckRecord> records) {
        LOGGER.info("Calling stored report started, parameter: {}", records);
        if (records.isEmpty()) {
            return;
        }

        final String reportPath = createReportPath(records);
        try (CSVWriter writer = new CSVWriter(new FileWriter(reportPath))) {
            final List<String[]> content = convertToCsvFormat(records);
            writer.writeAll(content);
        } catch (IOException e) {
            LOGGER.debug("Report not stored ", e);
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

    private String createReportPath(List<CheckRecord> records) {
        return MessageFormat.format(REPORTS_FILE_PATTERN, reportsDir, records.get(0).date());
    }

    private String performReportsDir(String reportsDir) {
        LOGGER.info("Creation of the reports directory started: {}", reportsDir);
        final Path target = Paths.get(Objects.requireNonNull(this.getClass().getResource("/")).getPath());
        final Path reports = Paths.get(MessageFormat.format(REPORTS_DIR_PATTERN, target.toAbsolutePath(), reportsDir));
        try {
            return Files.createDirectories(reports).toString();
        } catch (IOException e) {
            LOGGER.debug("Creation of the reports directory failed: ", e);
            throw new RuntimeException(e);
        }
    }
}
