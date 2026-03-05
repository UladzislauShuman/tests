package com.gp.solutions.test.service.query;

import com.gp.solutions.test.dto.HotelDetailedDto;
import com.gp.solutions.test.dto.HotelShortDto;
import com.gp.solutions.test.entity.Hotel;
import com.gp.solutions.test.exception.HotelNotFoundException;
import com.gp.solutions.test.mapper.HotelMapper;
import com.gp.solutions.test.repository.HotelRepository;
import com.gp.solutions.test.specification.HotelSpecificationBuilder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class HotelQueryService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    public List<HotelShortDto> getAllHotels(Pageable pageable) {
        log.atInfo()
            .setMessage("Retrieving list of all hotels")
            .addKeyValue("page", pageable.getPageNumber())
            .addKeyValue("size", pageable.getPageSize())
            .log();

        return hotelRepository.findAll(pageable)
            .map(hotelMapper::toShortDto)
            .getContent();
    }

    @Cacheable(value = "hotels", key = "#id")
    public HotelDetailedDto getHotelById(Long id) {
        log.atInfo()
            .setMessage("Fetching detailed data for hotel")
            .addKeyValue("hotelId", id)
            .log();

        return hotelRepository.findById(id)
            .map(hotelMapper::toDetailedDto)
            .orElseThrow(() -> {
                log.atWarn()
                    .setMessage("Hotel lookup failed")
                    .addKeyValue("hotelId", id)
                    .log();
                return new HotelNotFoundException("Hotel with id " + id + " not found");
            });
    }

    public List<HotelShortDto> searchHotels(String name, String brand, String city, String country,
        String amenity, Pageable pageable) {
        log.atInfo()
            .setMessage("Executing hotel search with filters")
            .addKeyValue("name", name)
            .addKeyValue("brand", brand)
            .addKeyValue("city", city)
            .addKeyValue("country", country)
            .addKeyValue("amenity", amenity)
            .log();

        Specification<Hotel> spec = HotelSpecificationBuilder.buildSearchSpec(name, brand, city,
            country, amenity);

        Page<Hotel> result = hotelRepository.findAll(spec, pageable);

        log.atDebug()
            .setMessage("Search completed")
            .log();

        return result.map(hotelMapper::toShortDto).getContent();
    }
}