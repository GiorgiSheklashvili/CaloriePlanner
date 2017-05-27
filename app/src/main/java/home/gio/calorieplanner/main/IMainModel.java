package home.gio.calorieplanner.main;


import android.content.Context;
import android.util.SparseArray;

import java.util.List;

public interface IMainModel {
    void parseGoodwillSakvebiProductebiHTML(Context context);

    void loadDataFromDatabase(Context context);

    List<String> asList(SparseArray<String> sparseArray);
}
