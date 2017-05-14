package home.gio.calorieplanner.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class Category extends ExpandableGroup<SubMenu> {

    public Category(String title, List<SubMenu> items) {
        super(title, items);
    }

    protected Category(Parcel in) {
        super(in);
    }
}
