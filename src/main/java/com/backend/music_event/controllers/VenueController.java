package com.backend.music_event.controllers;

import com.backend.music_event.model.Venue;
import com.backend.music_event.services.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/venues")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class VenueController {

    private final VenueService venueService;
    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    public VenueController(VenueService venueService) {
        this.venueService = venueService;
        createUploadDirectory();
    }

    private void createUploadDirectory() {
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }

    @PostMapping
    public ResponseEntity<?> createVenue(
            @RequestParam("name") String name,
            @RequestParam("city") String city,
            @RequestParam("state") String state,
            @RequestParam("capacity") int capacity,
            @RequestParam("description") String description,
            @RequestParam("amenities") List<String> amenities,
            @RequestParam("image") MultipartFile image) {

        try {
            String imageName = saveImage(image);

            Venue venue = new Venue();
            venue.setName(name);
            venue.setCity(city);
            venue.setState(state);
            venue.setCapacity(capacity);
            venue.setDescription(description);
            venue.setAmenities(amenities);
            venue.setImageUrl(imageName);

            Venue savedVenue = venueService.createVenue(venue);
            return new ResponseEntity<>(savedVenue, HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Image upload failed");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVenue(
            @PathVariable Long id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "capacity", required = false) Integer capacity,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "amenities", required = false) List<String> amenities,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        try {
            Venue existingVenue = venueService.getVenueById(id)
                    .orElseThrow(() -> new RuntimeException("Venue not found"));

            if (name != null) existingVenue.setName(name);
            if (city != null) existingVenue.setCity(city);
            if (state != null) existingVenue.setState(state);
            if (capacity != null) existingVenue.setCapacity(capacity);
            if (description != null) existingVenue.setDescription(description);
            if (amenities != null) existingVenue.setAmenities(amenities);

            if (image != null && !image.isEmpty()) {
                String imageName = saveImage(image);
                existingVenue.setImageUrl(imageName);
            }

            Venue updatedVenue = venueService.updateVenue(id, existingVenue);
            return ResponseEntity.ok(updatedVenue);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating venue: " + e.getMessage());
        }
    }

    private String saveImage(MultipartFile image) throws IOException {
        String originalFilename = image.getOriginalFilename();
        String uniqueFilename = System.currentTimeMillis() + "_" + originalFilename;
        Path targetLocation = Paths.get(UPLOAD_DIR + uniqueFilename);
        Files.copy(image.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }

    // Keep existing GET, DELETE, and filter methods unchanged
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenue(@PathVariable Long id) {
        venueService.deleteVenue(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // ... (other filter methods remain unchanged)
}
