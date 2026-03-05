package com.gp.solutions.test.controller;

import com.gp.solutions.test.dto.HotelCreateDto;
import com.gp.solutions.test.dto.HotelDetailedDto;
import com.gp.solutions.test.dto.HotelShortDto;
import com.gp.solutions.test.service.command.HotelCommandService;
import com.gp.solutions.test.service.query.HotelAggregationService;
import com.gp.solutions.test.service.query.HotelQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "GP Solutions. Hotel API", description = " RESTful API для работы с отелями")
public class HotelController {

    private final HotelQueryService queryService;
    private final HotelCommandService commandService;
    private final HotelAggregationService aggregationService;

    @GetMapping("/hotels")
    @Operation(summary = "получение списка всех отелей с их краткой информацией")
    // если бы я возвращал Page, то там были бы еще метаданные, которые не соответствуют требованию из ТЗ
    // либо эту информацию можно передавать в заголовке
    public List<HotelShortDto> getAllHotels(Pageable pageable) {
        log.atInfo()
            .setMessage("Fetching all hotels")
            .addKeyValue("pageNumber", pageable.getPageNumber())
            .addKeyValue("pageSize", pageable.getPageSize())
            .log();

        return queryService.getAllHotels(pageable);
    }

    @GetMapping("/hotels/{id}")
    @Operation(summary = "получение расширенной информации по конктретному отелю")
    public HotelDetailedDto getHotelById(@PathVariable Long id) {
        log.atInfo()
            .setMessage("Fetching detailed information for a specific hotel")
            .addKeyValue("hotelId", id)
            .log();

        return queryService.getHotelById(id);
    }

    @PostMapping("/hotels")
    @Operation(summary = "создание нового отеля")
    public HotelShortDto createHotel(@Valid @RequestBody HotelCreateDto dto) {
        log.atInfo()
            .setMessage("Received request to create a new hotel")
            .addKeyValue("hotelName", dto.name())
            .addKeyValue("brand", dto.brand())
            .log();

        return commandService.createHotel(dto);
    }

    @PostMapping("/hotels/{id}/amenities")
    @Operation(summary = "добавление списка amenities к отелю")
    public void addAmenities(@PathVariable Long id, @RequestBody List<String> amenities) {
        log.atInfo()
            .setMessage("Adding a list of amenities to the hotel")
            .addKeyValue("hotelId", id)
            .addKeyValue("amenitiesCount", amenities.size())
            .log();

        commandService.addAmenities(id, amenities);
    }

    @GetMapping("/search")
    @Operation(summary = "поиск получение списка всех отелей с их краткой информацией по следующим параметрам: name, brand, city, country, amenities")
    public List<HotelShortDto> searchHotels(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String brand,
        @RequestParam(required = false) String city,
        @RequestParam(required = false) String country,
        @RequestParam(required = false) String amenities,
        Pageable pageable) {
        log.atInfo()
            .setMessage("Searching for hotels with provided filters")
            .addKeyValue("nameFilter", name)
            .addKeyValue("brandFilter", brand)
            .addKeyValue("cityFilter", city)
            .addKeyValue("countryFilter", country)
            .addKeyValue("amenityFilter", amenities)
            .log();

        return queryService.searchHotels(name, brand, city, country, amenities, pageable);
    }

    @GetMapping("/histogram/{param}")
    @Operation(summary = "получение количества отелей сгруппированных по каждому значению указанного параметра. Параметр: brand, city, country, amenities.")
    public Map<String, Long> getHistogram(@PathVariable String param) {
        log.atInfo()
            .setMessage("Making hotel distribution histogram")
            .addKeyValue("groupByParameter", param)
            .log();

        return aggregationService.getHistogram(param);
    }
}