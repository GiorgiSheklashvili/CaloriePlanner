package home.gio.calorieplanner.personslist;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import home.gio.calorieplanner.R;
import home.gio.calorieplanner.models.Person;

public class PersonsList implements IPersonsListModel {

    @Override
    public List<Person> personListRefill(List<Person> persons, Context context) {
        if (persons == null) {
            persons = new ArrayList<>();
        }

        SharedPreferences sharedPrefs = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("newData", null);
        Type type = new TypeToken<ArrayList<Person>>() {
        }.getType();
        ArrayList<Person> newArrayList = gson.fromJson(json, type);
        if (persons.size() == 0) {
            String secondJson = sharedPrefs.getString("oldData", null);
            ArrayList<Person> oldArrayList = gson.fromJson(secondJson, type);
            if (oldArrayList != null) {
                for (int i = 0; i < oldArrayList.size(); i++) {
                    persons.add(oldArrayList.get(i));
                }
            }
        }
        if (newArrayList != null) {
            for (int i = 0; i < newArrayList.size(); i++) {
                if (!persons.contains(newArrayList.get(i))) {
                    persons.add(newArrayList.get(i));
                }

            }
            SharedPreferences.Editor editor = sharedPrefs.edit();
            String toJson = gson.toJson(persons);
            editor.putString("oldData", toJson);
            editor.commit();
        }
        return persons;
    }
}
