package Airport;

import java.util.*;

/**
 * Клас-контролер, який керує створенням та розподілом літаків, автобусів та сімей.
 * Відповідає за логіку розподілу пасажирів по автобусах та збір статистики.
 */
public class Controller {
    // Список можливих міст призначення
    private static final String[] CITIES = {"Kalush", "Kosiv", "Galych", "Kolomiya"};
    // Можливі місткості автобусів
    private static final int[] BUS_CAPACITIES = {6, 7, 8};
    // Генератор випадкових чисел для створення різноманітних даних
    private static final Random random = new Random();

    // Мапа, яка зберігає поточний автобус для кожного напрямку
    private static final Map<String, Bus> currentBusByDestination = new HashMap<>();
    // Синхронізований список всіх автобусів
    private static final List<Bus> allBuses = Collections.synchronizedList(new ArrayList<>());

    /**
     * Створює вказану кількість літаків і заповнює їх пасажирами (сім'ями).
     *
     * @param count кількість літаків для створення
     * @return список створених літаків
     */
    public static List<Plane> createPlanes(int count) {
        List<Plane> planes = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            Plane plane = new Plane(i);
            fillPlaneWithFamilies(plane);
            planes.add(plane);
        }

        return planes;
    }

    /**
     * Заповнює літак сім'ями пасажирів до досягнення максимальної місткості (100 пасажирів).
     * Кожна сім'я отримує унікальне ім'я, випадкове місто призначення та розмір від 1 до 4 осіб.
     *
     * @param plane літак, який потрібно заповнити пасажирами
     */
    private static void fillPlaneWithFamilies(Plane plane) {
        int totalPassengers = 0;
        char firstChar = 'a';
        char secondChar = 'a';

        // Заповнюємо літак, поки не досягнемо 100 пасажирів
        while (totalPassengers < 100) {
            // Генеруємо унікальне ім'я для сім'ї
            String name = "" + firstChar + secondChar;

            // Оновлюємо символи для наступного імені
            secondChar++;
            if (secondChar > 'z') {
                secondChar = 'a';
                firstChar++;
            }

            // Вибираємо випадкове місто призначення
            String city = CITIES[random.nextInt(CITIES.length)];

            // Визначаємо розмір сім'ї (від 1 до 4, але не більше ніж залишилось місць)
            int remainingSeats = 100 - totalPassengers;
            int maxFamilySize = Math.min(4, remainingSeats);
            int familySize = maxFamilySize == 1 ? 1 : 1 + random.nextInt(maxFamilySize);

            // Створюємо сім'ю та додаємо її до літака
            Family family = new Family(name, city, familySize);
            plane.addFamily(family);

            totalPassengers += familySize;
        }
    }

    /**
     * Розподіляє сім'ю до відповідного автобуса за напрямком.
     * Якщо автобус для цього напрямку не існує або заповнений, створюється новий.
     *
     * @param family сім'я для розподілу
     */
    public static void distributeFamilyToBus(Family family) {
        String destination = family.travelTo();

        // Отримуємо поточний автобус для цього напрямку
        Bus currentBus = currentBusByDestination.get(destination);

        // Якщо автобуса немає, створюємо новий
        if (currentBus == null) {
            currentBus = createBus(destination);
        }

        // Спроба додати сім'ю до автобуса
        if (!currentBus.addFamily(family)) {
            // Якщо автобус заповнений, створюємо новий і додаємо сім'ю до нього
            currentBus = createBus(destination);
            boolean added = currentBus.addFamily(family);
            assert added : "Failed to add family to new bus";
        }
    }

    /**
     * Створює новий автобус з випадковою місткістю для вказаного напрямку.
     *
     * @param destination напрямок руху автобуса
     * @return створений автобус
     */
    public static Bus createBus(String destination) {
        int randomCapacity = BUS_CAPACITIES[random.nextInt(BUS_CAPACITIES.length)];
        Bus bus = new Bus(randomCapacity, destination);
        allBuses.add(bus);
        currentBusByDestination.put(destination, bus);
        return bus;
    }

    /**
     * Повертає список всіх створених автобусів.
     *
     * @return список всіх автобусів
     */
    public static List<Bus> getAllBuses() {
        return allBuses;
    }

    /**
     * Виводить результати симуляції: інформацію про літаки, автобуси та статистику.
     *
     * @param planes список літаків
     * @param buses  список автобусів
     */
    public static void printResults(List<Plane> planes, List<Bus> buses) {
        // Виводимо інформацію про літаки
        System.out.println("=== PLANES ===");
        for (Plane plane : planes) {
            System.out.println(plane);
            System.out.println("Families on plane " + plane.getId() + ":");
            for (Family family : plane.getFamilies()) {
                System.out.println("  " + family);
            }
            System.out.println();
        }

        // Виводимо інформацію про автобуси
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

        // Підраховуємо кількість повністю заповнених автобусів
        int totalBuses = buses.size();
        int fullBuses = 0;
        for (Bus bus : buses) {
            if (bus.isFull()) {
                fullBuses++;
            }
        }

        // Виводимо загальну статистику
        System.out.println("=== STATISTICS ===");
        System.out.println("Total planes: " + planes.size());
        System.out.println("Total passengers: " + (planes.size() * 100));
        System.out.println("Total buses: " + totalBuses);
        System.out.println("Full buses: " + fullBuses);
        System.out.println("Buses with empty seats: " + (totalBuses - fullBuses));
    }
}