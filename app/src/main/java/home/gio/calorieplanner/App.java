package home.gio.calorieplanner;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.select.Evaluator;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import home.gio.calorieplanner.models.Person;
import home.gio.calorieplanner.models.Product;

import static java.security.AccessController.getContext;


public class App extends Application {
    private String ITEMS_CHILD_NAME = "items";
    private DatabaseReference itemsReference;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        itemsReference = database.getReference(ITEMS_CHILD_NAME);
    }

    public DatabaseReference getItemsReference() {
        return itemsReference;
    }

    public static void listToGson(Activity activity, List<String> list, String key) {
        SharedPreferences sharedPrefs = activity.getSharedPreferences(activity.getString(R.string.preference_file_key),activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String toJson = gson.toJson(list);
        editor.putString(key, toJson);
        editor.apply();
    }

    public static ArrayList<String> listFromGson(Activity activity, String key) {
        SharedPreferences sharedPrefs = activity.getSharedPreferences(activity.getString(R.string.preference_file_key),activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPrefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
