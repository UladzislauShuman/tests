package com.gp.solutions.test.repository;

import com.gp.solutions.test.dto.HistogramRow;
import com.gp.solutions.test.entity.Hotel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>,
    JpaSpecificationExecutor<Hotel> {

    @EntityGraph(attributePaths = {"amenities"})
    Optional<Hotel> findById(Long id);

    @Query("SELECT new com.gp.solutions.test.dto.HistogramRow(h.brand, COUNT(h)) " +
        "FROM Hotel h WHERE h.brand IS NOT NULL GROUP BY h.brand")
    List<HistogramRow> countByBrand();

    @Query("SELECT new com.gp.solutions.test.dto.HistogramRow(h.address.city, COUNT(h)) " +
        "FROM Hotel h WHERE h.address.city IS NOT NULL GROUP BY h.address.city")
    List<HistogramRow> countByCity();

    @Query("SELECT new com.gp.solutions.test.dto.HistogramRow(h.address.country, COUNT(h)) " +
        "FROM Hotel h WHERE h.address.country IS NOT NULL GROUP BY h.address.country")
    List<HistogramRow> countByCountry();

    @Query("SELECT new com.gp.solutions.test.dto.HistogramRow(a.name, COUNT(h)) " +
        "FROM Hotel h JOIN h.amenities a WHERE a.name IS NOT NULL GROUP BY a.name")
    List<HistogramRow> countByAmenities();
}