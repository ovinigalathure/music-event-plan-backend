package com.backend.music_event.services;

import com.backend.music_event.repositories.EventRepository;
import com.backend.music_event.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    // Get all events
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Get event by ID
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    // Save a new event or update an existing event
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    // Delete an event by ID
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    // Update an existing event
    public Event updateEvent(Long id, Event updatedEvent) {
        Optional<Event> existingEventOpt = eventRepository.findById(id);

        if (existingEventOpt.isPresent()) {
            Event existingEvent = existingEventOpt.get();

            // Update event fields with the values from the updatedEvent object
            existingEvent.setName(updatedEvent.getName());
            existingEvent.setDescription(updatedEvent.getDescription());
            existingEvent.setDate(updatedEvent.getDate());
            existingEvent.setEndDate(updatedEvent.getEndDate());
            existingEvent.setVenue(updatedEvent.getVenue());
            existingEvent.setPrice(updatedEvent.getPrice());
            existingEvent.setCategory(updatedEvent.getCategory());
            existingEvent.setFeatured(updatedEvent.getFeatured());
            existingEvent.setTotalTickets(updatedEvent.getTotalTickets());
            existingEvent.setSoldTickets(updatedEvent.getSoldTickets());

            // If there is a new image, update it
            if (updatedEvent.getImagePath() != null) {
                existingEvent.setImagePath(updatedEvent.getImagePath());  // Save the new image path
            }

            // Save and return the updated event
            return eventRepository.save(existingEvent);
        } else {
            return null;  // Event not found
        }
    }
}
