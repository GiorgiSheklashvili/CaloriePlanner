package home.gio.calorieplanner.grocerieslist;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import home.gio.calorieplanner.models.Person;
import home.gio.calorieplanner.models.Product;


public interface IGroceriesListModel {
    List<Person> fillPersonList(List<Person> personList, Context context);

    List<String> fillProductsList(Activity activity, String position);

//    ArrayList<Integer> fillNumberList(Activity activity, String position);
}
