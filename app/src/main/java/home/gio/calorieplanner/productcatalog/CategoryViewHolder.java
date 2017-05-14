package home.gio.calorieplanner.productcatalog;


import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import home.gio.calorieplanner.R;

public class CategoryViewHolder extends GroupViewHolder {
    public TextView categoryTextView;

    public void setCategoryTextView(ExpandableGroup category) {
        categoryTextView.setText(category.getTitle());
    }

    public CategoryViewHolder(View itemView) {
        super(itemView);
        categoryTextView = (TextView) itemView.findViewById(R.id.menu_item_textView);
        categoryTextView.setTypeface(null,Typeface.BOLD);
    }
}
