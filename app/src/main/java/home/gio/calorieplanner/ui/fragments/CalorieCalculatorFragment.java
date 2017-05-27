package home.gio.calorieplanner.ui.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Toast;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import home.gio.calorieplanner.R;
import home.gio.calorieplanner.calculator.ICalorieCalculatorView;
import home.gio.calorieplanner.events.CalculatorEvent;
import home.gio.calorieplanner.calculator.CalorieCalculator;
import home.gio.calorieplanner.models.Person;
import home.gio.calorieplanner.calculator.CalorieCalculatorPresenter;
import home.gio.calorieplanner.calculator.CustomTextWatcher;


public class CalorieCalculatorFragment extends Fragment implements ICalorieCalculatorView {
    private String[] items;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private EditText weight, height, age, name;
    private Button calculateButton, save;
    private LinearLayout nutritionFactsLayout;
    private Boolean unit, sex;
    private CustomTextWatcher textWatcher;
    private int difference;
    private double proteinPerPound, lifestyle;
    private TextView caloriesTextView, proteinTextView, fatTextView, carbTextView;
    private CalorieCalculatorPresenter mPresenter;
    private List<Person> personsList = new ArrayList<>();

    public CalorieCalculatorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPresenter = new CalorieCalculatorPresenter(this);
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
        spinner.setOnItemSelectedListener(unitSpinnerListener);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        items = new String[]{"Male", "Female"};
        spinner = (Spinner) rootView.findViewById(R.id.sex);
        spinner.setOnItemSelectedListener(sexSpinnerListener);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
        items = new String[]{"Lightly active(sports 1-3 days/wk)", "Moderately active(sports 3-5 days/wk)", "Very active(sports 6-7 days/wk)"};
        spinner = (Spinner) rootView.findViewById(R.id.lifestyle);
        spinner.setOnItemSelectedListener(activitySpinnerListener);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
        items = new String[]{"Maintain weight", "Lose weight", "Gain weight"};
        spinner = (Spinner) rootView.findViewById(R.id.goal);
        spinner.setOnItemSelectedListener(goalSpinnerListener);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
        calculateButton = (Button) rootView.findViewById(R.id.calculateButton);
        nutritionFactsLayout = (LinearLayout) rootView.findViewById(R.id.nutritionFactsLayout);
        calculateButton.setOnClickListener(calculateListener);
        save = (Button) rootView.findViewById(R.id.save_in_list);
        save.setOnClickListener(saveClickListener);
        name = (EditText) rootView.findViewById(R.id.name_of_person);
        final Drawable drawable = weight.getBackground();
        weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (weight.getText().toString().equals(""))
                    weight.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.red_line));
                else {
                    weight.setBackground(drawable);
                }
            }
        });
        age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (age.getText().toString().equals("")) {
                    age.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.red_line));
                } else {
                    age.setBackground(drawable);
                }
            }
        });
        height.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (height.getText().toString().equals("")) {
                    height.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.red_line));
                } else {
                    height.setBackground(drawable);
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
        weight.setText("");
        height.setText("");
        age.setText("");
        EventBus.getDefault().unregister(this);
    }

    View.OnClickListener saveClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Person person = new Person(name.getText().toString(), Integer.parseInt(caloriesTextView.getText().toString()), Integer.parseInt(proteinTextView.getText().toString()), Integer.parseInt(fatTextView.getText().toString()), Integer.parseInt(carbTextView.getText().toString()));
            personsList.add(person);
            Context context = getActivity();
            SharedPreferences sharedPrefs = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(personsList);
            editor.putString("newData", json);
            editor.commit();
            Toast.makeText(context, name.getText().toString() + " saved in persons list", Toast.LENGTH_SHORT).show();

        }
    };
    View.OnClickListener calculateListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (!weight.getText().toString().equals("") && !height.getText().toString().equals("") && !age.getText().toString().equals("")) {
                if (unit)
                    EventBus.getDefault().post(new CalculatorEvent(unit, (int) Math.round((2.2 * Integer.parseInt(weight.getText().toString()))), Integer.parseInt(height.getText().toString()), Integer.parseInt(age.getText().toString()), sex, lifestyle, difference, proteinPerPound));
                else
                    EventBus.getDefault().post(new CalculatorEvent(unit, Integer.parseInt(weight.getText().toString()), (int) Math.round(mPresenter.getCentimeters(String.valueOf(mPresenter.getFeet(height.getText().toString())), String.valueOf(mPresenter.getInches(height.getText().toString())))), Integer.parseInt(age.getText().toString()), sex, lifestyle, difference, proteinPerPound));
            }


        }
    };
    AdapterView.OnItemSelectedListener goalSpinnerListener = new AdapterView.OnItemSelectedListener()

    {
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
    };
    AdapterView.OnItemSelectedListener activitySpinnerListener = new AdapterView.OnItemSelectedListener()

    {
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
    };
    AdapterView.OnItemSelectedListener sexSpinnerListener = new AdapterView.OnItemSelectedListener()

    {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            sex = i == 0;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    AdapterView.OnItemSelectedListener unitSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (i == 0) {

                if (textWatcher != null)
                    height.removeTextChangedListener(textWatcher);
                if (height.getText().toString().length() != 0) {
                    height.setText(mPresenter.sendInches(height.getText().toString()));
                } else {
                    height.setText("");
                    height.setHint("cm");
                }
                if (weight.getText().toString().length() != 0) {
                    weight.setText(String.valueOf(Math.round(Integer.parseInt(weight.getText().toString()) / 2.2)));
                } else {
                    weight.setText("");
                    weight.setHint("kg");
                }
                unit = true;
            } else

            {
                textWatcher = new CustomTextWatcher(height);
                height.addTextChangedListener(textWatcher);
                if (height.getText().toString().length() != 0)
                    height.setText(String.valueOf(mPresenter.convertTofeetInches(String.valueOf(height.getText().toString()))));
                else {
                    height.setText("");
                    height.setHint("inches");
                }
                if (weight.getText().toString().length() != 0) {
                    weight.setText(String.valueOf((int) Math.round(Integer.parseInt(weight.getText().toString()) * 2.2)));
                } else {
                    weight.setHint("lbs");
                    weight.setText("");
                }
                unit = false;
            }
            if (age.getText().toString().length() == 0)
                age.setText("");
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    @Subscribe
    public void onEvent(CalculatorEvent event) {
        CalorieCalculator calorieCalculator = new CalorieCalculator(event.weight, event.sex, event.height, event.lifestyle, event.difference, event.proteinPerPound, 25, event.age);
        caloriesTextView.setText(String.valueOf((int) Math.round(calorieCalculator.createDifference())));
        proteinTextView.setText(String.valueOf((int) Math.round(calorieCalculator.getAverageProtein())));
        carbTextView.setText(String.valueOf((int) Math.round(calorieCalculator.getAverageCarbs())));
        fatTextView.setText(String.valueOf((int) Math.round(calorieCalculator.getAverageFat())));
        nutritionFactsLayout.setVisibility(View.VISIBLE);
    }


}



