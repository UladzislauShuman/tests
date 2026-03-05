package com.gp.solutions.test.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Элемент гистограммы")
public record HistogramRow(
    String value,
    Long count
) {

}