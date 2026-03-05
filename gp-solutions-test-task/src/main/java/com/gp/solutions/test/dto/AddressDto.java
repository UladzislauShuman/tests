package com.gp.solutions.test.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Информация об адресе")
public record AddressDto(
    @NotNull(message = "House number is required")
    @Schema(description = "Номер дома", example = "9")
    Integer houseNumber,

    @NotBlank(message = "Street is required")
    @Schema(description = "Улица", example = "Pobediteley Avenue")
    String street,

    @NotBlank(message = "City is required")
    @Schema(description = "Город", example = "Minsk")
    String city,

    @NotBlank(message = "Country is required")
    @Schema(description = "Страна", example = "Belarus")
    String country,

    @NotBlank(message = "Post code is required")
    @Schema(description = "Почтовый индекс", example = "220004")
    String postCode
) {}