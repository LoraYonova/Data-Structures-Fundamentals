package org.softuni.exam.structures;

import org.softuni.exam.entities.Deliverer;
import org.softuni.exam.entities.Package;

public class DeliveriesManagerImpl implements DeliveriesManager {
    @Override
    public void addDeliverer(Deliverer deliverer) {
        
    }

    @Override
    public void addPackage(Package _package) {

    }

    @Override
    public boolean contains(Deliverer deliverer) {
        return false;
    }

    @Override
    public boolean contains(Package _package) {
        return false;
    }

    @Override
    public Iterable<Deliverer> getDeliverers() {
        return null;
    }

    @Override
    public Iterable<Package> getPackages() {
        return null;
    }

    @Override
    public void assignPackage(Deliverer deliverer, Package _package) throws IllegalArgumentException {

    }

    @Override
    public Iterable<Package> getUnassignedPackages() {
        return null;
    }

    @Override
    public Iterable<Package> getPackagesOrderedByWeightThenByReceiver() {
        return null;
    }

    @Override
    public Iterable<Deliverer> getDeliverersOrderedByCountOfPackagesThenByName() {
        return null;
    }
}
