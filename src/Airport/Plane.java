package Airport;

import java.util.ArrayList;
import java.util.List;

public class Plane implements Runnable {
    private final List<Family> families;
    private final int id;

    public Plane(int id) {
        this.id = id;
        this.families = new ArrayList<>();
    }

    public List<Family> getFamilies() {
        return families;
    }

    public void addFamily(Family family) {
        families.add(family);
    }

    public int getId() {
        return id;
    }

    public int getTotalPassengers() {
        return families.stream().mapToInt(Family::count).sum();
    }

    @Override
    public void run() {
        System.out.println("Thread for Plane " + getId() + " started");

        for (Family family : getFamilies()) {
            Controller.distributeFamilyToBus(family);
        }

        System.out.println("Thread for Plane " + getId() + " finished");
    }

    @Override
    public String toString() {
        return "Plane{" +
                "id=" + id +
                ", totalPassengers=" + getTotalPassengers() +
                ", families=" + families.size() +
                '}';
    }
}
