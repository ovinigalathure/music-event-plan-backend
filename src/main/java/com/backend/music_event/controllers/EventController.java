package com.backend.music_event.controllers;

import com.backend.music_event.model.Event;
import com.backend.music_event.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/events")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class EventController {

    private final EventService eventService;
    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
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
    public ResponseEntity<?> createEvent(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("date") String date,
            @RequestParam("endDate") String endDate,
            @RequestParam("venue") String venue,
            @RequestParam("price") Double price,
            @RequestParam("category") String category,
            @RequestParam("featured") Boolean featured,
            @RequestParam("totalTickets") Integer totalTickets,
            @RequestParam("soldTickets") Integer soldTickets,
            @RequestParam("image") MultipartFile image) {

        try {
            String imageName = saveImage(image);
            Event event = new Event();
            event.setName(name);
            event.setDescription(description);
            event.setDate(date);
            event.setEndDate(endDate);
            event.setVenue(venue);
            event.setPrice(price);
            event.setCategory(category);
            event.setFeatured(featured);
            event.setTotalTickets(totalTickets);
            event.setSoldTickets(soldTickets);
            event.setImagePath(imageName);

            return ResponseEntity.status(201).body(eventService.saveEvent(event));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Image upload failed");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(
            @PathVariable Long id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "venue", required = false) String venue,
            @RequestParam(value = "price", required = false) Double price,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "featured", required = false) Boolean featured,
            @RequestParam(value = "totalTickets", required = false) Integer totalTickets,
            @RequestParam(value = "soldTickets", required = false) Integer soldTickets,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        try {
            Optional<Event> existingEventOpt = eventService.getEventById(id);
            if (!existingEventOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Event existingEvent = existingEventOpt.get();

            if (name != null) existingEvent.setName(name);
            if (description != null) existingEvent.setDescription(description);
            if (date != null) existingEvent.setDate(date);
            if (endDate != null) existingEvent.setEndDate(endDate);
            if (venue != null) existingEvent.setVenue(venue);
            if (price != null) existingEvent.setPrice(price);
            if (category != null) existingEvent.setCategory(category);
            if (featured != null) existingEvent.setFeatured(featured);
            if (totalTickets != null) existingEvent.setTotalTickets(totalTickets);
            if (soldTickets != null) existingEvent.setSoldTickets(soldTickets);

            if (image != null && !image.isEmpty()) {
                String imageName = saveImage(image);
                existingEvent.setImagePath(imageName);
            }

            return ResponseEntity.ok(eventService.saveEvent(existingEvent));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Image upload failed");
        }
    }

    private String saveImage(MultipartFile image) throws IOException {
        String originalFilename = image.getOriginalFilename();
        String uniqueFilename = System.currentTimeMillis() + "_" + originalFilename;
        Path targetLocation = Paths.get("uploads/" + uniqueFilename);
        Files.copy(image.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename; // Save only the filename in DB!
    }


    // Keep existing GET and DELETE methods unchanged
    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Optional<Event> event = eventService.getEventById(id);
        return event.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
