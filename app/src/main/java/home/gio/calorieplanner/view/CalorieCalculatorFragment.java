package home.gio.calorieplanner.view;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import home.gio.calorieplanner.R;
import home.gio.calorieplanner.events.CalculatorEvent;


public class CalorieCalculatorFragment extends Fragment {
    private String[] items;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private EditText weight, height, age;
    private Button calculateButton;
    private LinearLayout linearLayout;
    private Boolean unit;

    public CalorieCalculatorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_calorie_calculator, container, false);
        weight = (EditText) rootView.findViewById(R.id.weightEditText);
        height = (EditText) rootView.findViewById(R.id.heightEditText);
        age = (EditText) rootView.findViewById(R.id.ageEditText);
        items = new String[]{"Metric system", "Imperial system"};
        spinner = (Spinner) rootView.findViewById(R.id.metricsSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {
                    unit = true;
                    weight.setText("");
                    height.setText("");
                    age.setText("");
                    weight.setHint("kg");
                    height.setHint("cm");
                } else {
                    height.addTextChangedListener(new CustomTextWatcher(height));
                    unit = false;
                    weight.setText("");
                    height.setText("");
                    age.setText("");
                    weight.setHint("lbs");
                    height.setHint("inches");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        items = new String[]{"Male", "Female"};
        spinner = (Spinner) rootView.findViewById(R.id.sex);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
        items = new String[]{"Lightly active(sports 1-3 days/wk)", "Moderately active(sports 3-5 days/wk)", "Very active(sports 6-7 days/wk)"};
        spinner = (Spinner) rootView.findViewById(R.id.lifestyle);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
        items = new String[]{"Maintain weight", "Lose weight", "Gain weight"};
        spinner = (Spinner) rootView.findViewById(R.id.goal);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
        calculateButton = (Button) rootView.findViewById(R.id.calculateButton);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.nutritionFactsLayout);
        final Drawable drawable = weight.getBackground();
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (weight.getText().toString().equals(""))
                    weight.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.red_line));
                else {
                    weight.setBackground(drawable);
                }
                if (height.getText().toString().equals("")) {
                    height.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.red_line));
                } else {
                    height.setBackground(drawable);
                }
                if (age.getText().toString().equals("")) {
                    age.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.red_line));
                } else {
                    age.setBackground(drawable);
                }
                if (!weight.getText().toString().equals("") && !height.getText().toString().equals("") && !age.getText().toString().equals("")) {
                    linearLayout.setVisibility(View.VISIBLE);
                }
//                if(unit)
//                EventBus.getDefault().post(new CalculatorEvent(unit,(int)2.2*Integer.parseInt(weight.getText().toString()),Integer.parseInt(height.getText().toString()),);
//                else
//                    EventBus.getDefault().post(new CalculatorEvent(unit,weight,(double)Integer.parseInt(height.getText().toString())/2.54,);

            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
//        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
//        EventBus.getDefault().unregister(this);
    }

}
