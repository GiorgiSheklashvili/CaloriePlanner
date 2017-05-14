package home.gio.calorieplanner.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.TextView;

/**
 * Created by Gio on 14.05.2017.
 */

public class SubMenu implements Parcelable {
    String subMenuText;

    public String getSubMenuText() {
        return subMenuText;
    }

    public void setSubMenuText(String subMenuText) {
        this.subMenuText = subMenuText;
    }

    protected SubMenu(Parcel in) {
    }

    public SubMenu(String subMenuText) {
        this.subMenuText = subMenuText;
    }

    public static final Creator<SubMenu> CREATOR = new Creator<SubMenu>() {
        @Override
        public SubMenu createFromParcel(Parcel in) {
            return new SubMenu(in);
        }

        @Override
        public SubMenu[] newArray(int size) {
            return new SubMenu[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
