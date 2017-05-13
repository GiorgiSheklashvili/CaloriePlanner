package home.gio.calorieplanner.grocerieslist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import home.gio.calorieplanner.R;
import home.gio.calorieplanner.models.Product;

public class GroceriesListAdapter extends FirebaseRecyclerAdapter<Product, GroceriesListAdapter.ViewHolder> {
    private List<Product> productList;

    public GroceriesListAdapter(int modelLayout, DatabaseReference ref, List<Product> productList) {
        super(Product.class, modelLayout, GroceriesListAdapter.ViewHolder.class, ref);
        this.productList = productList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.groceries_list_custom_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    protected void populateViewHolder(ViewHolder viewHolder, Product model, int position) {
        viewHolder.name.setText(model.getName());
        viewHolder.price.setText(String.valueOf(model.getPrice()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.productName)
        public TextView name;
        @BindView(R.id.productPrice)
        public TextView price;
        @BindView(R.id.deleteProduct)
        public ImageView delete;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
