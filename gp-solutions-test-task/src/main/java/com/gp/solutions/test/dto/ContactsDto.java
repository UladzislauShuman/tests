package com.gp.solutions.test.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Контактная информация")
public record ContactsDto(
    @NotBlank(message = "Phone number is required")
    @Schema(description = "Номер телефона", example = "+375 17 309-80-00")
    String phone,

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Schema(description = "Адрес электронной почты", example = "doubletreeminsk.info@hilton.com")
    String email
) {}