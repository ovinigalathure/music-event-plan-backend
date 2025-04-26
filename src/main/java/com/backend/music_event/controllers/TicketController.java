package com.backend.music_event.controllers;

import com.backend.music_event.model.Ticket;
import com.backend.music_event.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "http://localhost:5173")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/book")
    public Ticket bookTicket(@RequestBody Ticket ticketDTO) {
        return ticketService.bookTicket(ticketDTO);
    }

    @GetMapping("/event/{eventId}")
    public List<Ticket> getTicketsByEvent(@PathVariable Long eventId) {
        return ticketService.getTicketsByEvent(eventId);
    }

    @GetMapping("/{ticketId}")
    public Ticket getTicketById(@PathVariable Long ticketId) {
        return ticketService.getTicketById(ticketId);
    }
    @GetMapping("/{ticketId}")
    public Ticket getTicketById(@PathVariable Long ticketId, @RequestParam Long userId) {
        return ticketService.getTicketByIdAndUser(ticketId, userId);
    }

}
