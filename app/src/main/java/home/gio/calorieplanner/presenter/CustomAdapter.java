package home.gio.calorieplanner.presenter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import home.gio.calorieplanner.R;
import home.gio.calorieplanner.view.GroceriesListFragment;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private int count;
    private Context context;

    public CustomAdapter(final int count, Context context) {
        this.count = count;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.plusMinusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((int) holder.plusMinusImageView.getTag() == R.drawable.plus)
                    count++;
                else
                    count--;
                notifyDataSetChanged();
            }
        });
        holder.middleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent groceriesList=new Intent(context,GroceriesListFragment.class);
                context.startActivity(groceriesList);

            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.enumTextView.setText("#" + String.valueOf(position + 1));
        if (position + 1 == count) {
            holder.plusMinusImageView.setImageResource(R.drawable.plus);
            holder.plusMinusImageView.setTag(R.drawable.plus);
        } else {
            holder.plusMinusImageView.setImageResource(R.drawable.minus);
            holder.plusMinusImageView.setTag(R.drawable.minus);
        }
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView enumTextView;
        private ImageView plusMinusImageView;
        private View middleView;

        public ViewHolder(View itemView) {
            super(itemView);
            enumTextView = (TextView) itemView.findViewById(R.id.enumTextView);
            plusMinusImageView = (ImageView) itemView.findViewById(R.id.plusMinusImageView);
            middleView = itemView.findViewById(R.id.emptyView);
        }

    }
}
