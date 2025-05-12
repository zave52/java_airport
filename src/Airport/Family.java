package Airport;

/**
 * Запис (record), що представляє сім'ю пасажирів.
 * Містить інформацію про ім'я сім'ї, пункт призначення та кількість осіб.
 *
 * @param name     ім'я сім'ї
 * @param travelTo пункт призначення
 * @param count    кількість осіб у сім'ї
 */
public record Family(String name, String travelTo, int count) {

    /**
     * Повертає рядкове представлення об'єкта Family.
     *
     * @return рядок з інформацією про сім'ю
     */
    @Override
    public String toString() {
        return "Family{" +
                "name='" + name + '\'' +
                ", travelTo='" + travelTo + '\'' +
                ", count=" + count +
                '}';
    }
}