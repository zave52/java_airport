package Airport;

import java.util.*;

public class Controller {
    private static final String[] CITIES = {"Kalush", "Kosiv", "Galych", "Kolomiya"};
    private static final int[] BUS_CAPACITIES = {6, 7, 8};
    private static final Random random = new Random();

    private static final Map<String, List<Bus>> currentBusByDestination = new HashMap<>();
    private static final List<Bus> allBuses = Collections.synchronizedList(new ArrayList<>());

    public static List<Plane> createPlanes(int count) {
        List<Plane> planes = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            Plane plane = new Plane(i);
            fillPlaneWithFamilies(plane);
            planes.add(plane);
        }

        return planes;
    }

    private static void fillPlaneWithFamilies(Plane plane) {
        int totalPassengers = 0;
        char firstChar = 'a';
        char secondChar = 'a';

        while (totalPassengers < 100) {
            String name = "" + firstChar + secondChar;

            secondChar++;
            if (secondChar > 'z') {
                secondChar = 'a';
                firstChar++;
            }

            String city = CITIES[random.nextInt(CITIES.length)];

            int remainingSeats = 100 - totalPassengers;
            int maxFamilySize = Math.min(4, remainingSeats);
            int familySize = maxFamilySize == 1 ? 1 : 1 + random.nextInt(maxFamilySize);

            Family family = new Family(name, city, familySize);
            plane.addFamily(family);

            totalPassengers += familySize;
        }
    }

    public static void distributeFamilyToBus(Family family) {
        String destination = family.travelTo();

        List<Bus> busesList;

        synchronized (currentBusByDestination) {
            busesList = currentBusByDestination.get(destination);
        }

        if (busesList == null) {
            busesList = new ArrayList<>();
            Bus newBus = createBus(destination);
            busesList.add(newBus);
            synchronized (currentBusByDestination) {
                currentBusByDestination.put(destination, busesList);
            }
        }

        boolean added = false;

        synchronized (busesList) {
            List<Bus> busesToRemove = new ArrayList<>();

            for (Bus bus : busesList) {
                if (bus.addFamily(family)) {
                    added = true;
                    if (bus.isFull()) {
                        busesToRemove.add(bus);
                    }
                    break;
                }
            }

            if (!busesToRemove.isEmpty()) {
                busesList.removeAll(busesToRemove);
            }
        }

        if (!added) {
            Bus newBus = createBus(destination);
            boolean addedToNewBus = newBus.addFamily(family);

            if (addedToNewBus) {
                synchronized (busesList) {
                    busesList.add(newBus);
                }
                added = true;
            }

            assert added : "Failed to add family to any bus, including a new one. Family: " + family;
        }
    }

    public static Bus createBus(String destination) {
        int randomCapacity = BUS_CAPACITIES[random.nextInt(BUS_CAPACITIES.length)];
        Bus bus = new Bus(randomCapacity, destination);
        allBuses.add(bus);
        return bus;
    }

    public static List<Bus> getAllBuses() {
        return allBuses;
    }

    public static void printResults(List<Plane> planes, List<Bus> buses) {
        System.out.println("=== PLANES ===");
        for (Plane plane : planes) {
            System.out.println(plane);
            System.out.println("Families on plane " + plane.getId() + ":");
            for (Family family : plane.getFamilies()) {
                System.out.println("  " + family);
            }
            System.out.println();
        }

        System.out.println("=== BUSES ===");
        for (int i = 0; i < buses.size(); i++) {
            Bus bus = buses.get(i);
            System.out.println("Bus " + (i + 1) + ": " + bus);
            System.out.println("Families on bus " + (i + 1) + ":");
            for (Family family : bus.getPassengers()) {
                System.out.println("  " + family);
            }
            System.out.println();
        }

        int totalBuses = buses.size();
        int fullBuses = 0;
        for (Bus bus : buses) {
            if (bus.isFull()) {
                fullBuses++;
            }
        }

        System.out.println("=== STATISTICS ===");
        System.out.println("Total planes: " + planes.size());
        System.out.println("Total passengers: " + (planes.size() * 100));
        System.out.println("Total buses: " + totalBuses);
        System.out.println("Full buses: " + fullBuses);
        System.out.println("Buses with empty seats: " + (totalBuses - fullBuses));
    }
}
