package home.gio.calorieplanner.grocerieslist;

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

import com.google.gson.Gson;
import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import home.gio.calorieplanner.R;
import home.gio.calorieplanner.main.Main;

public class GroceriesListAdapter extends RecyclerView.Adapter<GroceriesListAdapter.ViewHolder> {
    public List<String> productList;
    private ViewHolderInterface callback;
    public List<Integer> positionList = new ArrayList<>();
    private List<Integer> numberOfProductsList;
    private SharedPreferences.Editor editor;

    interface ViewHolderInterface {
        void OnItemClicked(int position, boolean isChecked);

        void OnEditTextChanged(int position, int number);
    }

    public GroceriesListAdapter(List<String> productList, List<Integer> numberOfProductsList1, Activity activity, final String rowAndDay) {
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        this.numberOfProductsList = numberOfProductsList1;
        if (numberOfProductsList1.size() == 0) {
            for (int i = 0; i < productList.size(); i++) {
                numberOfProductsList.add(1);
            }
        }
//        else {
//            for (int i = 0; i < productList.size(); i++) {
//                numberOfProductsList.add(1);
//            }
//        }
        this.productList = productList;
        callback = new ViewHolderInterface() {
            @Override
            public void OnItemClicked(int position, boolean isChecked) {
                if (isChecked)
                    positionList.add(position);
                else
                    positionList.remove(position);
                Collections.sort(positionList);
            }

            @Override
            public void OnEditTextChanged(int position, int number) {
                if (numberOfProductsList.size() > position) {
                    numberOfProductsList.set(position, number);
                    Gson gson = new Gson();
                    String toJson = gson.toJson(numberOfProductsList);
                    editor.putString("numberOf" + rowAndDay, toJson);
                    editor.apply();
                }

            }
        };
    }

    @Override
    public GroceriesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.groceries_list_custom_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroceriesListAdapter.ViewHolder holder, int position) {
        holder.bind(callback);
        holder.setCheckedTextView(productList.get(position));
        holder.numberOfProductEditText.setText(String.valueOf(numberOfProductsList.get(position)));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends CheckableChildViewHolder {
        private EditText numberOfProductEditText;
        private CheckedTextView checkedTextView;
        private ViewHolderInterface listener;

        public void setCheckedTextView(String text) {
            this.checkedTextView.setText(text);
        }

        public ViewHolder(View itemView) {
            super(itemView);
            checkedTextView = (CheckedTextView) itemView.findViewById(R.id.groceries_list_item_CheckedTextView);
            numberOfProductEditText = (EditText) itemView.findViewById(R.id.number_of_product_editText);
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
            numberOfProductEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().length() != 0) {
                        listener.OnEditTextChanged(getAdapterPosition(), Integer.parseInt(s.toString()));
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
