package home.gio.calorieplanner.submenuandfilter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablecheckrecyclerview.CheckableChildRecyclerViewAdapter;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import home.gio.calorieplanner.R;
import home.gio.calorieplanner.models.Category;
import home.gio.calorieplanner.models.SubMenu;

public class CategoryAdapter extends CheckableChildRecyclerViewAdapter<CategoryViewHolder, SubMenuViewHolder> {

    public CategoryAdapter(List<Category> groups) {
        super(groups);
    }

    @Override
    public SubMenuViewHolder onCreateCheckChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_row_item_checkable, parent, false);
        return new SubMenuViewHolder(view);
    }

    @Override
    public void onBindCheckChildViewHolder(SubMenuViewHolder holder, int flatPosition, CheckedExpandableGroup group, int childIndex) {
        final SubMenu subMenu = (SubMenu) group.getItems().get(childIndex);
        holder.setSubMenuTextView(subMenu.getSubMenuText());
    }

    @Override
    public CategoryViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_row_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindGroupViewHolder(CategoryViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setCategoryTextView(group);
    }
}
