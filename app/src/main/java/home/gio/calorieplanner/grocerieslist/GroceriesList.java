package home.gio.calorieplanner.grocerieslist;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import home.gio.calorieplanner.App;
import home.gio.calorieplanner.R;
import home.gio.calorieplanner.models.Person;


public class GroceriesList implements IGroceriesListModel {
    private FirebaseDatabase database;
    private String MESSAGE_CHILD = "Goodwill";
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPrefs;

    @Override
    public List<Person> fillPersonList(List<Person> personList, Context context) {
        sharedPrefs = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Person>>() {
        }.getType();
        String json = sharedPrefs.getString("oldData", null);
        personList = gson.fromJson(json, type);
        return personList;
    }

    @Override
    public List<String> fillProductsList(Activity activity, String position) {
        sharedPrefs = activity.getPreferences(Context.MODE_PRIVATE);
        String key = String.valueOf(sharedPrefs.getInt("personRow", -1)) + position;
        List<String> products = new ArrayList<>();

        if (!key.equals("")) {
            products = App.listFromGson(activity, key);
        }
        if (products != null) {
            return products;
        } else {
            return null;
        }
    }

//    public ArrayList<Integer> fillNumberList(Activity activity, String position) {
//        sharedPrefs = activity.getPreferences(Context.MODE_PRIVATE);
//        String key = "numberOf" + String.valueOf(sharedPrefs.getInt("personRow", -1)) + position;
//        Gson gson = new Gson();
//        String json = sharedPrefs.getString(key, "");
//        Type type = new TypeToken<ArrayList<String>>() {
//        }.getType();
//        ArrayList<String> numbers = gson.fromJson(json, type);
//        ArrayList<Integer> numbersInteger = new ArrayList<>();
//        if (numbers != null) {
//            for (String num : numbers) {
//                numbersInteger.add(Integer.valueOf(num));
//            }
//        }
//        if (numbersInteger.size() == 0)
//            return new ArrayList<>();
//        else
//            return numbersInteger;
//
//    }

}
