package tripadministratorjava;

public class Company {

    public String name;
    public int tripOrganizationLimit;

    public Company(String name, int tripOrganizationLimit) {
        this.name = name;
        this.tripOrganizationLimit = tripOrganizationLimit;
    }

    public String getName() {
        return name;
    }

    public Company setName(String name) {
        this.name = name;
        return this;
    }

    public int getTripOrganizationLimit() {
        return tripOrganizationLimit;
    }

    public Company setTripOrganizationLimit(int tripOrganizationLimit) {
        this.tripOrganizationLimit = tripOrganizationLimit;
        return this;
    }
}
