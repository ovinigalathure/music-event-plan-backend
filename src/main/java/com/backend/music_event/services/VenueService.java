package com.backend.music_event.services;

import com.backend.music_event.model.Venue;
import com.backend.music_event.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VenueService {

    private final VenueRepository venueRepository;

    @Autowired
    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    public Optional<Venue> getVenueById(Long id) {
        return venueRepository.findById(id);
    }

    public Venue createVenue(Venue venue) {
        return venueRepository.save(venue);
    }

    public Venue updateVenue(Long id, Venue venueDetails) {
        Optional<Venue> optionalVenue = venueRepository.findById(id);
        if (optionalVenue.isPresent()) {
            Venue existingVenue = optionalVenue.get();
            existingVenue.setName(venueDetails.getName());
            existingVenue.setCity(venueDetails.getCity());
            existingVenue.setState(venueDetails.getState());
            existingVenue.setCapacity(venueDetails.getCapacity());
            existingVenue.setDescription(venueDetails.getDescription());
            existingVenue.setAmenities(venueDetails.getAmenities());
            existingVenue.setImageUrl(venueDetails.getImageUrl());
            return venueRepository.save(existingVenue);
        }
        return null;
    }

    public void deleteVenue(Long id) {
        venueRepository.deleteById(id);
    }

    public List<Venue> searchVenuesByName(String name) {
        return venueRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Venue> getVenuesByCity(String city) {
        return venueRepository.findByCity(city);
    }

    public List<Venue> getVenuesByState(String state) {
        return venueRepository.findByState(state);
    }

    public List<Venue> getVenuesByMinCapacity(int capacity) {
        return venueRepository.findByCapacityGreaterThanEqual(capacity);
    }

    public List<Venue> getVenuesByMaxCapacity(int capacity) {
        return venueRepository.findByCapacityLessThanEqual(capacity);
    }
}
