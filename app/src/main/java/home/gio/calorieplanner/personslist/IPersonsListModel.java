package home.gio.calorieplanner.personslist;


import android.content.Context;

import java.util.List;

import home.gio.calorieplanner.models.Person;

public interface IPersonsListModel {
    List<Person> personListRefill(List<Person> persons, Context context);
}
