package com.example.airport.model;


public record Ticket(Long id, Long passengerId, String airline) {

    public Long getId() {return id;}


}
