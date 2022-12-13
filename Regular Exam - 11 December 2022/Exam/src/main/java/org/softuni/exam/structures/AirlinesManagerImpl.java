package org.softuni.exam.structures;

import org.softuni.exam.entities.Airline;
import org.softuni.exam.entities.Flight;

import java.util.*;
import java.util.stream.Collectors;

public class AirlinesManagerImpl implements AirlinesManager {

    private Map<Airline, List<Flight>> airlineManager;
    private List<Flight> flights;

    public AirlinesManagerImpl() {
        this.airlineManager = new LinkedHashMap<>();
        this.flights = new ArrayList<>();
    }


    @Override
    public void addAirline(Airline airline) {
        airlineManager.putIfAbsent(airline, new ArrayList<>());
    }

    @Override
    public void addFlight(Airline airline, Flight flight) {

        if (!airlineManager.containsKey(airline)) {
            throw new IllegalArgumentException();
        }

        airlineManager.get(airline).add(flight);
        flights.add(flight);
    }

    @Override
    public boolean contains(Airline airline) {
        return airlineManager.containsKey(airline);
    }

    @Override
    public boolean contains(Flight flight) {
        return flights.contains(flight);
    }

    @Override
    public void deleteAirline(Airline airline) throws IllegalArgumentException {

        if (!airlineManager.containsKey(airline)) {
            throw new IllegalArgumentException();
        }

        List<Flight> flights = airlineManager.get(airline);
        airlineManager.remove(airline);
        this.flights.removeAll(flights);
    }

    @Override
    public Iterable<Flight> getAllFlights() {
        return flights;
    }

    @Override
    public Flight performFlight(Airline airline, Flight flight) throws IllegalArgumentException {
        if (!airlineManager.containsKey(airline)) {
            throw new IllegalArgumentException();
        }

        List<Flight> flights = airlineManager.get(airline);
        if (!flights.contains(flight)) {
            throw new IllegalArgumentException();
        }

        Flight perform = this.flights.stream().filter(f -> f.getId().equals(flight.getId())).findFirst().get();
        perform.setCompleted(true);
        return perform;
    }

    @Override
    public Iterable<Flight> getCompletedFlights() {
        return flights.stream().filter(Flight::isCompleted).collect(Collectors.toList());
    }

    @Override
    public Iterable<Flight> getFlightsOrderedByNumberThenByCompletion() {

       return flights.stream().sorted(Comparator.comparing(Flight::isCompleted).thenComparing(Flight::getNumber)).collect(Collectors.toList());

    }

    @Override
    public Iterable<Airline> getAirlinesOrderedByRatingThenByCountOfFlightsThenByName() {
        return null;
    }

    @Override
    public Iterable<Airline> getAirlinesWithFlightsFromOriginToDestination(String origin, String destination) {
        return null;
    }
}
