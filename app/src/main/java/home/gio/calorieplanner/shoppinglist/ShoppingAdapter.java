package home.gio.calorieplanner.shoppinglist;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import home.gio.calorieplanner.R;
import home.gio.calorieplanner.main.Main;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder> {
    public List<String> productList;
    public List<String> numberedProductList=new ArrayList<>();
    private ViewHolderInterface callback;
    public List<Integer> positionList = new ArrayList<>();
    private List<Integer> numbersList = new ArrayList<>();
    private SharedPreferences.Editor editor;

    interface ViewHolderInterface {
        void OnItemClicked(int position, boolean isChecked);
    }

    public ShoppingAdapter(List<String> productList, Activity activity) {
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        this.productList = productList;
        for (int i = 0; i < productList.size(); i++) {
            int occurrence = Collections.frequency(productList, productList.get(i));
            numbersList.add(occurrence);
            numberedProductList.add(productList.get(i));
            productList.removeAll(Collections.singleton(productList.get(i)));
        }
        callback = new ViewHolderInterface() {
            @Override
            public void OnItemClicked(int position, boolean isChecked) {
                if (isChecked)
                    positionList.add(position);
                else
                    positionList.remove(position);
                Collections.sort(positionList);
            }

        };
    }

    @Override
    public ShoppingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_row_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShoppingAdapter.ViewHolder holder, int position) {
        holder.bind(callback);
        holder.setCheckedTextView(numberedProductList.get(position));
        holder.shoppingTextView.setText(String.valueOf(numbersList.get(position)));
    }

    @Override
    public int getItemCount() {
        return numberedProductList.size();
    }

    public static class ViewHolder extends CheckableChildViewHolder {
        private TextView shoppingTextView;
        private CheckedTextView checkedTextView;
        private ViewHolderInterface listener;

        public void setCheckedTextView(String text) {
            this.checkedTextView.setText(text);
        }

        public ViewHolder(View itemView) {
            super(itemView);
            checkedTextView = (CheckedTextView) itemView.findViewById(R.id.shopping_list_item_CheckedTextView);
            shoppingTextView = (TextView) itemView.findViewById(R.id.number_of_product_TextViewText);
        }

        public void bind(final ViewHolderInterface callback) {
            listener = callback;
            checkedTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CheckedTextView) v).toggle();
                    int position = getAdapterPosition();
                    if (position >= 0) {
                        listener.OnItemClicked(position, ((CheckedTextView) v).isChecked());
                    }
                }
            });

        }

        @Override
        public Checkable getCheckable() {
            return checkedTextView;
        }
    }
}
