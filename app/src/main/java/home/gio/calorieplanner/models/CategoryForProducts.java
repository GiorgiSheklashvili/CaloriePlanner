package home.gio.calorieplanner.models;

import android.os.Parcel;

import com.thoughtbot.expandablecheckrecyclerview.models.MultiCheckExpandableGroup;

import java.util.List;


public class CategoryForProducts extends MultiCheckExpandableGroup {

    public CategoryForProducts(String title, List<Product> items) {
        super(title, items);
    }

    protected CategoryForProducts(Parcel in) {
        super(in);
    }
}
