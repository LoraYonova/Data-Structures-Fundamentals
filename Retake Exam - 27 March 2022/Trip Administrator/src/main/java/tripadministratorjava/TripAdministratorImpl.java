package tripadministratorjava;

import java.util.*;
import java.util.stream.Collectors;

public class TripAdministratorImpl implements TripAdministrator {

    private Map<Company, List<Trip>> tripAdministrator;
    private List<Trip> trip;

    public TripAdministratorImpl() {
        tripAdministrator = new HashMap<>();
        trip = new ArrayList<>();
    }

    @Override
    public void addCompany(Company c) {
        if (tripAdministrator.containsKey(c)) {
            throw new IllegalArgumentException();
        }
        tripAdministrator.put(c, new ArrayList<>());
    }

    @Override
    public void addTrip(Company c, Trip t) {

        if (!tripAdministrator.containsKey(c)) {
            throw new IllegalArgumentException();
        }

        if (trip.contains(t)) {
            throw new IllegalArgumentException();
        }

        tripAdministrator.get(c).add(t);
        trip.add(t);
    }

    @Override
    public boolean exist(Company c) {
        return tripAdministrator.containsKey(c);
    }

    @Override
    public boolean exist(Trip t) {
        return trip.contains(t);
    }

    @Override
    public void removeCompany(Company c) {

        if (!tripAdministrator.containsKey(c)) {
            throw new IllegalArgumentException();
        }

        List<Trip> trips = tripAdministrator.get(c);
        tripAdministrator.remove(c);
        trip.removeAll(trips);
    }

    @Override
    public Collection<Company> getCompanies() {
        return tripAdministrator.keySet();
    }

    @Override
    public Collection<Trip> getTrips() {
        return trip;
    }

    @Override
    public void executeTrip(Company c, Trip t) {

        if (!tripAdministrator.containsKey(c)) {
            throw new IllegalArgumentException();
        }
        List<Trip> trips = tripAdministrator.get(c);

        if(!trips.contains(t)) {
            throw new IllegalArgumentException();
        }

        tripAdministrator.get(c).remove(t);
        trip.remove(t);
    }

    @Override
    public Collection<Company> getCompaniesWithMoreThatNTrips(int n) {

       return tripAdministrator.entrySet()
                .stream().filter(companyListEntry -> companyListEntry.getValue().size() > n)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

    }

    @Override
    public Collection<Trip> getTripsWithTransportationType(Transportation t) {
        return trip.stream().filter(tr -> tr.getTransportation().equals(t)).collect(Collectors.toList());
    }

    @Override
    public Collection<Trip> getAllTripsInPriceRange(int lo, int hi) {
        return trip.stream().filter(tr -> tr.price >= lo && tr.price <= hi).collect(Collectors.toList());
    }
}
