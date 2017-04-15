package home.gio.calorieplanner.grocerieslist;

import android.content.Context;

import java.util.List;

import home.gio.calorieplanner.models.Person;


public interface IGroceriesListView {
    void fillPersonList(List<Person> personList);
}
