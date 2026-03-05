package com.gp.solutions.test.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

@Schema(description = "Информация о времени заезда и выезда")
public record ArrivalTimeDto(
    @NotNull(message = "Check-in time is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @Schema(description = "Время заезда", type = "string", pattern = "HH:mm", example = "14:00")
    LocalTime checkIn,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @Schema(description = "Время выезда", type = "string", pattern = "HH:mm", example = "12:00")
    LocalTime checkOut
) {}