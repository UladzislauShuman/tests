package com.gp.solutions.test.service.query;

import com.gp.solutions.test.service.strategy.HistogramStrategy;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class HotelAggregationService {

    private final Map<String, HistogramStrategy> strategies;

    public HotelAggregationService(List<HistogramStrategy> strategyList) {
        this.strategies = strategyList.stream()
            .collect(Collectors.toMap(HistogramStrategy::getParamName, Function.identity()));
    }

    @Cacheable(value = "histograms", key = "#param")
    public Map<String, Long> getHistogram(String param) {
        log.atInfo()
            .setMessage("Building distribution histogram for hotels")
            .addKeyValue("parameter", param)
            .log();

        HistogramStrategy strategy = strategies.get(param.toLowerCase());
        if (strategy == null) {
            log.atWarn()
                .setMessage("Requested histogram parameter is not supported")
                .addKeyValue("invalidParameter", param)
                .log();
            throw new IllegalArgumentException("Unsupported histogram parameter: " + param);
        }
        Map<String, Long> result = strategy.buildHistogram();
        log.atDebug()
            .setMessage("Histogram data successfully generated")
            .addKeyValue("parameter", param)
            .addKeyValue("entriesCount", result.size())
            .log();

        return result;
    }
}