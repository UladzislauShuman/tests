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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "GP Solutions. Hotel API", description = " RESTful API для работы с отелями")
public class HotelController {

    private final HotelQueryService queryService;
    private final HotelCommandService commandService;
    private final HotelAggregationService aggregationService;

    @GetMapping("/hotels")
    @Operation(summary = "получение списка всех отелей с их краткой информацией")
    public Page<HotelShortDto> getAllHotels(Pageable pageable) {
        return queryService.getAllHotels(pageable);
    }

    @GetMapping("/hotels/{id}")
    @Operation(summary = "получение расширенной информации по конктретному отелю")
    public HotelDetailedDto getHotelById(@PathVariable Long id) {
        return queryService.getHotelById(id);
    }

    @PostMapping("/hotels")
    @Operation(summary = "создание нового отеля")
    public HotelShortDto createHotel(@Valid @RequestBody HotelCreateDto dto) {
        return commandService.createHotel(dto);
    }

    @PostMapping("/hotels/{id}/amenities")
    @Operation(summary = "добавление списка amenities к отелю")
    public void addAmenities(@PathVariable Long id, @RequestBody List<String> amenities) {
        commandService.addAmenities(id, amenities);
    }

    @GetMapping("/search")
    @Operation(summary = "поиск получение списка всех отелей с их краткой информацией по следующим параметрам: name, brand, city, country, amenities")
    public Page<HotelShortDto> searchHotels(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String brand,
        @RequestParam(required = false) String city,
        @RequestParam(required = false) String country,
        @RequestParam(required = false) String amenities,
        Pageable pageable) {
        return queryService.searchHotels(name, brand, city, country, amenities, pageable);
    }

    @GetMapping("/histogram/{param}")
    @Operation(summary = "получение количества отелей сгруппированных по каждому значению указанного параметра. Параметр: brand, city, country, amenities.")
    public Map<String, Long> getHistogram(@PathVariable String param) {
        return aggregationService.getHistogram(param);
    }
}