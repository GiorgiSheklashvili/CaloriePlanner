package home.gio.calorieplanner.calculator;


public interface ICalorieCalculatorPresenter {
    String sendInches(String inches);
    double getCentimeters(String feet, String inches);
    double convertToCentimeter(String feet, String inches);
    String convertTofeetInches(String str);
    int getFeet(String feetAndInches);
    int getInches(String feetAndInches);

}
