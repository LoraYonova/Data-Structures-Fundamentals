package vaccopsjava;

public class Patient {

    public String name;
    public int height;
    public int age;
    public String town;

    public Patient(String name, int height, int age, String town) {
        this.name = name;
        this.height = height;
        this.age = age;
        this.town = town;
    }

    public String getName() {
        return name;
    }

    public Patient setName(String name) {
        this.name = name;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public Patient setHeight(int height) {
        this.height = height;
        return this;
    }

    public int getAge() {
        return age;
    }

    public Patient setAge(int age) {
        this.age = age;
        return this;
    }

    public String getTown() {
        return town;
    }

    public Patient setTown(String town) {
        this.town = town;
        return this;
    }
}
