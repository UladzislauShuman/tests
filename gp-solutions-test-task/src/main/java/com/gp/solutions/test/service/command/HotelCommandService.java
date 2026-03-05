package com.gp.solutions.test.service.command;

import com.gp.solutions.test.dto.HotelCreateDto;
import com.gp.solutions.test.dto.HotelShortDto;
import com.gp.solutions.test.entity.Amenity;
import com.gp.solutions.test.entity.Hotel;
import com.gp.solutions.test.exception.HotelNotFoundException;
import com.gp.solutions.test.mapper.HotelMapper;
import com.gp.solutions.test.repository.AmenityRepository;
import com.gp.solutions.test.repository.HotelRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelCommandService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final AmenityRepository amenityRepository;

    @Transactional
    @CacheEvict(value = "histograms", allEntries = true)
    public HotelShortDto createHotel(HotelCreateDto dto) {
        log.atInfo()
            .setMessage("Creating a new hotel record")
            .addKeyValue("hotelName", dto.name())
            .addKeyValue("brand", dto.brand())
            .log();

        Hotel hotel = hotelMapper.toEntity(dto);
        Hotel savedHotel = hotelRepository.save(hotel);

        log.atInfo()
            .setMessage("Hotel successfully saved to database")
            .addKeyValue("hotelId", savedHotel.getId())
            .log();

        return hotelMapper.toShortDto(savedHotel);
    }

    @Transactional
    @CacheEvict(value = {"hotels", "histograms"}, allEntries = true)
    public void addAmenities(Long id, List<String> amenityNames) {
        log.atInfo()
            .setMessage("Updating amenities for hotel")
            .addKeyValue("hotelId", id)
            .log();

        Hotel hotel = hotelRepository.findById(id)
            .orElseThrow(() -> new HotelNotFoundException("Hotel not found"));

        List<String> uniqueInputNames = amenityNames.stream()
            .filter(name -> name != null && !name.isBlank())
            .map(String::trim)
            .map(String::toLowerCase)
            .distinct()
            .toList();

        if (uniqueInputNames.isEmpty()) {
            return;
        }

        List<Amenity> existingAmenities = amenityRepository.findByNameIn(uniqueInputNames);
        Set<String> existingNames = existingAmenities.stream()
            .map(Amenity::getName)
            .collect(Collectors.toSet());

        List<Amenity> newAmenities = uniqueInputNames.stream()
            .filter(name -> !existingNames.contains(name))
            .map(Amenity::new)
            .toList();

        if (!newAmenities.isEmpty()) {
            log.atDebug()
                .setMessage("Saving new amenities to DB")
                .addKeyValue("newAmenitiesCount", newAmenities.size())
                .log();
            existingAmenities.addAll(amenityRepository.saveAll(newAmenities));
        }

        Set<Long> currentAmenityIds = hotel.getAmenities().stream()
            .map(Amenity::getId)
            .collect(Collectors.toSet());

        long addedCount = existingAmenities.stream()
            .filter(amenity -> !currentAmenityIds.contains(amenity.getId()))
            .peek(hotel.getAmenities()::add)
            .count();

        log.atInfo()
            .setMessage("Amenities link process completed")
            .addKeyValue("hotelId", id)
            .addKeyValue("newlyLinkedCount", addedCount)
            .log();
    }
}