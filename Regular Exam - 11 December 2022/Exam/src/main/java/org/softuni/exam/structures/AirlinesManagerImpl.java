package org.softuni.exam.structures;

import org.softuni.exam.entities.Airline;
import org.softuni.exam.entities.Flight;

import java.util.*;
import java.util.stream.Collector;
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

       return airlineManager.entrySet()
                .stream()
                .sorted((o1, o2) -> {
                    if (Double.compare(o2.getKey().getRating(), o1.getKey().getRating()) == 0) {
                        if (o2.getValue().size() == o1.getValue().size()) {
                            return o1.getKey().getName().compareTo(o2.getKey().getName());
                        }
                        return Integer.compare(o2.getValue().size(), o1.getValue().size());
                    }
                    return Double.compare(o2.getKey().getRating(), o1.getKey().getRating());
                }).map(Map.Entry::getKey)
                .collect(Collectors.toList());

//        return airlineManager.entrySet()
//                .stream()
//                .sorted(Collections.reverseOrder(Comparator.comparing(airlineListEntry -> airlineListEntry.getKey().getRating())))
//                .sorted(Collections.reverseOrder(Comparator.comparing(airlineListEntry -> airlineListEntry.getValue().size())))
//                .sorted(Comparator.comparing(airlineListEntry -> airlineListEntry.getKey().getName()))
//                .map(Map.Entry::getKey)
//                .collect(Collectors.toList());


    }

    @Override
    public Iterable<Airline> getAirlinesWithFlightsFromOriginToDestination(String origin, String destination) {

        return airlineManager.entrySet()
                .stream()
                .filter(airlineListEntry -> {

                    List<Flight> collect = airlineListEntry.getValue().stream().filter(flight -> !flight.isCompleted())
                            .filter(flight -> flight.getOrigin().equals(origin) && flight.getDestination().equals(destination)).collect(Collectors.toList());

                    return !collect.isEmpty();

                }).map(Map.Entry::getKey)
                .collect(Collectors.toList());

    }
}
