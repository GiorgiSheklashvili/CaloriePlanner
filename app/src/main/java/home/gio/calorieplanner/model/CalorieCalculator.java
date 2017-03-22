package home.gio.calorieplanner.model;

import home.gio.calorieplanner.Constants;

public class CalorieCalculator {
    private double weight;
    private boolean sex;
    private double waist;
    private double lifestyle;
    private int difference;
    private int proteinPerPound;
    private int fatPercentage;
    private int carbPercentage;

    public CalorieCalculator(double weight, boolean sex, double waist, double lifestyle, int difference, int proteinPerPound, int fatPercentage, int carbPercentage) {
        this.weight = weight;
        this.sex = sex;
        this.waist = waist;
        this.lifestyle = lifestyle;
        this.difference = difference;
        this.proteinPerPound = proteinPerPound;
        this.fatPercentage = fatPercentage;
        this.carbPercentage = carbPercentage;
    }

    public double getBodyFatPercentage() {
        if (sex)
            return 100 * (4.15 * waist - 0.082 * weight - 98.42) / weight;
        else
            return 100 * (4.15 * waist - 0.082 * weight - 76.76) / weight;
    }

    public double calculateBMR() {
        return 370 + (21.6 * ((weight - weight * (getBodyFatPercentage() / 10)) / 2.2));
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
            case 25:
                return energyExpenditure * Constants.surplus25;
            case 20:
                return energyExpenditure * Constants.surplus20;
            case 15:
                return energyExpenditure * Constants.surplus15;
            case 10:
                return energyExpenditure * Constants.surplus10;
            case 5:
                return energyExpenditure * Constants.surplus5;
            case 0:
                return energyExpenditure;
            case -25:
                return energyExpenditure * Constants.deficit25;
            case -20:
                return energyExpenditure * Constants.deficit20;
            case -15:
                return energyExpenditure * Constants.deficit15;
            case -10:
                return energyExpenditure * Constants.deficit10;
            case -5:
                return energyExpenditure * Constants.deficit5;
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
