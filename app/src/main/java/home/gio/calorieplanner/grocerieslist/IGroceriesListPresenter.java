package home.gio.calorieplanner.grocerieslist;


import android.app.Activity;
import android.content.Context;

import java.util.List;

import home.gio.calorieplanner.models.Person;
import home.gio.calorieplanner.models.Product;

public interface IGroceriesListPresenter {
    void fillPersonsList(List<Person> personList, Context context);
    void fillProductsList(Activity activity,String position);
    void fillNumbersList(Activity activity, String position);
}
