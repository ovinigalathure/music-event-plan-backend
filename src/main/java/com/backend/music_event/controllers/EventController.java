package com.backend.music_event.controllers;

import com.backend.music_event.model.Event;
import com.backend.music_event.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class EventController {

    @Autowired
    private EventService eventService;

    // Path to save the uploaded images
    private static final String UPLOAD_DIR = "src/main/resources/uploads/";

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public Optional<Event> getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    @PostMapping
    public Event createEvent(
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
            @RequestParam("image") MultipartFile image) throws IOException {

        // Save image to the local uploads folder
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
        event.setImagePath(imageName);  // Store the image path in the database

        return eventService.saveEvent(event);
    }

    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable Long id,
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
                             @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        // Get the existing event
        Optional<Event> existingEventOpt = eventService.getEventById(id);
        if (existingEventOpt.isPresent()) {
            Event existingEvent = existingEventOpt.get();

            // Update event fields
            existingEvent.setName(name);
            existingEvent.setDescription(description);
            existingEvent.setDate(date);
            existingEvent.setEndDate(endDate);
            existingEvent.setVenue(venue);
            existingEvent.setPrice(price);
            existingEvent.setCategory(category);
            existingEvent.setFeatured(featured);
            existingEvent.setTotalTickets(totalTickets);
            existingEvent.setSoldTickets(soldTickets);

            // If a new image is uploaded, save it and update the image path
            if (image != null && !image.isEmpty()) {
                String imageName = saveImage(image);
                existingEvent.setImagePath(imageName);  // Update the image path
            }

            return eventService.saveEvent(existingEvent);  // Save and return the updated event
        } else {
            return null;  // Event not found
        }
    }

    // Helper method to save image to local folder
    // Helper method to save the image
    private String saveImage(MultipartFile image) throws IOException {
        String originalFilename = StringUtils.cleanPath(image.getOriginalFilename());
        Path targetLocation = Paths.get(UPLOAD_DIR + originalFilename);

        // Save the image to the upload folder
        Files.copy(image.getInputStream(), targetLocation);

        return targetLocation.toString();  // Return the image path to store in the database
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }
}
