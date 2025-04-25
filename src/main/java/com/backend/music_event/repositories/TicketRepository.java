package com.backend.music_event.repositories;

import com.backend.music_event.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    // Fetch tickets based on eventId
    List<Ticket> findByEventId(Long eventId);
}
