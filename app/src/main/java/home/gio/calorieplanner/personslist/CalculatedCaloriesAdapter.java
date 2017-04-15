package home.gio.calorieplanner.personslist;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import home.gio.calorieplanner.R;
import home.gio.calorieplanner.models.Person;


public class CalculatedCaloriesAdapter extends RecyclerView.Adapter<CalculatedCaloriesAdapter.ViewHolder> {
    private List<Person> personList;
    private OnItemRemovedListener mCallback;
    private Context context;

    interface OnItemRemovedListener {
        void itemRemoved(int position);
    }

    public CalculatedCaloriesAdapter(final List<Person> personList, final Context context) {
        this.personList = personList;
        this.context = context;
        mCallback = new OnItemRemovedListener() {
            @Override
            public void itemRemoved(int position) {
                    personList.remove(position);
                    SharedPreferences sharedPrefs = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    Gson gson = new Gson();
                    String toJson = gson.toJson(personList);
                    editor.putString("oldData", toJson);
                    editor.commit();
                    notifyDataSetChanged();
            }
        };
    }

    @Override
    public CalculatedCaloriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calculated_calories_custom_row, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CalculatedCaloriesAdapter.ViewHolder holder, final int position) {
        holder.minus.setImageResource(R.drawable.minus);
        holder.minus.setTag(R.drawable.minus);
        holder.bind(mCallback);
        holder.name.setText(personList.get(position).getName());
        holder.protein.setText(Integer.toString(personList.get(position).getProtein()));
        holder.fat.setText(Integer.toString(personList.get(position).getFat()));
        holder.carbs.setText(Integer.toString(personList.get(position).getCarbs()));
        holder.calories.setText(Integer.toString(personList.get(position).getCalories()));

    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView calories;
        private TextView protein;
        private TextView fat;
        private TextView carbs;
        private ImageView minus;
        private OnItemRemovedListener mListener;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            calories = (TextView) itemView.findViewById(R.id.caloriesTextView);
            protein = (TextView) itemView.findViewById(R.id.proteinTextView);
            fat = (TextView) itemView.findViewById(R.id.fatTextView);
            carbs = (TextView) itemView.findViewById(R.id.carbohydratesTextView);
            minus = (ImageView) itemView.findViewById(R.id.minusImageView);

        }

        public void bind(OnItemRemovedListener listener) {
            mListener = listener;
            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position >= 0) {
                        mListener.itemRemoved(position);
                    }
                }
            });
        }
    }
}
