package com.gp.solutions.test.entity;

import jakarta.persistence.Embeddable;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class ArrivalTime {

    private LocalTime checkIn;
    private LocalTime checkOut;
}