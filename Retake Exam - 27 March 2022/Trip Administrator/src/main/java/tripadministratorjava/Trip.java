package tripadministratorjava;

public class Trip {

    public String id;
    public int peopleLimit;
    public Transportation transportation;
    public int price;

    public Trip(String id, int peopleLimit, Transportation transportation, int price) {
        this.id = id;
        this.peopleLimit = peopleLimit;
        this.transportation = transportation;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public Trip setId(String id) {
        this.id = id;
        return this;
    }

    public int getPeopleLimit() {
        return peopleLimit;
    }

    public Trip setPeopleLimit(int peopleLimit) {
        this.peopleLimit = peopleLimit;
        return this;
    }

    public Transportation getTransportation() {
        return transportation;
    }

    public Trip setTransportation(Transportation transportation) {
        this.transportation = transportation;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public Trip setPrice(int price) {
        this.price = price;
        return this;
    }
}
