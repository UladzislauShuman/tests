package com.gp.solutions.test.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Данные для создания нового отеля")
public record HotelCreateDto(
    @NotBlank(message = "Hotel name is required")
    @Schema(description = "Название отеля", example = "DoubleTree by Hilton Minsk")
    String name,

    @Schema(description = "Описание отеля", example = "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms...")
    String description,

    @NotBlank(message = "Brand is required")
    @Schema(description = "Бренд отеля", example = "Hilton")
    String brand,

    @Valid
    @NotNull(message = "Address is required")
    @Schema(description = "Адрес")
    AddressDto address,

    @Valid
    @NotNull(message = "Contacts are required")
    @Schema(description = "Контактные данные")
    ContactsDto contacts,

    @Valid
    @NotNull(message = "Arrival time is required")
    @Schema(description = "Время въезда и выезда")
    ArrivalTimeDto arrivalTime
) {

}