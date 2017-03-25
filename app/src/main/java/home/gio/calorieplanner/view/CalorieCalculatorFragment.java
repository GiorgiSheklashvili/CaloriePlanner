package home.gio.calorieplanner.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import home.gio.calorieplanner.R;


public class CalorieCalculatorFragment extends Fragment {
    String[] items;
    Spinner spinner;
    ArrayAdapter<String> adapter;

    public CalorieCalculatorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_calorie_calculator, container, false);
        items = new String[]{"Metric system", "Imperial system"};
        spinner = (Spinner) rootView.findViewById(R.id.metricsSpinner);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        items = new String[]{"Male", "Female"};
        spinner=(Spinner)rootView.findViewById(R.id.sex);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
        items = new String[]{"Lightly active(sports 1-3 days/wk)", "Moderately active(sports 3-5 days/wk)","Very active(sports 6-7 days/wk)"};
        spinner=(Spinner)rootView.findViewById(R.id.lifestyle);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
        items = new String[]{"Maintain weight", "Lose weight","Gain weight"};
        spinner=(Spinner)rootView.findViewById(R.id.goal);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        return rootView;
    }

}
