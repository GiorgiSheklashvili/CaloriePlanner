package home.gio.calorieplanner.productcatalog;


import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class StoreViewHolder extends GroupViewHolder {
    public TextView MenuTextView;

    public StoreViewHolder(View itemView) {
        super(itemView);
    }

    public void setMenuTextView(TextView menuTextView) {
        MenuTextView = menuTextView;
    }
}
