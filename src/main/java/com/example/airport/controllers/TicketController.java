package com.example.airport.controllers;

import com.example.airport.model.Ticket;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("api/passengers/{passenger_id}/tickets")
public class TicketController {

    private final List<Ticket> tickets;

    public TicketController() {
        tickets = List.of(new Ticket(1L, 1L, "s7"),
                new Ticket(2L, 2L, "+79998888888"));
    }

    @GetMapping()
    public List<Ticket> getPhoneNumbers(@PathVariable("passenger_id") Long passengerId) {
        return tickets.stream()
                .filter(ticket -> ticket.passengerId().equals(passengerId))
                .toList();
    }
}