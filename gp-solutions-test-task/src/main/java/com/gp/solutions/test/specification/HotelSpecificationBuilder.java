package com.gp.solutions.test.specification;

import com.gp.solutions.test.entity.Hotel;
import java.util.Arrays;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public class HotelSpecificationBuilder {

    public static Specification<Hotel> buildSearchSpec(String name, String brand, String city,
        String country, String amenities) {
        return Specification.where(distinct())
            .and(nameStartsWith(name))
            .and(fieldEquals("brand", brand))
            .and(addressEquals("city", city))
            .and(addressEquals("country", country))
            .and(hasAmenities(amenities));
    }

    private static Specification<Hotel> distinct() {
        return (root, query, cb) -> {
            query.distinct(true);
            return null;
        };
    }

    private static Specification<Hotel> nameStartsWith(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) {
                return null;
            }
            return cb.like(cb.lower(root.get("name")), name.toLowerCase() + "%");
        };
    }

    private static Specification<Hotel> fieldEquals(String field, String value) {
        return (root, query, cb) -> {
            if (value == null || value.isBlank()) {
                return null;
            }
            return cb.equal(cb.lower(root.get(field)), value.toLowerCase());
        };
    }

    private static Specification<Hotel> addressEquals(String field, String value) {
        return (root, query, cb) -> {
            if (value == null || value.isBlank()) {
                return null;
            }
            return cb.equal(cb.lower(root.get("address").get(field)), value.toLowerCase());
        };
    }

    private static Specification<Hotel> hasAmenities(String amenitiesStr) {
        return (root, query, cb) -> {
            if (amenitiesStr == null || amenitiesStr.isBlank()) {
                return null;
            }

            List<String> amenitiesList = Arrays.stream(amenitiesStr.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .toList();

            return cb.lower(root.join("amenities").get("name")).in(amenitiesList);
        };
    }
}