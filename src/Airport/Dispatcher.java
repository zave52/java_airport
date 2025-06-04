package Airport;

import java.util.*;

public class Dispatcher {
    public static void main(String[] args) {
        List<Plane> planes = Controller.createPlanes(100);

        List<Thread> threads = new ArrayList<>();
        for (Plane plane : planes) {
            Thread thread = new Thread(plane);
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Thread was interrupted while waiting: " + e.getMessage());
            }
        }

        List<Bus> buses = Controller.getAllBuses();
        Controller.printResults(planes, buses);
    }
}
