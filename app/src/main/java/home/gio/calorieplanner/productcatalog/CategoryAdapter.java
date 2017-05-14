package home.gio.calorieplanner.productcatalog;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import home.gio.calorieplanner.R;
import home.gio.calorieplanner.models.Category;
import home.gio.calorieplanner.models.SubMenu;

public class CategoryAdapter extends ExpandableRecyclerViewAdapter<CategoryViewHolder, SubMenuViewHolder> {

    public CategoryAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public CategoryViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_row_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public SubMenuViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_row_item, parent, false);
        return new SubMenuViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(SubMenuViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final SubMenu subMenu = ((Category) group).getItems().get(childIndex);
        holder.setSubMenuTextView(subMenu.getSubMenuText());
    }

    @Override
    public void onBindGroupViewHolder(CategoryViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setCategoryTextView(group);
    }
}
