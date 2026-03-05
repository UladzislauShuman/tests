package com.gp.solutions.test.specification;

import com.gp.solutions.test.entity.Hotel;
import org.springframework.data.jpa.domain.Specification;

public class HotelSpecificationBuilder {

    public static Specification<Hotel> buildSearchSpec(String name, String brand, String city, String country, String amenity) {
        return Specification.where(distinct())
            .and(nameStartsWith(name))
            .and(fieldEquals("brand", brand))
            .and(addressEquals("city", city))
            .and(addressEquals("country", country))
            .and(hasAmenity(amenity));
    }

    private static Specification<Hotel> distinct() {
        return (root, query, cb) -> {
            query.distinct(true);
            return null;
        };
    }

    private static Specification<Hotel> nameStartsWith(String name) {
        return name == null ? null : (root, query, cb) ->
            cb.like(cb.lower(root.get("name")), name.toLowerCase() + "%");
    }

    private static Specification<Hotel> fieldEquals(String field, String value) {
        return value == null ? null : (root, query, cb) ->
            cb.equal(cb.lower(root.get(field)), value.toLowerCase());
    }

    private static Specification<Hotel> addressEquals(String field, String value) {
        return value == null ? null : (root, query, cb) ->
            cb.equal(cb.lower(root.get("address").get(field)), value.toLowerCase());
    }

    private static Specification<Hotel> hasAmenity(String amenity) {
        return amenity == null ? null : (root, query, cb) ->
            cb.equal(cb.lower(root.join("amenities").get("name")), amenity.toLowerCase());
    }
}