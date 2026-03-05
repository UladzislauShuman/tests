package com.gp.solutions.test.service.strategy;

import com.gp.solutions.test.repository.HotelRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AmenitiesHistogramStrategy implements HistogramStrategy {

    private final HotelRepository repository;

    @Override
    public String getParamName() {
        return "amenities";
    }

    @Override
    public Map<String, Long> buildHistogram() {
        return rowsToMap(repository.countByAmenities());
    }
}