package com.backend.music_event.services;

import com.backend.music_event.repositories.EventRepository;
import com.backend.music_event.model.Ticket;
import com.backend.music_event.model.Event;
import com.backend.music_event.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketRepository ticketRepository;

    private static final double SERVICE_FEE_PERCENTAGE = 0.05;

    public Ticket bookTicket(Ticket ticketDTO) {
        Event event = eventRepository.findById(ticketDTO.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        double totalPrice = ticketDTO.getQuantity() * event.getPrice();
        double serviceFee = totalPrice * SERVICE_FEE_PERCENTAGE;
        double grandTotal = totalPrice + serviceFee;

        Ticket ticket = new Ticket();
        ticket.setEventId(ticketDTO.getEventId());
        ticket.setUserId(ticketDTO.getUserId());
        ticket.setQuantity(ticketDTO.getQuantity());
        ticket.setTotalPrice(totalPrice);
        ticket.setServiceFee(serviceFee);
        ticket.setGrandTotal(grandTotal);

        return ticketRepository.save(ticket);
    }

    public List<Ticket> getTicketsByEvent(Long eventId) {
        return ticketRepository.findByEventId(eventId);
    }

    public Ticket getTicketById(Long ticketId) {
        Optional<Ticket> ticket = ticketRepository.findById(ticketId);
        if (ticket.isPresent()) {
            return ticket.get();
        } else {
            throw new RuntimeException("Ticket not found");
        }
    }

    public Ticket getTicketByIdAndUser(Long ticketId, Long userId) {
        Optional<Ticket> ticket = ticketRepository.findById(ticketId);
        if (ticket.isPresent() && ticket.get().getUserId().equals(userId)) {
            return ticket.get();
        } else {
            throw new RuntimeException("Ticket not found or user is not authorized");
        }
    }

    public List<Ticket> getTicketsByUser(Long userId) {
        return ticketRepository.findByUserId(userId);
    }
}
