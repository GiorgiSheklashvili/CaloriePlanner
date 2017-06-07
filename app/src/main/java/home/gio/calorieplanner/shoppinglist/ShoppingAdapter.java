package home.gio.calorieplanner.shoppinglist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.CheckedTextView;

import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import home.gio.calorieplanner.R;
import home.gio.calorieplanner.main.Main;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder> {
    public List<String> productList;
    private OnItemClickedListener callback;
    public List<Integer> positionList = new ArrayList<>();


    interface OnItemClickedListener {
        void OnItemClicked(int position, boolean isChecked);
    }

    public ShoppingAdapter(List<String> productList) {
        this.productList = productList;
        callback = new OnItemClickedListener() {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_row_item_checkable, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShoppingAdapter.ViewHolder holder, int position) {
        holder.bind(callback);
        holder.setCheckedTextView(productList.get(position));

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends CheckableChildViewHolder {
        private CheckedTextView checkedTextView;

        private OnItemClickedListener listener;


        public void setCheckedTextView(String text) {
            this.checkedTextView.setText(text);
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
