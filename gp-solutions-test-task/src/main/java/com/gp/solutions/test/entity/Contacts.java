package com.gp.solutions.test.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Contacts {

    private String phone;
    private String email;
}