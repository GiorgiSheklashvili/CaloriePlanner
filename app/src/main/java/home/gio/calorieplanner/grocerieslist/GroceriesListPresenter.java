package home.gio.calorieplanner.grocerieslist;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.List;

import home.gio.calorieplanner.models.Person;
import home.gio.calorieplanner.models.Product;


public class GroceriesListPresenter implements IGroceriesListPresenter {
    WeakReference<IGroceriesListView> view;
    GroceriesList model;

    public GroceriesListPresenter(IGroceriesListView view) {
        this.view = new WeakReference<IGroceriesListView>(view);
        this.model = new GroceriesList();
    }

    private IGroceriesListView getView() throws NullPointerException {
        if (view != null)
            return view.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    @Override
    public void fillPersonsList(List<Person> personList, Context context) {
        getView().fillPersonList(model.fillPersonList(personList, context));
    }


}
