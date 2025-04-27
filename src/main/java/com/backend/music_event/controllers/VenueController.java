package com.backend.music_event.controller;

import com.backend.music_event.model.Venue;
import com.backend.music_event.services.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/venues")
public class VenueController {

    private final VenueService venueService;

    @Autowired
    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @GetMapping
    public ResponseEntity<List<Venue>> getAllVenues() {
        List<Venue> venues = venueService.getAllVenues();
        return new ResponseEntity<>(venues, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venue> getVenueById(@PathVariable Long id) {
        Optional<Venue> venue = venueService.getVenueById(id);
        return venue.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Venue> createVenue(@RequestBody Venue venue) {
        Venue newVenue = venueService.createVenue(venue);
        return new ResponseEntity<>(newVenue, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venue> updateVenue(@PathVariable Long id, @RequestBody Venue venue) {
        Venue updatedVenue = venueService.updateVenue(id, venue);
        if (updatedVenue != null) {
            return new ResponseEntity<>(updatedVenue, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenue(@PathVariable Long id) {
        venueService.deleteVenue(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Venue>> searchVenuesByName(@RequestParam String name) {
        List<Venue> venues = venueService.searchVenuesByName(name);
        return new ResponseEntity<>(venues, HttpStatus.OK);
    }

    @GetMapping("/filter/city")
    public ResponseEntity<List<Venue>> getVenuesByCity(@RequestParam String city) {
        List<Venue> venues = venueService.getVenuesByCity(city);
        return new ResponseEntity<>(venues, HttpStatus.OK);
    }

    @GetMapping("/filter/state")
    public ResponseEntity<List<Venue>> getVenuesByState(@RequestParam String state) {
        List<Venue> venues = venueService.getVenuesByState(state);
        return new ResponseEntity<>(venues, HttpStatus.OK);
    }

    @GetMapping("/filter/min-capacity")
    public ResponseEntity<List<Venue>> getVenuesByMinCapacity(@RequestParam int capacity) {
        List<Venue> venues = venueService.getVenuesByMinCapacity(capacity);
        return new ResponseEntity<>(venues, HttpStatus.OK);
    }

    @GetMapping("/filter/max-capacity")
    public ResponseEntity<List<Venue>> getVenuesByMaxCapacity(@RequestParam int capacity) {
        List<Venue> venues = venueService.getVenuesByMaxCapacity(capacity);
        return new ResponseEntity<>(venues, HttpStatus.OK);
    }
}
