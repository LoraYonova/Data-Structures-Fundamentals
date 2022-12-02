package barbershopjava;

import java.util.*;
import java.util.stream.Collectors;


public class BarberShopImpl implements BarberShop {

    private final Map<Barber, List<Client>> barber;
    private final List<Client> clientList;

    public BarberShopImpl() {
        barber = new HashMap<>();
        clientList = new ArrayList<>();
    }

    @Override
    public void addBarber(Barber b) {
        if (barber.containsKey(b)) {
            throw new IllegalArgumentException();
        }
        barber.put(b, new ArrayList<>());
    }

    @Override
    public void addClient(Client c) {
        if (clientList.contains(c)) {
            throw new IllegalArgumentException();
        }
        clientList.add(c);
    }

    @Override
    public boolean exist(Barber b) {
        return barber.containsKey(b);
    }

    @Override
    public boolean exist(Client c) {
        return clientList.contains(c);
    }

    @Override
    public Collection<Barber> getBarbers() {
        return barber.keySet();
    }

    @Override
    public Collection<Client> getClients() {
        return clientList;
    }

    @Override
    public void assignClient(Barber b, Client c) {
        if (!barber.containsKey(b)) {
            throw new IllegalArgumentException();
        }

        if (!clientList.contains(c)) {
            throw new IllegalArgumentException();
        }

        barber.get(b).add(c);
        clientList.remove(c);
    }

    @Override
    public void deleteAllClientsFrom(Barber b) {

        if (!barber.containsKey(b)) {
            throw new IllegalArgumentException();
        }

        List<Client> clients = barber.get(b);
        barber.get(b).removeAll(clients);
    }

    @Override
    public Collection<Client> getClientsWithNoBarber() {
        return clientList;
    }

    @Override
    public Collection<Barber> getAllBarbersSortedWithClientsCountDesc() {

        LinkedHashMap<Barber, List<Client>> collect = barber.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(barberListEntry -> barberListEntry.getValue().size())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));
        return collect.keySet();
    }

    @Override
    public Collection<Barber> getAllBarbersSortedWithStarsDescendingAndHaircutPriceAsc() {
        Comparator<Barber> barberComparator = Comparator.comparingInt(Barber::getStars).reversed().thenComparing(Barber::getHaircutPrice);
      return barber.keySet()
                .stream()
                .sorted(barberComparator)
                .collect(Collectors.toList());

    }

    @Override
    public Collection<Client> getClientsSortedByAgeDescAndBarbersStarsDesc() {

       return clientList.stream().filter(client -> client.getBarber() != null)
                 .sorted((o1, o2) -> {
                     if (o2.getAge() - o1.getAge() == 0) {
                         return o2.getBarber().getStars() - o1.getBarber().getStars();
                     }
                     return o2.getAge() - o1.getAge();
                 }).collect(Collectors.toList());



    }
}
