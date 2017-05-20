package home.gio.calorieplanner.submenuandfilter;

import android.view.View;
import android.widget.Checkable;
import android.widget.CheckedTextView;


import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;

import home.gio.calorieplanner.R;

public class SubMenuViewHolder extends CheckableChildViewHolder {
    CheckedTextView subMenuTextView;

    public void setSubMenuTextView(String subMenuTextView) {
        this.subMenuTextView.setText(subMenuTextView);
    }

    public SubMenuViewHolder(View itemView) {
        super(itemView);
        subMenuTextView = (CheckedTextView) itemView.findViewById(R.id.menu_item_CheckedTextView);
    }

    @Override
    public Checkable getCheckable() {
        return subMenuTextView;
    }
}
