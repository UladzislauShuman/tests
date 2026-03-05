package com.gp.solutions.test.repository;

import com.gp.solutions.test.dto.HistogramRow;
import com.gp.solutions.test.entity.Hotel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>,
    JpaSpecificationExecutor<Hotel> {

    @Query("SELECT new com.gp.solutions.test.dto.HistogramRow(h.brand, COUNT(h)) " +
        "FROM Hotel h GROUP BY h.brand")
    List<HistogramRow> countByBrand();

    @Query("SELECT new com.gp.solutions.test.dto.HistogramRow(h.address.city, COUNT(h)) " +
        "FROM Hotel h GROUP BY h.address.city")
    List<HistogramRow> countByCity();

    @Query("SELECT new com.gp.solutions.test.dto.HistogramRow(h.address.country, COUNT(h)) " +
        "FROM Hotel h GROUP BY h.address.country")
    List<HistogramRow> countByCountry();

    @Query("SELECT new com.gp.solutions.test.dto.HistogramRow(a.name, COUNT(h)) " +
        "FROM Hotel h JOIN h.amenities a GROUP BY a.name")
    List<HistogramRow> countByAmenities();
}