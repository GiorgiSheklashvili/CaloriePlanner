package home.gio.calorieplanner.productcatalog;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import home.gio.calorieplanner.R;
import home.gio.calorieplanner.main.Main;


public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {


    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuAdapter.ViewHolder holder, int position) {
        holder.menuItem.setText(Main.outRetailChainList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return Main.outRetailChainList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.menu_item_textView)
        public TextView menuItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        @Override
        public void onClick(View v) {

        }
    }
}
