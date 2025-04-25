package com.backend.music_event.repositories;

import com.backend.music_event.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    // Custom query methods if necessary
}
