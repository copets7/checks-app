package com.yarosh.checks.controller;

import com.yarosh.checks.controller.dto.DiscountCardDto;
import com.yarosh.checks.controller.util.ApiDtoConverter;
import com.yarosh.checks.controller.view.DiscountCardView;
import com.yarosh.checks.domain.DiscountCard;
import com.yarosh.checks.domain.id.DiscountCardId;
import com.yarosh.checks.service.CrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1.0/discount_card")
public class DiscountCardController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscountCardController.class);

    private final CrudService<DiscountCard, DiscountCardId> discountCardService;
    private final ApiDtoConverter<DiscountCardDto, DiscountCardView, DiscountCard> discountCardApiDtoConverter;


    public DiscountCardController(CrudService<DiscountCard, DiscountCardId> discountCardService,
                                  ApiDtoConverter<DiscountCardDto, DiscountCardView, DiscountCard> discountCardApiDtoConverter) {
        this.discountCardService = discountCardService;
        this.discountCardApiDtoConverter = discountCardApiDtoConverter;
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public ResponseEntity<DiscountCardView> add(final @RequestBody DiscountCardDto discountCardDto) {
        LOGGER.info("Calling add discount card started, parameter: {}", discountCardDto);
        LOGGER.debug("Discount card parameter: {}", discountCardDto);

        final DiscountCard card = discountCardApiDtoConverter.convertDtoToDomain(discountCardDto);
        final DiscountCard created = discountCardService.add(card);

        LOGGER.info("Calling add discount card successfully ended for product, id: {}", created.id());
        LOGGER.debug("Saved discount card view detailed printing: {}", created);

        return new ResponseEntity<>(discountCardApiDtoConverter.convertDomainToView(created), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<DiscountCardView> getById(final @PathVariable("id") Long id) {
        LOGGER.info("Calling getById discount card started for id: {}", id);

        return discountCardService.get(new DiscountCardId(id))
                .map(discountCardApiDtoConverter::convertDomainToView)
                .map(view -> new ResponseEntity<>(view, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<DiscountCardView>> getAll() {
        LOGGER.info("Calling getAll discount cards started");

        List<DiscountCardView> discountCards = discountCardService.getAll()
                .stream()
                .map(discountCardApiDtoConverter::convertDomainToView)
                .toList();

        LOGGER.debug("Discount cards views detailed printing: {}", discountCards);

        return new ResponseEntity<>(discountCards, HttpStatus.OK);
    }

    @RequestMapping(path = "/update", method = RequestMethod.PUT)
    public ResponseEntity<DiscountCardView> update(final @RequestBody DiscountCardDto discountCardDto) {
        LOGGER.info("Calling update discount card started for product with id: {}", discountCardDto.id());
        LOGGER.debug("Discount card parameter: {}", discountCardDto);

        final DiscountCard discountCard = discountCardApiDtoConverter.convertDtoToDomain(discountCardDto);
        final DiscountCard updated = discountCardService.update(discountCard);

        LOGGER.info("Calling updated discount card successfully ended for product, id: {}", updated.id());
        LOGGER.debug("Updated discount card view detailed printing: {}", updated);

        return new ResponseEntity<>(discountCardApiDtoConverter.convertDomainToView(updated), HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}/delete", method = RequestMethod.DELETE)
    public ResponseEntity<DiscountCardView> delete(final @PathVariable Long id) {
        LOGGER.info("Calling delete discount card started for product, id: {}", id);
        discountCardService.delete(new DiscountCardId(id));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
