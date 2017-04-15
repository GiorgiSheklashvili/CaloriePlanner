package home.gio.calorieplanner.ui.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import home.gio.calorieplanner.R;
import home.gio.calorieplanner.grocerieslist.GroceriesListPresenter;
import home.gio.calorieplanner.grocerieslist.IGroceriesListView;
import home.gio.calorieplanner.models.Person;

public class GroceriesListFragment extends Fragment implements AdapterView.OnItemSelectedListener,IGroceriesListView {
    private EditText protein, carbs, fat, calories;
    public static final String ARG_OBJECT = "object";
    public Spinner spinner;
    List<Person> personList;
    List<String> namesList;
    GroceriesListPresenter presenter;
    public GroceriesListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        presenter=new GroceriesListPresenter(this);
        View rootView = inflater.inflate(R.layout.fragment_groceries_list, container, false);
        String[] items = new String[]{"None", "Vegetarian", "Vegan", "Raw Vegan", "Paleo"};
        protein = (EditText) rootView.findViewById(R.id.proteinEditText);
        carbs = (EditText) rootView.findViewById(R.id.carbEditText);
        fat = (EditText) rootView.findViewById(R.id.fatEditText);
        calories = (EditText) rootView.findViewById(R.id.caloriesEditText);
        spinner = (Spinner) rootView.findViewById(R.id.contraintSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner = (Spinner) rootView.findViewById(R.id.nameSpinner);
        namesList = new ArrayList<>();
        namesList.add("None");
        presenter.fillPersonsList(personList,getContext());
        ArrayAdapter<String> nameAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, namesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(nameAdapter);
        spinner.setOnItemSelectedListener(this);
        Bundle args = getArguments();
        return rootView;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i != 0) {
            protein.setText(String.valueOf(personList.get(i - 1).getProtein()));
            carbs.setText(String.valueOf(personList.get(i - 1).getCarbs()));
            fat.setText(String.valueOf(personList.get(i - 1).getFat()));
            calories.setText(String.valueOf(personList.get(i - 1).getCalories()));
        } else {
            protein.setText("");
            carbs.setText("");
            fat.setText("");
            calories.setText("");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void fillPersonList(List<Person> personList) {
        this.personList=personList;
        if (personList != null) {
            for (int i = 0; i < personList.size(); i++) {

                namesList.add(personList.get(i).getName());
            }
        }
    }
}
