package home.gio.calorieplanner.main;


import android.content.Context;

import java.lang.ref.WeakReference;

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
}
