package home.gio.calorieplanner.grocerieslist;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import home.gio.calorieplanner.R;
import home.gio.calorieplanner.models.Person;

public class GroceriesList implements IGroceriesListModel {


    public List<Person> fillPersonList(List<Person> personList,Context context) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Person>>() {
        }.getType();
        String json = sharedPrefs.getString("oldData", null);
        personList = gson.fromJson(json, type);
        return personList;
    }

}
