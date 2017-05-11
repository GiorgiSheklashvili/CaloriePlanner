package home.gio.calorieplanner.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import home.gio.calorieplanner.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsCatalogFragment extends Fragment {

    public Spinner spinner;

    public ProductsCatalogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_products_catalog, container, false);
        spinner = (Spinner) rootView.findViewById(R.id.constraintSpinner);
        String[] items = new String[]{"None", "Vegetarian", "Vegan", "Raw Vegan", "Paleo"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Inflate the layout for this fragment
        return rootView;
    }

}
