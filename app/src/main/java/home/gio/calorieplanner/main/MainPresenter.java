package home.gio.calorieplanner.main;


import android.content.Context;
import android.util.SparseArray;

import java.lang.ref.WeakReference;
import java.util.List;

public class MainPresenter implements IMainPresenter {
    WeakReference<IMainView> view;
    Main model;

    public MainPresenter(IMainView view) {
        this.view = new WeakReference<IMainView>(view);
        this.model = new Main();
    }

    private IMainView getView() throws NullPointerException {
        if (view != null)
            return view.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    @Override
    public void parseGoodwillSakvebiProductebiHTML(Context context) {
        model.parseGoodwillSakvebiProductebiHTML(context);
    }

    @Override
    public void loadDataFromDatabase(Context context) {
        model.loadDataFromDatabase(context);


    }

}
