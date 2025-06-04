package Airport;

import java.util.*;
import java.util.concurrent.*;

public class Dispatcher {
    public static void main(String[] args) {
        List<Plane> planes = Controller.createPlanes(1000);

        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        for (Plane plane : planes) {
            executorService.submit(plane);
        }

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("Pool did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

        List<Bus> buses = Controller.getAllBuses();
        Controller.printResults(planes, buses);
    }
}
