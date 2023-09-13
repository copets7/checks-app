package com.yarosh.library.reports.local;

import com.opencsv.CSVWriter;
import com.yarosh.library.reports.api.CheckRecord;
import com.yarosh.library.reports.api.ProductInfo;
import com.yarosh.library.reports.api.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CheckReportsService implements ReportService<CheckRecord> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckReportsService.class);

    private static final String[] CSV_HEADER = {"Market", "Cashier", "Date", "Time", "Products", "Number", "Discount"};
    private static final String EMPTY_COLUMN = "";
    private static final String NO_DISCOUNT = "No discount";
    private static final int ZERO_DISCOUNT = 0;
    private static final int ZERO_ROW_INDEX = 0;
    private static final int CSV_HEADER_INDEX = 0;
    private static final String REPORTS_DIR_PATTERN = "{0}/{1}";
    private static final String REPORTS_FILE_PATTERN = "{0}/report-{1}.csv";

    private final String reportsDir;

    @Inject
    public CheckReportsService(String reportsDir) {
        this.reportsDir = performReportsDir(reportsDir);
    }

    @Override
    public void storeReport(List<CheckRecord> records) {
        if (records.isEmpty()) {
            return;
        }
        LOGGER.info("Calling stored report started, parameter: {}", records);

        final String reportPath = MessageFormat.format(REPORTS_FILE_PATTERN, reportsDir, LocalDate.now());
        try (CSVWriter writer = new CSVWriter(new FileWriter(reportPath))) {
            final List<String[]> content = convertToArray(records);
            writer.writeAll(content);
        } catch (IOException e) {
            LOGGER.debug("Report not stored ", e);
            throw new RuntimeException(e);
        }
    }

    private List<String[]> convertToArray(List<CheckRecord> records) {
        List<String[]> content = new ArrayList<>();
        content.add(CSV_HEADER_INDEX, CSV_HEADER);

        for (CheckRecord record : records) {

            final List<ProductInfo> products = record.products();
            for (int i = 0; i < products.size(); i++) {
                final ProductInfo product = products.get(i);
                if (i == ZERO_ROW_INDEX) {
                    content.add(
                            new String[]{
                                    record.marketName(),
                                    record.cashierName(),
                                    record.date().toString(),
                                    record.time().toString(),
                                    product.name(),
                                    product.number().toString(),
                                    product.discount() != ZERO_DISCOUNT ? product.discount().toString() : NO_DISCOUNT,
                            }
                    );
                } else {
                    content.add(
                            new String[]{
                                    EMPTY_COLUMN,
                                    EMPTY_COLUMN,
                                    EMPTY_COLUMN,
                                    EMPTY_COLUMN,
                                    product.name(),
                                    product.number().toString(),
                                    product.discount() != ZERO_DISCOUNT ? product.discount().toString() : NO_DISCOUNT
                            }
                    );
                }
            }

            if (record.discount().isPresent()) {
                content.add(new String[]{"Discount from card", String.valueOf(record.discount().get())});
            }

            content.add(new String[]{"Total price", String.valueOf(record.totalPrice())});
            content.add(new String[]{});
        }
        return content;
    }

    private String performReportsDir(String reportsDir) {
        LOGGER.info("Creation of the reports directory started: {}", reportsDir);
        final Path target = Paths.get(Objects.requireNonNull(this.getClass().getResource("/")).getPath());
        final Path reports = Paths.get(MessageFormat.format(REPORTS_DIR_PATTERN, target.toAbsolutePath(), reportsDir));

        try {
            final String path = Files.createDirectories(reports).toString();
            LOGGER.debug("Report file was created, path: {}", path);

            return path;
        } catch (IOException e) {
            LOGGER.debug("Creation of the reports directory failed: ", e);
            throw new RuntimeException(e);
        }
    }
}
