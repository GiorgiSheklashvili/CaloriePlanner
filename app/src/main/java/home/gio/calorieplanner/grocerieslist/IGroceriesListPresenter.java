package home.gio.calorieplanner.grocerieslist;


import android.content.Context;

import java.util.List;

import home.gio.calorieplanner.models.Person;
import home.gio.calorieplanner.models.Product;

public interface IGroceriesListPresenter {
    public void fillPersonsList(List<Person> personList, Context context);

    void fillProductList(List<Product> productList,Context context);
}
