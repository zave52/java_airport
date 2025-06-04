package Airport;

import java.util.ArrayList;
import java.util.List;

public class Bus {
    private final int capacity;
    private final String driveTo;
    private final List<Family> passengers;
    private int currentPassengerCount;

    public Bus(int capacity, String driveTo) {
        this.capacity = capacity;
        this.driveTo = driveTo;
        this.passengers = new ArrayList<>();
        this.currentPassengerCount = 0;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getDriveTo() {
        return driveTo;
    }

    public List<Family> getPassengers() {
        return passengers;
    }

    public int getCurrentPassengerCount() {
        return currentPassengerCount;
    }

    public synchronized boolean addFamily(Family family) {
        if (family.travelTo().equals(driveTo)
                && (currentPassengerCount + family.count() <= capacity)) {
            passengers.add(family);
            currentPassengerCount += family.count();
            return true;
        } else {
            return false;
        }
    }

    public int getRemainingCapacity() {
        return capacity - currentPassengerCount;
    }

    public boolean isFull() {
        return currentPassengerCount == capacity;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "capacity=" + capacity +
                ", driveTo='" + driveTo + '\'' +
                ", currentPassengerCount=" + currentPassengerCount +
                ", families=" + passengers.size() +
                '}';
    }
}
