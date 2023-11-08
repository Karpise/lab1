package com.example.airport.controllers;

import com.example.airport.model.Ticket;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("api/tickets")
public class TicketController {
    private final List<Ticket> tickets;

    public TicketController() {
        tickets = new CopyOnWriteArrayList<>();
        tickets.addAll(List.of(
                new Ticket(1L, 1L, "s7"),
                new Ticket(2L, 2L, "Победа"),
                new Ticket(3L, 2L, "Аэрофлот"),
                new Ticket(4L, 1L, "s7"),
                new Ticket(5L, 3L, "Победа")
        ));
    }

    @GetMapping
    public List<Ticket> getTicket() {
        return tickets;
    }

    @PutMapping("/{ticket_id}")
    public ResponseEntity<List<Ticket>> updateTicket(
            @PathVariable("ticket_id") Long ticketId,
            @RequestBody Ticket updatedTicket) {
        Optional<Ticket> optionalTicket = tickets.stream().filter(ticket -> ticket.getId().equals(ticketId)).findFirst();
        if (optionalTicket.isPresent()) {
            tickets.replaceAll(ticket -> ticket.getId().equals(ticketId) ? updatedTicket : ticket);
            return new ResponseEntity<>(tickets, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{passenger_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTicket(@PathVariable("passenger_id") Long passengerId,
                          @RequestParam String airline) {
        Long id = ThreadLocalRandom.current().nextLong();
        Ticket ticket = new Ticket(id, passengerId, airline);
        tickets.add(ticket);
    }

    @DeleteMapping("/{ticket_id}")
    public ResponseEntity<List<Ticket>>
    deleteTicket(@PathVariable("ticket_id") Long ticketId) {
        tickets.removeIf(ticket -> ticket.getId().equals(ticketId));
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }
}