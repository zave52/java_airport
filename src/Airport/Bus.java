package Airport;

import java.util.ArrayList;
import java.util.List;

/**
 * Клас, що представляє автобус для перевезення пасажирів до певного пункту призначення.
 * Має обмежену місткість та може перевозити тільки сім'ї, які прямують до вказаного пункту.
 */
public class Bus {
    // Максимальна місткість автобуса (кількість пасажирів)
    private final int capacity;
    // Пункт призначення автобуса
    private final String driveTo;
    // Список сімей, які перевозяться автобусом
    private final List<Family> passengers;
    // Поточна кількість пасажирів в автобусі
    private int currentPassengerCount;

    /**
     * Створює новий автобус з вказаною місткістю та пунктом призначення.
     *
     * @param capacity місткість автобуса (максимальна кількість пасажирів)
     * @param driveTo  пункт призначення автобуса
     */
    public Bus(int capacity, String driveTo) {
        this.capacity = capacity;
        this.driveTo = driveTo;
        this.passengers = new ArrayList<>();
        this.currentPassengerCount = 0;
    }

    /**
     * Повертає місткість автобуса.
     *
     * @return місткість автобуса
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Повертає пункт призначення автобуса.
     *
     * @return пункт призначення
     */
    public String getDriveTo() {
        return driveTo;
    }

    /**
     * Повертає список сімей, які перевозяться автобусом.
     *
     * @return список сімей
     */
    public List<Family> getPassengers() {
        return passengers;
    }

    /**
     * Повертає поточну кількість пасажирів в автобусі.
     *
     * @return поточна кількість пасажирів
     */
    public int getCurrentPassengerCount() {
        return currentPassengerCount;
    }

    /**
     * Додає сім'ю до автобуса, якщо вона прямує до того ж пункту призначення
     * і в автобусі достатньо місця для всіх членів сім'ї.
     * Метод синхронізований для безпечного доступу з різних потоків.
     *
     * @param family сім'я для додавання
     * @return true, якщо сім'я була успішно додана, false - якщо ні
     */
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

    /**
     * Обчислює залишкову місткість автобуса (скільки ще пасажирів можна додати).
     *
     * @return залишкова місткість
     */
    public int getRemainingCapacity() {
        return capacity - currentPassengerCount;
    }

    /**
     * Перевіряє, чи автобус повністю заповнений.
     *
     * @return true, якщо автобус повний, false - якщо є вільні місця
     */
    public boolean isFull() {
        return currentPassengerCount == capacity;
    }

    /**
     * Повертає рядкове представлення об'єкта Bus.
     *
     * @return рядок з інформацією про автобус
     */
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
