package home.gio.calorieplanner.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import home.gio.calorieplanner.R;
import home.gio.calorieplanner.model.Product;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity{
//    private TextView txt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainFragment mainFragment=new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_main_container,mainFragment).commit();

//        txt1=(TextView) findViewById(R.id.textView);
//        Realm realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
//        Product product= realm.createObject(Product.class,"Burger");
//        product.setAlcohol(0);
//        product.setQuantity(1);
//        product.setCarbohydrates(200);
//        product.setProtein(40);
//        product.setFat(10);
//        product.setCalories(product.totalCalories(product.getQuantity()));
//        realm.commitTransaction();
//        RealmQuery<Product> query=realm.where(Product.class);
//        query.equalTo("name","Burger");
//        RealmResults<Product> result1=query.findAll();
//        txt1.setText(result1.get(0).getName());
    }

}
