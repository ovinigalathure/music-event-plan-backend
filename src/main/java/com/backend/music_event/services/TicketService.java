package com.backend.music_event.services;

import com.backend.music_event.repositories.EventRepository;
import com.backend.music_event.model.Ticket;
import com.backend.music_event.model.Event;
import com.backend.music_event.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketRepository ticketRepository;

    private static final double SERVICE_FEE_PERCENTAGE = 0.05;  // 5% service fee

    public Ticket bookTicket(Ticket ticketDTO) {
        // Fetch the event details from the database
        Event event = eventRepository.findById(ticketDTO.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Calculate total price, service fee, and grand total
        double totalPrice = ticketDTO.getQuantity() * event.getPrice();
        double serviceFee = totalPrice * SERVICE_FEE_PERCENTAGE;
        double grandTotal = totalPrice + serviceFee;

        // Create a new Ticket object and save it to the database
        Ticket ticket = new Ticket();
        ticket.setEventId(ticketDTO.getEventId());
        ticket.setQuantity(ticketDTO.getQuantity());
        ticket.setTotalPrice(totalPrice);
        ticket.setServiceFee(serviceFee);
        ticket.setGrandTotal(grandTotal);

        // Save the ticket to the database (ID will be auto-generated)
        Ticket savedTicket = ticketRepository.save(ticket);

        // Return the saved ticket, now including the auto-generated ID
        return savedTicket;
    }

    public List<Ticket> getTicketsByEvent(Long eventId) {
        // Use the repository to find tickets by eventId
        return ticketRepository.findByEventId(eventId);
    }
}
