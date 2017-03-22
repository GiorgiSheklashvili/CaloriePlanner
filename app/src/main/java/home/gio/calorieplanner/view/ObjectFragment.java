package home.gio.calorieplanner.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import home.gio.calorieplanner.R;

public class ObjectFragment extends Fragment {
    public static final String ARG_OBJECT = "object";

    public ObjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_object, container, false);
        String[] items = new String[]{"None", "Vegetarian", "Vegan", "Raw Vegan", "Paleo"};
        Spinner spinner = (Spinner) rootView.findViewById(R.id.contraintSpinner);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Bundle args = getArguments();
        return rootView;
    }

}
