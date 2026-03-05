package com.gp.solutions.test.service.query;

import com.gp.solutions.test.service.strategy.HistogramStrategy;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class HotelAggregationService {

    private final Map<String, HistogramStrategy> strategies;

    public HotelAggregationService(List<HistogramStrategy> strategyList) {
        this.strategies = strategyList.stream()
            .collect(Collectors.toMap(HistogramStrategy::getParamName, Function.identity()));
    }

    @Cacheable(value = "histograms", key = "#param")
    public Map<String, Long> getHistogram(String param) {
        HistogramStrategy strategy = strategies.get(param.toLowerCase());
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported histogram parameter: " + param);
        }
        return strategy.buildHistogram();
    }
}