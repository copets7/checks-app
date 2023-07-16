package com.yarosh.checks.controller;

import com.yarosh.checks.controller.dto.CheckDto;
import com.yarosh.checks.controller.dto.DiscountCardDto;
import com.yarosh.checks.controller.dto.ProductPairDto;
import com.yarosh.checks.controller.util.ApiDtoConverter;
import com.yarosh.checks.controller.util.ProductApiDtoConverter;
import com.yarosh.checks.controller.view.CheckView;
import com.yarosh.checks.controller.view.DiscountCardView;
import com.yarosh.checks.controller.view.ProductView;
import com.yarosh.checks.domain.Check;
import com.yarosh.checks.domain.DiscountCard;
import com.yarosh.checks.domain.Product;
import com.yarosh.checks.domain.id.CheckId;
import com.yarosh.checks.domain.id.DiscountCardId;
import com.yarosh.checks.domain.id.ProductId;
import com.yarosh.checks.service.CrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1.0/check")
public class CheckController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckController.class);

    private final CrudService<Check, CheckId> checkService;
    private final CrudService<DiscountCard, DiscountCardId> discountCardService;
    private final CrudService<Product, ProductId> productService;

    private final ApiDtoConverter<DiscountCardDto, DiscountCardView, DiscountCard> discountCardConverter;
    private final ProductApiDtoConverter productConverter;

    private final String marketName;
    private final String cashierName;

    public CheckController(final CrudService<Check, CheckId> checkService,
                           final CrudService<DiscountCard, DiscountCardId> discountCardService,
                           final CrudService<Product, ProductId> productService,
                           final ApiDtoConverter<DiscountCardDto, DiscountCardView, DiscountCard> discountCardConverter,
                           final ProductApiDtoConverter productConverter,
                           final @Value("${app.cashier.name}") String marketName,
                           final @Value("${app.market.name}") String cashierName) {
        this.checkService = checkService;
        this.discountCardService = discountCardService;
        this.productService = productService;
        this.discountCardConverter = discountCardConverter;
        this.productConverter = productConverter;
        this.marketName = marketName;
        this.cashierName = cashierName;
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public ResponseEntity<CheckView> add(final @RequestBody CheckDto checkDto) {
        LOGGER.info("Calling add check started, parameter: {}", checkDto);

        final Check check = convertToCheck(checkDto);
        final Check created = checkService.add(check);
        final CheckView view = convertToCheckView(created);

        LOGGER.info("Calling add check successfully ended for product, value: {}", view.id());
        LOGGER.debug("Saved check view detailed printing: {}", view);

        return new ResponseEntity<>(view, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<CheckView> getById(final @PathVariable("id") Long id) {
        LOGGER.info("Calling getById check started for value: {}", id);

        return checkService.get(new CheckId(id))
                .map(this::convertToCheckView)
                .map(view -> new ResponseEntity<>(view, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<CheckView>> getAll() {
        LOGGER.info("Calling getAll checks started");

        List<CheckView> checks = checkService.getAll()
                .stream()
                .map(this::convertToCheckView)
                .toList();

        LOGGER.trace("Checks views detailed printing: {}", checks);

        return new ResponseEntity<>(checks, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}/delete", method = RequestMethod.DELETE)
    public ResponseEntity<CheckView> delete(final @PathVariable Long id) {
        LOGGER.info("Calling delete check started for product, value: {}", id);
        checkService.delete(new CheckId(id));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private CheckView convertToCheckView(Check check) {
        return new CheckView(
                check.getId().orElseThrow().value(),
                check.getMarketName(),
                check.getCashierName(),
                check.getDate(),
                check.getTime(),
                convertToProductViews(check.getProducts()),
                check.getDiscountCard().map(discountCardConverter::convertDomainToView).orElseThrow(),
                check.getTotalPrice()
        );
    }

    private Check convertToCheck(CheckDto checkDto) {
        final Map<ProductId, Integer> productIds = convertToProductIds(checkDto.products());
        return new Check(
                Optional.ofNullable(checkDto.id()).map(CheckId::new),
                marketName,
                cashierName,
                (checkDto.date() != null) ? checkDto.date() : LocalDate.now(),
                (checkDto.time() != null) ? checkDto.time() : LocalTime.now(),
                convertToProducts(productIds),
                discountCardService.get(new DiscountCardId(checkDto.discountCardId()))
        );
    }

    private List<ProductView> convertToProductViews(Map<Product, Integer> products) {
        return products.entrySet()
                .stream()
                .map(entry -> productConverter.convertProductToView(entry.getKey(), entry.getValue()))
                .toList();
    }

    private Map<ProductId, Integer> convertToProductIds(final List<ProductPairDto> products) {
        return products.stream()
                .collect(Collectors.toMap(product -> new ProductId(product.id()), ProductPairDto::quantityInCheck));
    }

    private Map<Product, Integer> convertToProducts(Map<ProductId, Integer> products) {
        return products.entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> productService.getOrThrow(entry.getKey()), Map.Entry::getValue));
    }
}
