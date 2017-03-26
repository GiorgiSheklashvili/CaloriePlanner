package home.gio.calorieplanner.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import home.gio.calorieplanner.R;
import home.gio.calorieplanner.model.Person;

public class CalculatedCaloriesAdapter extends RecyclerView.Adapter<CalculatedCaloriesAdapter.ViewHolder> {
    private List<Person> personList;
    private OnItemRemovedListener mCallback;

    interface OnItemRemovedListener {
        void itemRemoved(int position);
    }

    public CalculatedCaloriesAdapter(final List<Person> personList) {
        this.personList = personList;
        mCallback = new OnItemRemovedListener() {
            @Override
            public void itemRemoved(int position) {
                personList.remove(position);
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public CalculatedCaloriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.calculated_calories_custom_row,parent,false);
        final ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CalculatedCaloriesAdapter.ViewHolder holder, final int position) {
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
            name=(TextView) itemView.findViewById(R.id.name);
            calories=(TextView) itemView.findViewById(R.id.caloriesTextView);
            protein=(TextView) itemView.findViewById(R.id.proteinTextView);
            fat=(TextView) itemView.findViewById(R.id.fatTextView);
            carbs=(TextView) itemView.findViewById(R.id.carbohydratesTextView);
            minus=(ImageView)itemView.findViewById(R.id.minusImageView);

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
