package home.gio.calorieplanner.presenter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import home.gio.calorieplanner.R;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private int count;


    public CustomAdapter(int count) {
        this.count = count;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.enumTextView.setText("#" + Integer.toString(position));
        holder.plusImageView.setImageResource(R.drawable.plus);
    }

    @Override
    public int getItemCount() {
        if (count == 0)
            return 1;
        else
            return count;
    }

    public int getCount() {
        return count;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView enumTextView;
        private ImageView plusImageView;
        private View middleView;

        public ViewHolder(View itemView) {
            super(itemView);
            enumTextView = (TextView) itemView.findViewById(R.id.enumTextView);
            plusImageView = (ImageView) itemView.findViewById(R.id.plusMinusImageView);
            middleView = itemView.findViewById(R.id.emptyView);
        }
    }
}
