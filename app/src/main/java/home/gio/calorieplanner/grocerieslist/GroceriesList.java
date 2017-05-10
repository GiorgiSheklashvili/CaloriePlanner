package home.gio.calorieplanner.grocerieslist;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.inputmethod.InputMethodManager;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import home.gio.calorieplanner.App;
import home.gio.calorieplanner.R;
import home.gio.calorieplanner.main.Main;
import home.gio.calorieplanner.models.Person;
import home.gio.calorieplanner.models.Product;
import home.gio.calorieplanner.ui.fragments.GroceriesListFragment;

public class GroceriesList implements IGroceriesListModel {
    private FirebaseDatabase database;
    private String MESSAGE_CHILD = "Goodwill";
    private DatabaseReference databaseReference;

    @Override
    public List<Person> fillPersonList(List<Person> personList, Context context) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Person>>() {
        }.getType();
        String json = sharedPrefs.getString("oldData", null);
        personList = gson.fromJson(json, type);
        return personList;
    }

    @Override
    public List<Product> fillProductList(List<Product> productList, Context context) {
        App app = (App) context.getApplicationContext();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Main.outRetailChainList.get(0);
//        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), 0);
//        Product product = new Product();
//        product.setName("asd");
//        product.setCalories(500);
//        product.setCarbohydrates(20);
//        product.setFat(30);
//        product.setProtein(40);
//        databaseReference.push().setValue(product);
        productList = new ArrayList<>();
        return productList;
    }


}
