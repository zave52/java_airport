package Airport;

public record Family(String name, String travelTo, int count) {

    @Override
    public String toString() {
        return "Family{" +
                "name='" + name + '\'' +
                ", travelTo='" + travelTo + '\'' +
                ", count=" + count +
                '}';
    }
}