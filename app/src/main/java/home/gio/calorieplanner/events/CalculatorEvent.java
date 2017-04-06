package home.gio.calorieplanner.events;


public class CalculatorEvent {
    public final boolean unit;
    public final int weight;
    public final int height;
    public final int age;
    public final boolean sex;
    public final double lifestyle;
    public final int difference;
    public final double proteinPerPound;

    public CalculatorEvent(boolean unit, int weight, int height, int age, boolean sex, double lifestyle, int difference, double proteinPerPound) {
        this.unit = unit;
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.sex = sex;
        this.lifestyle = lifestyle;
        this.difference = difference;
        this.proteinPerPound = proteinPerPound;
    }
}
