package com.yarosh.checks.controller;

import com.yarosh.checks.controller.dto.DiscountCardDto;
import com.yarosh.checks.controller.util.ApiDtoConverter;
import com.yarosh.checks.controller.util.PaginationDtoConverter;
import com.yarosh.checks.controller.view.ContentPageView;
import com.yarosh.checks.controller.view.DiscountCardView;
import com.yarosh.checks.domain.DiscountCard;
import com.yarosh.checks.domain.id.DiscountCardId;
import com.yarosh.checks.domain.pagination.ContentPage;
import com.yarosh.checks.domain.pagination.ContentPageRequest;
import com.yarosh.checks.service.CrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1.0/discount-card")
public class DiscountCardController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscountCardController.class);

    private final CrudService<DiscountCard, DiscountCardId> discountCardService;
    private final ApiDtoConverter<DiscountCardDto, DiscountCardView, DiscountCard> discountCardApiDtoConverter;
    private final PaginationDtoConverter paginationDtoConverter;

    public DiscountCardController(final CrudService<DiscountCard, DiscountCardId> discountCardService,
                                  final ApiDtoConverter<DiscountCardDto, DiscountCardView, DiscountCard> discountCardApiDtoConverter,
                                  final PaginationDtoConverter paginationDtoConverter) {
        this.discountCardService = discountCardService;
        this.discountCardApiDtoConverter = discountCardApiDtoConverter;
        this.paginationDtoConverter = paginationDtoConverter;
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public ResponseEntity<DiscountCardView> add(final @RequestBody DiscountCardDto discountCardDto) {
        LOGGER.info("Calling add discount card started, parameter: {}", discountCardDto);

        final DiscountCard card = discountCardApiDtoConverter.convertDtoToDomain(discountCardDto);
        final DiscountCard created = discountCardService.add(card);
        final DiscountCardView view = discountCardApiDtoConverter.convertDomainToView(created);

        LOGGER.info("Calling add discount card successfully ended for product, value: {}", view.id());
        LOGGER.debug("Saved discount card view detailed printing: {}", view);

        return new ResponseEntity<>(view, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<DiscountCardView> getById(final @PathVariable("id") Long id) {
        LOGGER.info("Calling getById discount card started for value: {}", id);

        return discountCardService.get(new DiscountCardId(id))
                .map(discountCardApiDtoConverter::convertDomainToView)
                .map(view -> new ResponseEntity<>(view, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public ResponseEntity<ContentPageView<DiscountCardView>> getAll(
            final @RequestParam(name = "page") Integer page,
            final @RequestParam(name = "size") Integer size,
            final @RequestParam(name = "column", required = false) String column,
            final @RequestParam(name = "isDesc", required = false) Boolean isDesc
    ) {
        LOGGER.info("Calling getAll discount cards started, page: {}, size: {}, column: {}, isDesc: {}", page, size, column, isDesc);

        final ContentPageRequest pageRequest = paginationDtoConverter.convertToContentPageRequest(page, size, column, isDesc);
        final ContentPage<DiscountCard> pageContent = discountCardService.getAll(pageRequest);

        final ContentPageView<DiscountCardView> pageView = paginationDtoConverter.convertToContentPageView(pageContent,
                discountCards -> discountCards.stream().map(discountCardApiDtoConverter::convertDomainToView).toList()
        );

        LOGGER.trace("Discount cards views detailed printing: {}", pageView);

        return new ResponseEntity<>(pageView, HttpStatus.OK);
    }

    @RequestMapping(path = "/update", method = RequestMethod.PUT)
    public ResponseEntity<DiscountCardView> update(final @RequestBody DiscountCardDto discountCardDto) {
        LOGGER.info("Calling update discount card started for product with value: {}", discountCardDto.id());
        LOGGER.debug("Discount card parameter: {}", discountCardDto);

        final DiscountCard discountCard = discountCardApiDtoConverter.convertDtoToDomain(discountCardDto);
        final DiscountCard updated = discountCardService.update(discountCard);
        final DiscountCardView view = discountCardApiDtoConverter.convertDomainToView(updated);

        LOGGER.info("Calling updated discount card successfully ended for product, value: {}", view.id());
        LOGGER.debug("Updated discount card view detailed printing: {}", view);

        return new ResponseEntity<>(view, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}/delete", method = RequestMethod.DELETE)
    public ResponseEntity<DiscountCardView> delete(final @PathVariable Long id) {
        LOGGER.info("Calling delete discount card started for product, value: {}", id);
        discountCardService.delete(new DiscountCardId(id));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
