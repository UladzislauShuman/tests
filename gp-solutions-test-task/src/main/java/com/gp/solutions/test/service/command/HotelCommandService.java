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
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HotelCommandService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final AmenityRepository amenityRepository;

    @Transactional
    @CacheEvict(value = "histograms", allEntries = true)
    public HotelShortDto createHotel(HotelCreateDto dto) {
        Hotel hotel = hotelMapper.toEntity(dto);
        Hotel savedHotel = hotelRepository.save(hotel);
        return hotelMapper.toShortDto(savedHotel);
    }

    @Transactional
    @CacheEvict(value = {"hotels", "histograms"}, allEntries = true)
    public void addAmenities(Long id, List<String> amenityNames) {
        Hotel hotel = hotelRepository.findById(id)
            .orElseThrow(() -> new HotelNotFoundException("Hotel not found"));

        List<String> uniqueInputNames = amenityNames.stream()
            .filter(name -> name != null && !name.isBlank())
            .map(String::toLowerCase)
            .distinct()
            .toList();

        for (String name : uniqueInputNames) {
            Amenity amenity = amenityRepository.findByName(name)
                .orElseGet(() -> amenityRepository.save(new Amenity(name)));

            boolean alreadyHas = hotel.getAmenities().stream()
                .anyMatch(existing -> existing.getId().equals(amenity.getId()));

            if (!alreadyHas) {
                hotel.getAmenities().add(amenity);
            }
        }
    }
}