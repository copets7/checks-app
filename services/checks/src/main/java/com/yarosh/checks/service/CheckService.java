package com.yarosh.checks.service;

import com.yarosh.checks.domain.Check;
import com.yarosh.checks.domain.DiscountCard;
import com.yarosh.checks.domain.id.CheckId;
import com.yarosh.checks.domain.pagination.ContentPage;
import com.yarosh.checks.domain.pagination.ContentPageRequest;
import com.yarosh.checks.service.util.converter.BidirectionalConverter;
import com.yarosh.checks.service.util.converter.PaginationConverter;
import com.yarosh.library.reports.api.CheckRecord;
import com.yarosh.library.reports.api.ProductInfo;
import com.yarosh.library.reports.api.ReportService;
import com.yarosh.library.repository.api.CrudRepository;
import com.yarosh.library.repository.api.entity.CheckEntity;
import com.yarosh.library.repository.api.pagination.RepositoryPage;
import com.yarosh.library.repository.api.pagination.RepositoryPageRequest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class CheckService implements CrudService<Check, CheckId> {

    private final ReportService<CheckRecord> checkReportsService;
    private final CrudRepository<CheckEntity, Long> checkRepository;
    private final BidirectionalConverter<Check, CheckEntity> checkConverter;
    private final PaginationConverter paginationConverter;

    @Inject
    public CheckService(final ReportService<CheckRecord> checkReportsService,
                        final CrudRepository<CheckEntity, Long> checkRepository,
                        final BidirectionalConverter<Check, CheckEntity> checkConverter,
                        final PaginationConverter paginationConverter) {
        this.checkReportsService = checkReportsService;
        this.checkRepository = checkRepository;
        this.checkConverter = checkConverter;
        this.paginationConverter = paginationConverter;
    }

    @Scheduled(cron = "${reports.cron}")
    void sendReport() {
        checkReportsService.storeReport(convertToReportRecords(getAll()));
    }

    @Override
    public Check add(Check check) {
        return upsert(checkRepository::insert, check);
    }

    @Override
    public Check getOrThrow(CheckId id) {
        return get(id).orElseThrow(ObjectNotFoundException.supplier(id.value(), "Check"));
    }

    @Override
    @Cacheable("check-cache")
    public Optional<Check> get(CheckId id) {
        return checkRepository.select(id.value())
                .map(checkConverter::convertToDomain);
    }

    @Override
    @Cacheable("checks-cache")
    public List<Check> getAll() {
        return checkRepository.selectAll()
                .stream()
                .map(checkConverter::convertToDomain)
                .toList();
    }

    @Override
    public ContentPage<Check> getAll(ContentPageRequest pageRequest) {
        final RepositoryPageRequest databasePageRequest = paginationConverter.convertToRepositoryPageRequest(pageRequest);
        final RepositoryPage<CheckEntity> databasePage = checkRepository.selectAll(databasePageRequest);

        return paginationConverter.convertToContentPage(
                databasePage,
                checks -> checks.stream().map(checkConverter::convertToDomain).toList(),
                pageRequest.pageNumber() + 1
        );
    }

    @Override
    public Check update(Check check) {
        return upsert(checkRepository::update, check);
    }

    @Override
    public void delete(CheckId id) {
        checkRepository.delete(id.value());
    }

    private Check upsert(Function<CheckEntity, CheckEntity> upsert, Check check) {
        final CheckEntity upsertedCheck = upsert.apply(checkConverter.convertToEntity(check));
        return checkConverter.convertToDomain(upsertedCheck);
    }

    private List<CheckRecord> convertToReportRecords(List<Check> checks) {
        return checks.stream().map(check ->
                new CheckRecord(
                        check.getMarketName(),
                        check.getCashierName(),
                        check.getDate(),
                        check.getTime(),
                        convertToProductInfo(check),
                        check.getDiscountCard().map(DiscountCard::discount),
                        check.getTotalPrice()
                )
        ).toList();
    }

    private List<ProductInfo> convertToProductInfo(Check check) {
        return check.getProducts()
                .entrySet()
                .stream()
                .map(entry -> new ProductInfo(entry.getKey().description(), entry.getValue(), entry.getKey().discount()))
                .toList();
    }
}
