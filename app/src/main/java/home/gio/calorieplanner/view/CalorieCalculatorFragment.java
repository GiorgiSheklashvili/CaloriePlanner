package home.gio.calorieplanner.view;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.provider.DocumentFile;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import home.gio.calorieplanner.MVP_Interfaces;
import home.gio.calorieplanner.R;
import home.gio.calorieplanner.StateMaintainer;
import home.gio.calorieplanner.events.CalculatorEvent;
import home.gio.calorieplanner.model.CalorieCalculator;
import home.gio.calorieplanner.presenter.CalorieCalculatorPresenter;


public class CalorieCalculatorFragment extends Fragment implements MVP_Interfaces.RequiredViewOperations {
    private String[] items;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private EditText weight, height, age;
    private Button calculateButton;
    private LinearLayout nutritionFactsLayout;
    private Boolean unit, sex;
    String feet, goal;
    CustomTextWatcher textWatcher;
    private int difference;
    private double proteinPerPound, lifestyle;
    private TextView caloriesTextView, proteinTextView, fatTextView, carbTextView;
    private MVP_Interfaces.ProvidedPresenterOperations mPresenter;
    protected final String TAG = getClass().getSimpleName();
    private FragmentActivity myContext;
    private StateMaintainer mStateMaintainer;


    public CalorieCalculatorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myContext=(FragmentActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpMVP();
        View rootView = inflater.inflate(R.layout.fragment_calorie_calculator, container, false);
        caloriesTextView = (TextView) rootView.findViewById(R.id.calculationCalories);
        proteinTextView = (TextView) rootView.findViewById(R.id.calculationProtein);
        fatTextView = (TextView) rootView.findViewById(R.id.calculationFat);
        carbTextView = (TextView) rootView.findViewById(R.id.calculationCarbs);
        weight = (EditText) rootView.findViewById(R.id.weightEditText);
        height = (EditText) rootView.findViewById(R.id.heightEditText);
        age = (EditText) rootView.findViewById(R.id.ageEditText);
        items = new String[]{"Metric system", "Imperial system"};
        spinner = (Spinner) rootView.findViewById(R.id.metricsSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {
                    if (height.getText().toString().length() != 0) {
                        if (textWatcher != null)
                            height.removeTextChangedListener(textWatcher);
                        mPresenter.sendInches(height.getText().toString());
//                        height.setText(Integer.toString());
                    } else {
                        height.setText("");
                        height.setHint("cm");
                    }
                    unit = true;
                    weight.setText("");
                    age.setText("");
                    weight.setHint("kg");

                } else {
                    textWatcher = new CustomTextWatcher(height);
                    height.addTextChangedListener(textWatcher);
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
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0)
                    sex = true;
                else
                    sex = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
        items = new String[]{"Lightly active(sports 1-3 days/wk)", "Moderately active(sports 3-5 days/wk)", "Very active(sports 6-7 days/wk)"};
        spinner = (Spinner) rootView.findViewById(R.id.lifestyle);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0)
                    lifestyle = 1.375;
                if (i == 1)
                    lifestyle = 1.55;
                if (i == 2)
                    lifestyle = 1.725;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
        items = new String[]{"Maintain weight", "Lose weight", "Gain weight"};
        spinner = (Spinner) rootView.findViewById(R.id.goal);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    difference = 0;
                    proteinPerPound = 0.85;
                }
                if (i == 1) {
                    difference = -20;
                    proteinPerPound = 0.80;
                }
                if (i == 2) {
                    difference = 20;
                    proteinPerPound = 0.90;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
        calculateButton = (Button) rootView.findViewById(R.id.calculateButton);
        nutritionFactsLayout = (LinearLayout) rootView.findViewById(R.id.nutritionFactsLayout);
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
                    if (unit)
                        EventBus.getDefault().post(new CalculatorEvent(unit, (int) (2.2 * Integer.parseInt(weight.getText().toString())), Integer.parseInt(height.getText().toString()), Integer.parseInt(age.getText().toString()), sex, lifestyle, difference, proteinPerPound));
//                else
//                    EventBus.getDefault().post(new CalculatorEvent(unit, weight, (double) Integer.parseInt(convertToCentimeter(getFeet(height.getText().toString()), getInches(height.getText().toString()))), Integer.parseInt(age.getText().toString()), sex, lifestyle, goal));
                }


            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(CalculatorEvent event) {
        CalorieCalculator calorieCalculator = new CalorieCalculator(event.weight, event.sex, event.height, event.lifestyle, event.difference, event.proteinPerPound, 25, event.age);
        caloriesTextView.setText(String.valueOf((int) calorieCalculator.createDifference()));
        proteinTextView.setText(String.valueOf((int) calorieCalculator.getAverageProtein()));
        carbTextView.setText(String.valueOf((int) calorieCalculator.getAverageCarbs()));
        fatTextView.setText(String.valueOf((int) calorieCalculator.getAverageFat()));
        nutritionFactsLayout.setVisibility(View.VISIBLE);
    }

    public void setUpMVP() {
        try {
            mStateMaintainer = new StateMaintainer(myContext.getSupportFragmentManager(), TAG);
            if (mStateMaintainer.firstTimeIn()) {
                initialize(this);
            }
            else{
                reinitialize(this);
            }

        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void initialize(MVP_Interfaces.RequiredViewOperations view) throws InstantiationException, IllegalAccessException {
        mPresenter = new CalorieCalculatorPresenter(view);
        mStateMaintainer.put(MVP_Interfaces.ProvidedPresenterOperations.class.getSimpleName(), mPresenter);
    }

    private void reinitialize(MVP_Interfaces.RequiredViewOperations view) throws InstantiationException, IllegalAccessException {
        mPresenter = mStateMaintainer.get(MVP_Interfaces.ProvidedPresenterOperations.class.getSimpleName());
        if (mPresenter == null) {
            initialize(view);
        } else {
            mPresenter.onConfigurationChanged(view);
        }

    }


}
