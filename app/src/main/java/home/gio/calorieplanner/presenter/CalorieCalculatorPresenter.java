package home.gio.calorieplanner.presenter;


import java.lang.ref.WeakReference;

import home.gio.calorieplanner.MVP_Interfaces;
import home.gio.calorieplanner.model.CalorieCalculator;

public class CalorieCalculatorPresenter implements MVP_Interfaces.ProvidedPresenterOperations, MVP_Interfaces.RequiredPresenterOperations {
    WeakReference<MVP_Interfaces.RequiredViewOperations> mView;
    private MVP_Interfaces.ProvidedModelOperations mModel;

    public CalorieCalculatorPresenter(MVP_Interfaces.RequiredViewOperations mView) {
        this.mView = new WeakReference<>(mView);
        this.mModel=new CalorieCalculator();
    }

    public void setModel(MVP_Interfaces.ProvidedModelOperations model) {
        this.mModel = model;
    }

    private MVP_Interfaces.RequiredViewOperations getView() throws NullPointerException {
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
    public double getCentimeters(String feet,String inches) {
        return mModel.convertToCentimeter(feet, inches);
    }

    @Override
    public double convertToCentimeter(String feet, String inches) {
        return mModel.convertToCentimeter(feet,inches);
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

    @Override
    public void onConfigurationChanged(MVP_Interfaces.RequiredViewOperations view) {
        mView = new WeakReference<>(view);
    }
}
