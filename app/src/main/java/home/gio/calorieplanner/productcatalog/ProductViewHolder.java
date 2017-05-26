package home.gio.calorieplanner.productcatalog;


import android.view.View;
import android.widget.Checkable;
import android.widget.CheckedTextView;

import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;

import home.gio.calorieplanner.R;
import home.gio.calorieplanner.models.Product;

public class ProductViewHolder extends CheckableChildViewHolder {
    private CheckedTextView checkedTextView;


    public void setCheckedTextView(String checkedTextView) {
        this.checkedTextView.setText(checkedTextView);
    }

    public ProductViewHolder(View itemView) {
        super(itemView);
        checkedTextView = (CheckedTextView) itemView.findViewById(R.id.menu_item_CheckedTextView);
    }

    @Override
    public Checkable getCheckable() {
        return checkedTextView;
    }
}
