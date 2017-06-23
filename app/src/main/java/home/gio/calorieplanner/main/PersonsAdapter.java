package home.gio.calorieplanner.main;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import home.gio.calorieplanner.R;
import home.gio.calorieplanner.ui.fragments.GroceriesViewpagerFragment;

public class PersonsAdapter extends RecyclerView.Adapter<PersonsAdapter.ViewHolder> {
    private Context context;
    private Drawable drawable;
    public List<String> personList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public PersonsAdapter(Context context, List<String> personsList) {
        this.context = context;
        this.personList = personsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        drawable = holder.nameEditText.getBackground();
        sharedPreferences = ((Activity) context).getPreferences(Context.MODE_PRIVATE);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.plusMinusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((int) holder.plusMinusImageView.getTag() == R.drawable.plus) {
                    personList.add("");
                    holder.nameEditText.setText("");
                } else {
                    sharedPreferences.edit().remove(String.valueOf(holder.getAdapterPosition()) + "0").apply();
                    sharedPreferences.edit().remove(String.valueOf(holder.getAdapterPosition()) + "1").apply();
                    sharedPreferences.edit().remove(String.valueOf(holder.getAdapterPosition()) + "1").apply();

                    if (holder.getAdapterPosition() < personList.size() - 1) {
                        for (int i = holder.getAdapterPosition() + 1; i <= personList.size() - 1; i++) {
                            for (int k = 0; k < 3; k++) {
                                if (sharedPreferences.contains(String.valueOf(i) + String.valueOf(k))) {
                                    Set<String> oldSet = sharedPreferences.getStringSet(String.valueOf(i) + String.valueOf(k), null);
                                    sharedPreferences.edit().remove(String.valueOf(i) + String.valueOf(k)).apply();
                                    sharedPreferences.edit().putStringSet(String.valueOf(i - 1) + String.valueOf(k), oldSet).apply();
                                }
                            }
                        }
                    }
                    if (personList.size() > holder.getAdapterPosition())
                        personList.remove(holder.getAdapterPosition());
                }
                notifyDataSetChanged();
            }
        });
        if (position + 1 == personList.size()) {
            holder.plusMinusImageView.setImageResource(R.drawable.plus);
            holder.plusMinusImageView.setTag(R.drawable.plus);
            holder.addProducts.setVisibility(View.INVISIBLE);
            holder.nameEditText.setVisibility(View.INVISIBLE);

        } else {
            holder.addProducts.setVisibility(View.VISIBLE);
            holder.nameEditText.setVisibility(View.VISIBLE);
            holder.plusMinusImageView.setImageResource(R.drawable.minus);
            holder.plusMinusImageView.setTag(R.drawable.minus);
            holder.nameEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() != 0) {
                        holder.nameEditText.setBackground(drawable);
                        personList.set(holder.getAdapterPosition(), holder.nameEditText.getText().toString());
                    }
                }
            });
        }
        holder.addProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.nameEditText.getText().length() == 0) {
                    holder.nameEditText.setBackground(ContextCompat.getDrawable(context, R.drawable.red_line));
                    Toast.makeText(context, "შეიყვანეთ სახელი", Toast.LENGTH_SHORT).show();
                } else {
                    GroceriesViewpagerFragment fragment = new GroceriesViewpagerFragment();
                    editor = sharedPreferences.edit();
                    editor.putInt("personRow", holder.getAdapterPosition());
                    editor.apply();
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_container, fragment,"GroceriesViewpager").addToBackStack(fragment.getClass().getName()).commit();
                }
            }
        });
        if (personList.size() > position) {
            holder.nameEditText.setText(personList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private EditText nameEditText;
        private ImageView plusMinusImageView;
        private Button addProducts;

        public ViewHolder(View itemView) {
            super(itemView);
            nameEditText = (EditText) itemView.findViewById(R.id.nameEditText);
            plusMinusImageView = (ImageView) itemView.findViewById(R.id.plusMinusImageView);
            addProducts = (Button) itemView.findViewById(R.id.addProducts);
        }

    }
}
