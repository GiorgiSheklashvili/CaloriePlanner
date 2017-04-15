package home.gio.calorieplanner.models;


import io.realm.RealmList;
import io.realm.RealmObject;

public class RetailChain extends RealmObject {
    private String name;
    private RealmList<Store> stores;
}
