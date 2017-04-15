package home.gio.calorieplanner.models;

import io.realm.RealmList;
import io.realm.RealmObject;


public class Store extends RealmObject {
    private String address;
    private RealmList<Product> products;

}
