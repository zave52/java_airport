package Airport;

import java.util.*;

/**
 * Головний клас програми, який симулює роботу аеропорту.
 * Програма моделює прибуття літаків з пасажирами (сім'ями) та їх розподіл по автобусах
 * відповідно до пунктів призначення.
 */
public class Dispatcher {
    /**
     * Точка входу в програму.
     * Створює літаки з пасажирами, запускає потоки для обробки кожного літака,
     * чекає завершення всіх потоків і виводить результати.
     *
     * @param args аргументи командного рядка (не використовуються)
     */
    public static void main(String[] args) {
        // Створюємо 5 літаків з пасажирами
        List<Plane> planes = Controller.createPlanes(100);

        // Створюємо та запускаємо окремий потік для кожного літака
        List<Thread> threads = new ArrayList<>();
        for (Plane plane : planes) {
            Thread thread = new Thread(plane);
            threads.add(thread);
            thread.start();
        }

        // Чекаємо завершення всіх потоків
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Thread was interrupted while waiting: " + e.getMessage());
            }
        }

        // Отримуємо список всіх автобусів і виводимо результати
        List<Bus> buses = Controller.getAllBuses();
        Controller.printResults(planes, buses);
    }
}
