package home.gio.calorieplanner.shoppinglist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.CheckedTextView;

import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;
import java.util.ArrayList;
import java.util.List;
import home.gio.calorieplanner.R;
import home.gio.calorieplanner.main.Main;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder> {
    private List<String> productList;
    private OnItemClickedListener callback;
    public List<Integer> positionList = new ArrayList<>();

    interface OnItemClickedListener {
        void OnItemClicked(int position);
    }

    public ShoppingAdapter(List<String> productList) {
        this.productList = productList;
        callback = new OnItemClickedListener() {
            @Override
            public void OnItemClicked(int position) {
                positionList.add(position);
            }
        };
    }

    @Override
    public ShoppingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_row_item_checkable, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShoppingAdapter.ViewHolder holder, int position) {
        holder.bind(callback);
        holder.setCheckedTextView(productList.get(position));
        holder.setCalories(Integer.parseInt(Main.getCalories(productList.get(position))));
        holder.setCarbs(Integer.parseInt(Main.getCarbs(productList.get(position))));
        holder.setCarbs(Integer.parseInt(Main.getProtein(productList.get(position))));
        holder.setCarbs(Integer.parseInt(Main.getFat(productList.get(position))));
        holder.setPrice(Main.getPrice(productList.get(position)));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends CheckableChildViewHolder {
        private CheckedTextView checkedTextView;
        private int protein;
        private int carbs;
        private int fat;
        private int calories;
        private String price;
        private OnItemClickedListener listener;


        public void setCheckedTextView(String text) {
            this.checkedTextView.setText(text);
        }

        public int getProtein() {
            return protein;
        }

        public void setProtein(int protein) {
            this.protein = protein;
        }

        public int getCarbs() {
            return carbs;
        }

        public void setCarbs(int carbs) {
            this.carbs = carbs;
        }

        public int getFat() {
            return fat;
        }

        public void setFat(int fat) {
            this.fat = fat;
        }

        public int getCalories() {
            return calories;
        }

        public void setCalories(int calories) {
            this.calories = calories;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            checkedTextView = (CheckedTextView) itemView.findViewById(R.id.menu_item_CheckedTextView);

        }

        public void bind(final OnItemClickedListener callback) {
            listener = callback;
            checkedTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CheckedTextView) v).toggle();
                    int position = getAdapterPosition();
                    if (position >= 0) {
                        listener.OnItemClicked(position);
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
