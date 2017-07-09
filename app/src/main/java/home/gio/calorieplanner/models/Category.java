package home.gio.calorieplanner.models;


import com.thoughtbot.expandablecheckrecyclerview.models.MultiCheckExpandableGroup;

import java.util.List;

public class Category extends MultiCheckExpandableGroup{

    public Category(String title, List<SubMenu> items) {
        super(title, items);
    }

}
