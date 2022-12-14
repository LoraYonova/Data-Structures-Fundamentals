package org.softuni.exam.structures;

import org.softuni.exam.entities.Deliverer;
import org.softuni.exam.entities.Package;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeliveriesManagerImpl implements DeliveriesManager {

    private Map<Deliverer, List<Package>> deliverySystem;
    private List<Package> packages;
    private List<Package> unassignedPackages;

    public DeliveriesManagerImpl() {
        this.deliverySystem = new LinkedHashMap<>();
        this.packages = new ArrayList<>();
        this.unassignedPackages = new ArrayList<>();
    }

    @Override
    public void addDeliverer(Deliverer deliverer) {
        deliverySystem.put(deliverer, new ArrayList<>());
    }

    @Override
    public void addPackage(Package _package) {
        packages.add(_package);
        unassignedPackages.add(_package);
    }

    @Override
    public boolean contains(Deliverer deliverer) {
        return deliverySystem.containsKey(deliverer);
    }

    @Override
    public boolean contains(Package _package) {
        return packages.contains(_package);
    }

    @Override
    public Iterable<Deliverer> getDeliverers() {
        return deliverySystem.keySet();
    }

    @Override
    public Iterable<Package> getPackages() {
        return packages;
    }

    @Override
    public void assignPackage(Deliverer deliverer, Package _package) throws IllegalArgumentException {

        if (!deliverySystem.containsKey(deliverer)) {
            throw new IllegalArgumentException();
        }

        if (!packages.contains(_package)) {
            throw new IllegalArgumentException();
        }

        deliverySystem.get(deliverer).add(_package);
        unassignedPackages.remove(_package);
    }

    @Override
    public Iterable<Package> getUnassignedPackages() {
        return unassignedPackages;
    }

    @Override
    public Iterable<Package> getPackagesOrderedByWeightThenByReceiver() {

        Comparator<Package> packageComparator = Comparator.comparing(Package::getWeight).reversed().thenComparing(Package::getReceiver);
        return packages.stream()
                .sorted(packageComparator)
                .collect(Collectors.toList());

    }

    @Override
    public Iterable<Deliverer> getDeliverersOrderedByCountOfPackagesThenByName() {

       return deliverySystem.entrySet()
                .stream()
                .sorted((o1, o2) -> {
                    if (o2.getValue().size() == o1.getValue().size()) {
                        return o1.getKey().getName().compareTo(o2.getKey().getName());
                    }
                    return Integer.compare(o2.getValue().size(), o1.getValue().size());
                }).map(Map.Entry::getKey)
                .collect(Collectors.toList());

    }


}
