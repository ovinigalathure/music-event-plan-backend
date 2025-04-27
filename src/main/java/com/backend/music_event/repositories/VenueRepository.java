package com.backend.music_event.repository;

import com.backend.music_event.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
    List<Venue> findByCity(String city);
    List<Venue> findByState(String state);
    List<Venue> findByCapacityGreaterThanEqual(int capacity);
    List<Venue> findByCapacityLessThanEqual(int capacity);
    List<Venue> findByNameContainingIgnoreCase(String name);
}
