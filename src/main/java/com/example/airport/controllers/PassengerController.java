package com.example.airport.controllers;

import com.example.airport.model.Passenger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;


@RestController
@RequestMapping("api/passengers")
public class PassengerController {

    private final List<Passenger> passengers;

    public PassengerController() {
        passengers = new CopyOnWriteArrayList<>();
        passengers.addAll(List.of(
                new Passenger(1L, 1111111111L, true),
                new Passenger(2L, 2222222222L, false),
                new Passenger(3L, 3333333333L, false)
        ));
    }


    @GetMapping
    public List<Passenger> getPassengers() {
        return passengers;
    }

    @GetMapping("/{passenger_id}")
    public Passenger getPassengers(@PathVariable("passenger_id") Long passengerId) {
        return passengers.stream()
                .filter(passenger -> passenger.id().equals(passengerId))
                .findAny()
                .orElse(null);
    }

    @DeleteMapping("/{passenger_id}")
    public ResponseEntity<List<Passenger>>
    deletePassenger(@PathVariable("passenger_id") Long passengerId) {
        passengers.removeIf(passenger -> passenger.id().equals(passengerId));
        return new ResponseEntity<>(passengers, HttpStatus.OK);
    }


    @PutMapping("/{passenger_id}")
    public ResponseEntity<List<Passenger>> updatePassenger(
            @PathVariable("passenger_id") Long passengerId,
            @RequestBody Passenger updatedPassenger) {
        Optional<Passenger> optionalPassenger = passengers.stream().filter(passenger -> passenger.id().equals(passengerId)).findAny();
        if (optionalPassenger.isPresent()) {
            passengers.replaceAll(passenger -> passenger.id().equals(passengerId) ? updatedPassenger : passenger);
            return new ResponseEntity<>(passengers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<List<Passenger>> addPassenger(@RequestBody Passenger newPassenger) {
        passengers.add(newPassenger);
        return new ResponseEntity<>(passengers, HttpStatus.OK);
    }
}