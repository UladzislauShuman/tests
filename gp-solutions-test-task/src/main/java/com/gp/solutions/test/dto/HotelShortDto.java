package com.gp.solutions.test.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Краткая информация об отеле")
public record HotelShortDto(
    @Schema(description = "Уникальный идентификатор отеля", example = "1")
    Long id,

    @Schema(description = "Название отеля", example = "DoubleTree by Hilton Minsk")
    String name,

    @Schema(description = "Краткое описание отеля", example = "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms...")
    String description,

    @Schema(description = "Полный адрес в одну строку", example = "9 Pobediteley Avenue, Minsk, 220004, Belarus")
    String address,

    @Schema(description = "Контактный телефон", example = "+375 17 309-80-00")
    String phone
) {}