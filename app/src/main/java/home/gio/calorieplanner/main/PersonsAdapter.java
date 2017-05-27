package home.gio.calorieplanner.main;


import android.content.Context;
import android.graphics.drawable.Drawable;
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
    private int count;
    private Context context;
    private Drawable drawable;
    public SparseArray<String> personArray = new SparseArray<>();
    private List<String> personList = new ArrayList<>();


    public PersonsAdapter(final int count, Context context, Set<String> personsSet) {
        this.count = count;
        this.context = context;
        personList.addAll(personsSet);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        drawable = holder.nameEditText.getBackground();
        holder.addProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.nameEditText.getText().length() == 0) {
                    holder.nameEditText.setBackground(ContextCompat.getDrawable(parent.getContext(), R.drawable.red_line));
                    Toast.makeText(parent.getContext(), "შეიყვანეთ სახელი", Toast.LENGTH_SHORT).show();

                } else {

                    GroceriesViewpagerFragment fragment = new GroceriesViewpagerFragment();
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_container, fragment).addToBackStack(null).commit();
                }
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.plusMinusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((int) holder.plusMinusImageView.getTag() == R.drawable.plus) {
                    count++;
                    holder.nameEditText.setText("");
                } else {
                    count--;
                    if (!holder.nameEditText.getText().toString().equals("")) {
                        personArray.remove(position);
                        if (personList.size() > position)
                            personList.remove(position);
                    }
                }
                notifyDataSetChanged();
            }
        });
        if (position + 1 == count) {
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
                    if (s.length() != 0)
                        holder.nameEditText.setBackground(drawable);
                    personArray.put(position, holder.nameEditText.getText().toString());
                }
            });
        }
        if (personList.size() > position) {
            holder.nameEditText.setText(personList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return count;
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
