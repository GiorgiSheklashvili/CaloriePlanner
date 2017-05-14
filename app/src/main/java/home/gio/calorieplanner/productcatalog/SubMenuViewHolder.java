package home.gio.calorieplanner.productcatalog;

import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import home.gio.calorieplanner.R;

public class SubMenuViewHolder extends ChildViewHolder {
    TextView subMenuTextView;

    public void setSubMenuTextView(String subMenuTextView) {
        this.subMenuTextView.setText(subMenuTextView);
    }

    public SubMenuViewHolder(View itemView) {
        super(itemView);
        subMenuTextView = (TextView) itemView.findViewById(R.id.menu_item_textView);
    }
}
