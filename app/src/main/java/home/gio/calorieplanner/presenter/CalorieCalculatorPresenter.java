package home.gio.calorieplanner.presenter;


import java.lang.ref.WeakReference;

import home.gio.calorieplanner.MVP_Interfaces;

public class CalorieCalculatorPresenter implements MVP_Interfaces.ProvidedPresenterOperations, MVP_Interfaces.RequiredPresenterOperations {
    WeakReference<MVP_Interfaces.RequiredViewOperations> mView;
    private MVP_Interfaces.ProvidedModelOperations mModel;

    public CalorieCalculatorPresenter(MVP_Interfaces.RequiredViewOperations mView) {
        this.mView = new WeakReference<>(mView);
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
    public void onConfigurationChanged(MVP_Interfaces.RequiredViewOperations view) {
        mView = new WeakReference<>(view);
    }
}
