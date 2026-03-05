package com.gp.solutions.test.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Address {

    private Integer houseNumber;
    private String street;
    private String city;
    private String country;
    private String postCode;
}