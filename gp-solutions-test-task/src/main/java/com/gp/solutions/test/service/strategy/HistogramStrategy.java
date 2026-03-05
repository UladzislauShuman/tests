package com.gp.solutions.test.service.strategy;

import com.gp.solutions.test.dto.HistogramRow;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface HistogramStrategy {

    String getParamName();

    default Map<String, Long> rowsToMap(List<HistogramRow> rows) {
        return rows.stream()
            .collect(Collectors.toMap(
                HistogramRow::value,
                HistogramRow::count
            ));
    }

    Map<String, Long> buildHistogram();
}