package home.gio.calorieplanner.events;


public class CalculatorEvent {
    public final boolean unit;
    public final int weight;
    public final int height;
    public final int age;
    public final boolean sex;
    public final String lifestyle;
    public final String goal;

    public CalculatorEvent(boolean unit, int weight, int height, int age, boolean sex, String lifetyle, String goal) {
        this.unit = unit;
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.sex = sex;
        this.lifestyle = lifetyle;
        this.goal = goal;
    }
}
