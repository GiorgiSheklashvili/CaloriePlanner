package home.gio.calorieplanner.shoppinglist;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import home.gio.calorieplanner.App;
import home.gio.calorieplanner.R;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder> {
    public List<String> productList;
    private ViewHolderInterface callback;
    public List<Integer> positionList = new ArrayList<>();
    public List<String> numbersList = new ArrayList<>();
    private int tempIndex;
    int occurrence;

    interface ViewHolderInterface {
        void OnItemClicked(int position, boolean isChecked);
    }

    public ShoppingAdapter(List<String> productList, Activity activity) {
        this.productList = new ArrayList<>(productList);
        if (App.listFromGson(activity, "numbersOfShoppingList") != null && App.listFromGson(activity, "numbersOfShoppingList").size() != 0) {
            numbersList.addAll(App.listFromGson(activity, "numbersOfShoppingList"));
        }
        for (int i = 0; i < this.productList.size(); i++) {
            occurrence = Collections.frequency(productList, productList.get(i));
            if (numbersList.size() < this.productList.size() && i >= numbersList.size()) {
                boolean containedBefore = false;
                for (int c = 0; c < numbersList.size(); c++) {
                    if (this.productList.get(c).equals(this.productList.get(i))) {
                        containedBefore = true;
                    }
                }
                if (!containedBefore) {// When added products are all new
                    numbersList.add(String.valueOf(occurrence));
                    int tempOccurrence = occurrence - 1;
                    while (tempOccurrence != 0) {
                        this.productList.remove(this.productList.get(i));
                        tempOccurrence--;
                    }
                }
            }
            if (i > numbersList.size()) {
                boolean tempNewAddedProductsSameOrNot = false;
                for (int h = i + 1; h < this.productList.size(); h++) {
                    if (this.productList.get(h).equals(this.productList.get(i))) {
                        tempNewAddedProductsSameOrNot = true;
                    }
                }
                if (!tempNewAddedProductsSameOrNot) {//Old products did not contain new product //rac daemata imis nairebi ar iyo aqamde
                    numbersList.add(String.valueOf(occurrence));
                    int tempOccurrence = occurrence - 1;
                    while (tempOccurrence != 0) {
                        this.productList.remove(this.productList.get(i));
                        tempOccurrence--;
                    }
                }
            }

            if (occurrence > 1) {
                for (int k = i + 1; k < this.productList.size(); k++) {
                    if (this.productList.get(k).equals(this.productList.get(i))) {
                        tempIndex = k;
                        break;
                    }
                }
                for (int j = this.productList.size() - 1; j >= tempIndex; j--) {
                    if (this.productList.get(j).equals(this.productList.get(i)) && i != j) {
                        this.productList.remove(j);
                        int oldNumber = Integer.valueOf(numbersList.get(i));
                        oldNumber++;
                        numbersList.set(i, String.valueOf(oldNumber));
                    }
                }
            }
        }
        App.listToGson(activity, this.productList, "shoppinglist");
        App.listToGson(activity, this.numbersList, "numbersOfShoppingList");
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
        holder.setCheckedTextView(productList.get(position));
        holder.shoppingTextView.setText(numbersList.get(position));
    }

    @Override
    public int getItemCount() {
        return productList.size();
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
