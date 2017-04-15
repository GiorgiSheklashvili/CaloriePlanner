package home.gio.calorieplanner.calculator;


import java.lang.ref.WeakReference;

public class CalorieCalculatorPresenter implements ICalorieCalculatorPresenter, ICalorieCalculatorModel {
    WeakReference<ICalorieCalculatorView> mView;
    private CalorieCalculator mModel;

    public CalorieCalculatorPresenter(ICalorieCalculatorView mView) {
        this.mView = new WeakReference<>(mView);
        this.mModel = new CalorieCalculator(this);
    }

    private ICalorieCalculatorView getView() throws NullPointerException {
        if (mView != null)
            return mView.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    @Override
    public String sendInches(String inches) {
        return mModel.sendInches(inches);
    }

    @Override
    public double getCentimeters(String feet, String inches) {
        return mModel.convertToCentimeter(feet, inches);
    }

    @Override
    public double convertToCentimeter(String feet, String inches) {
        return mModel.convertToCentimeter(feet, inches);
    }

    @Override
    public String convertTofeetInches(String str) {
        return mModel.convertTofeetInches(str);
    }

    @Override
    public int getFeet(String feetAndInches) {
        return mModel.getFeet(feetAndInches);
    }

    @Override
    public int getInches(String feetAndInches) {
        return mModel.getInches(feetAndInches);
    }


}
