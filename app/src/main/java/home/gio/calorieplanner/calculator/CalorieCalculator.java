package home.gio.calorieplanner.calculator;


import home.gio.calorieplanner.Constants;

public class CalorieCalculator implements ICalorieCalculatorModel{
    private double weight;
    private boolean sex;
    private double height;
    private int age;
    private double lifestyle;
    private int difference;
    private double proteinPerPound;
    private int fatPercentage;
    private ICalorieCalculatorModel model;

    public CalorieCalculator(ICalorieCalculatorModel model) {
        this.model = model;
    }


    public CalorieCalculator(double weight, boolean sex, double height, double lifestyle, int difference, double proteinPerPound, int fatPercentage, int age) {
        this.weight = weight;
        this.sex = sex;
        this.height = height;
        this.lifestyle = lifestyle;
        this.difference = difference;
        this.proteinPerPound = proteinPerPound;
        this.fatPercentage = fatPercentage;
        this.age = age;
    }


    public double calculateBMR() {
        if (sex)
            return 10 * (weight / 2.2) + 6.25 * height - age + 5;//man
        else
            return 10 * (weight / 2.2) + 6.25 * height - age - 165;//woman
    }

    public double activityMultiplier() {
        if (lifestyle == 1.375) {
            return calculateBMR() * 1.375;
        }
        if (lifestyle == 1.55) {
            return calculateBMR() * 1.55;
        }
        if (lifestyle == 1.725) {
            return calculateBMR() * 1.725;
        }
        return 0;
    }

    public double createDifference() {
        double energyExpenditure = activityMultiplier();
        double returnDifference;
        switch (difference) {
            case 20:
                returnDifference = energyExpenditure * Constants.surplus20;
                break;
            case 0:
                returnDifference = energyExpenditure;
                break;
            case -20:
                returnDifference = energyExpenditure * Constants.deficit20;
                break;
            default:
                returnDifference = 0;
                break;

        }
        return returnDifference;
    }


    public double getAverageProtein() {
        return weight * proteinPerPound;
    }

    public double getAverageFat() {
        return (createDifference() * (fatPercentage / 100.0)) / 9;
    }

    public double getAverageCarbs() {
        return (createDifference() - getAverageProtein() * 4 - getAverageFat() * 9) / 4;
    }


    public String convertTofeetInches(String str) throws NumberFormatException {
        Double value = Double.valueOf(str);
        int feet = (int) Math.floor(value / 30.48);
        int inches = (int) Math.round((value / 2.54) - ((int) feet * 12));
        return feet + "' " + inches + "\"";
    }


    public double convertToCentimeter(String feet, String inches) {
        double heightInFeet = 0;
        double heightInInches = 0;
        try {
            if (feet != null && feet.trim().length() != 0) {
                heightInFeet = Double.parseDouble(feet);
            }
            if (inches != null && inches.trim().length() != 0) {
                heightInInches = Double.parseDouble(inches);
            }
        } catch (NumberFormatException nfe) {

        }
        return (heightInFeet * 30.48) + (heightInInches * 2.54);
    }

    public int getFeet(String feetAndInches) {
        Character feet = feetAndInches.charAt(0);
        return Character.getNumericValue(feet);
    }

    public int getInches(String feetAndInches) {
        String inches = feetAndInches.substring(2, feetAndInches.length() - 1);
        inches = inches.replaceAll("\\s+", "");
        return Integer.parseInt(inches);
    }

    public String sendInches(String feetAndInches) {
        int inchesPart = getInches(feetAndInches);
        int feetPart = getFeet(feetAndInches);
        return String.valueOf(Math.round(2.54 * (12 * feetPart + inchesPart)));
    }

}
