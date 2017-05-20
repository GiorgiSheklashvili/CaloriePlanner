package home.gio.calorieplanner.productcatalog;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.thoughtbot.expandablecheckrecyclerview.CheckableChildRecyclerViewAdapter;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import home.gio.calorieplanner.R;

import home.gio.calorieplanner.models.CategoryForProducts;
import home.gio.calorieplanner.models.Product;
import home.gio.calorieplanner.submenuandfilter.CategoryViewHolder;

public class CatalogAdapter extends CheckableChildRecyclerViewAdapter<CategoryViewHolder, ProductViewHolder> {

    public CatalogAdapter(List<CategoryForProducts> groups) {
        super(groups);
    }

    @Override
    public ProductViewHolder onCreateCheckChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_row_item_checkable, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindCheckChildViewHolder(ProductViewHolder holder, int flatPosition, CheckedExpandableGroup group, int childIndex) {
        final Product product = (Product) group.getItems().get(childIndex);
        holder.setCheckedTextView(product.getName() + " ცილა:" + product.getProtein() + " ნახშირწყლები:" + product.getCarbohydrates() + " ცხიმი:" + product.getFat() + " ფასი:" + product.getPrice());
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
