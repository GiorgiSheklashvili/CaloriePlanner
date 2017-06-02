package home.gio.calorieplanner.grocerieslist;

import android.content.Context;

import java.util.List;

import home.gio.calorieplanner.models.Person;
import home.gio.calorieplanner.models.Product;


public interface IGroceriesListView {
    void fillPersonList(List<Person> personList);
    void fillProductList(List<String> productList);
}
