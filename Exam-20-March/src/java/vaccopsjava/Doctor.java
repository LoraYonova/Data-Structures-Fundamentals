package vaccopsjava;

public class Doctor {

    public String name;
    public int popularity;

    public Doctor(String name, int popularity) {
        this.name = name;
        this.popularity = popularity;
    }

    public String getName() {
        return name;
    }

    public Doctor setName(String name) {
        this.name = name;
        return this;
    }

    public int getPopularity() {
        return popularity;
    }

    public Doctor setPopularity(int popularity) {
        this.popularity = popularity;
        return this;
    }
}
