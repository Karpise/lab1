package com.example.airport.model;

import java.util.Date;

public record Passenger(Long id, Long passport, Date birthday, boolean benefit) {
}