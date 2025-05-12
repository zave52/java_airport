package Airport;

import java.util.ArrayList;
import java.util.List;

/**
 * Клас, що представляє літак з пасажирами (сім'ями).
 * Реалізує інтерфейс Runnable для обробки пасажирів у окремому потоці.
 */
public class Plane implements Runnable {
    // Список сімей на борту літака
    private final List<Family> families;
    // Унікальний ідентифікатор літака
    private final int id;

    /**
     * Створює новий літак з вказаним ідентифікатором.
     *
     * @param id ідентифікатор літака
     */
    public Plane(int id) {
        this.id = id;
        this.families = new ArrayList<>();
    }

    /**
     * Повертає список сімей на борту літака.
     *
     * @return список сімей
     */
    public List<Family> getFamilies() {
        return families;
    }

    /**
     * Додає сім'ю на борт літака.
     *
     * @param family сім'я для додавання
     */
    public void addFamily(Family family) {
        families.add(family);
    }

    /**
     * Повертає ідентифікатор літака.
     *
     * @return ідентифікатор літака
     */
    public int getId() {
        return id;
    }

    /**
     * Обчислює загальну кількість пасажирів на борту літака.
     *
     * @return загальна кількість пасажирів
     */
    public int getTotalPassengers() {
        return families.stream().mapToInt(Family::count).sum();
    }

    /**
     * Метод, що виконується в окремому потоці.
     * Обробляє всі сім'ї на борту літака: додає їх до глобального списку
     * та розподіляє по автобусах відповідно до їх пунктів призначення.
     */
    @Override
    public void run() {
        System.out.println("Thread for Plane " + getId() + " started");

        // Розподіляємо сім'ї по автобусах
        for (Family family : getFamilies()) {
            Controller.distributeFamilyToBus(family);
        }

        System.out.println("Thread for Plane " + getId() + " finished");
    }

    /**
     * Повертає рядкове представлення об'єкта Plane.
     *
     * @return рядок з інформацією про літак
     */
    @Override
    public String toString() {
        return "Plane{" +
                "id=" + id +
                ", totalPassengers=" + getTotalPassengers() +
                ", families=" + families.size() +
                '}';
    }
}
