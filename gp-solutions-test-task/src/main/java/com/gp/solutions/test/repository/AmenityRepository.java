package com.gp.solutions.test.repository;

import com.gp.solutions.test.entity.Amenity;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Long> {
    List<Amenity> findByNameIn(Collection<String> names);
}