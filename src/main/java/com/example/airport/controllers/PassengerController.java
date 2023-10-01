package com.example.airport.controllers;

import com.example.airport.model.Passenger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/passengers")
public class PassengerController {

    private final List<Passenger> passengers;

    public PassengerController() {
        try {
            String strDate1 = "15.11.2000";
            DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            Date date1 = formatter.parse(strDate1);

            String strDate2 = "01.01.2001";
            Date date2 = formatter.parse(strDate2);

            String strDate3 = "05.05.2005";
            Date date3 = formatter.parse(strDate2);


            Passenger u1 = new Passenger(1L, 1111111111L, date1, true);
            Passenger u2 = new Passenger(2L, 2222222222L, date2, false);
            Passenger u3 = new Passenger(3L, 3333333333L, date3, false);

            passengers = List.of(u1, u2,u3);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
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
}