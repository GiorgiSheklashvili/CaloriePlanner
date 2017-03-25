package home.gio.calorieplanner.model;

import home.gio.calorieplanner.Constants;

public class CalorieCalculator {
    private double weight;
    private boolean sex;
    private double height;
    private int age;
    private double lifestyle;
    private int difference;
    private int proteinPerPound;
    private int fatPercentage;
    private int carbPercentage;

    public CalorieCalculator(double weight, boolean sex, double height, double lifestyle, int difference, int proteinPerPound, int fatPercentage, int carbPercentage, int age) {
        this.weight = weight;
        this.sex = sex;
        this.height = height;
        this.lifestyle = lifestyle;
        this.difference = difference;
        this.proteinPerPound = proteinPerPound;
        this.fatPercentage = fatPercentage;
        this.carbPercentage = carbPercentage;
        this.age = age;
    }

    public double calculateBMR() {
        if (sex)
            return 10 * (weight / 2.2) + 6.25 * (height * 2.54) - age + 5;//man
        else
            return 10 * (weight / 2.2) + 6.25 * (height * 2.54) - age - 165;//woman
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
        switch (difference) {
            case 20:
                return energyExpenditure * Constants.surplus20;
            case 0:
                return energyExpenditure;
            case -20:
                return energyExpenditure * Constants.deficit20;
            default:
                return 0;

        }
    }

    public double getAverageProtein() {
        return createDifference() * 2.2 * proteinPerPound;
    }

    public double getAverageFat() {
        return (createDifference() * fatPercentage) / 9;
    }

    public double getAverageCarbs() {
        return (createDifference() - getAverageProtein() * 4 - getAverageFat() * 9) / 4;
    }

}
