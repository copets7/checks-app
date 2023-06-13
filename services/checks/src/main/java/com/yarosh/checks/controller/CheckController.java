package com.yarosh.checks.controller;

import com.yarosh.checks.controller.dto.CheckDto;
import com.yarosh.checks.controller.util.ApiDtoConverter;
import com.yarosh.checks.controller.view.CheckView;
import com.yarosh.checks.domain.Check;
import com.yarosh.checks.domain.id.CheckId;
import com.yarosh.checks.service.CrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1.0/check")
public class CheckController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckController.class);

    private final CrudService<Check, CheckId> checkService;
    private final ApiDtoConverter<CheckDto, CheckView, Check> checkApiDtoConverter;

    public CheckController(final CrudService<Check, CheckId> checkService,
                           final ApiDtoConverter<CheckDto, CheckView, Check> checkApiDtoConverter) {
        this.checkService = checkService;
        this.checkApiDtoConverter = checkApiDtoConverter;
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public ResponseEntity<CheckView> add(final @RequestBody CheckDto checkDto) {
        LOGGER.info("Calling add check started, parameter: {}", checkDto);

        final Check check = checkApiDtoConverter.convertDtoToDomain(checkDto);
        final Check created = checkService.add(check);
        final CheckView view = checkApiDtoConverter.convertDomainToView(created);

        LOGGER.info("Calling add check successfully ended for product, id: {}", view.id());
        LOGGER.debug("Saved check view detailed printing: {}", view);

        return new ResponseEntity<>(view, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<CheckView> getById(final @PathVariable("id") Long id) {
        LOGGER.info("Calling getById check started for id: {}", id);

        return checkService.get(new CheckId(id))
                .map(checkApiDtoConverter::convertDomainToView)
                .map(view -> new ResponseEntity<>(view, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<CheckView>> getAll() {
        LOGGER.info("Calling getAll checks started");

        List<CheckView> checks = checkService.getAll()
                .stream()
                .map(checkApiDtoConverter::convertDomainToView)
                .toList();

        LOGGER.trace("Checks views detailed printing: {}", checks);

        return new ResponseEntity<>(checks, HttpStatus.OK);
    }

    @RequestMapping(path = "/update", method = RequestMethod.PUT)
    public ResponseEntity<CheckView> update(final @RequestBody CheckDto checkDto) {
        LOGGER.info("Calling update check started for product with id: {}", checkDto.id());

        final Check check = checkApiDtoConverter.convertDtoToDomain(checkDto);
        final Check updated = checkService.update(check);
        final CheckView view = checkApiDtoConverter.convertDomainToView(updated);

        LOGGER.info("Calling updated check successfully ended for product, id: {}", view.id());
        LOGGER.debug("Updated check view detailed printing: {}", view);

        return new ResponseEntity<>(view, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}/delete", method = RequestMethod.DELETE)
    public ResponseEntity<CheckView> delete(final @PathVariable Long id) {
        LOGGER.info("Calling delete check started for product, id: {}", id);
        checkService.delete(new CheckId(id));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
