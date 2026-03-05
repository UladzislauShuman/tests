package com.gp.solutions.test.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Полная информация об отеле")
public record HotelDetailedDto(
    @Schema(description = "Уникальный идентификатор отеля", example = "1")
    Long id,

    @Schema(description = "Название", example = "DoubleTree by Hilton Minsk")
    String name,

    @Schema(description = "Подробное описание", example = "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms in the Belorussian capital...")
    String description,

    @Schema(description = "Бренд", example = "Hilton")
    String brand,

    @Schema(description = "Полный адрес")
    AddressDto address,

    @Schema(description = "Контактная информация")
    ContactsDto contacts,

    @Schema(description = "Информация о времени въезда и выезда")
    ArrivalTimeDto arrivalTime,

    @Schema(description = "Список удобств", example = "[\"Free parking\", \"Free WiFi\", \"Fitness center\"]")
    List<String> amenities
) {

}