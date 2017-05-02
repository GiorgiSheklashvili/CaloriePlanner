package home.gio.calorieplanner.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import home.gio.calorieplanner.App;
import home.gio.calorieplanner.R;
import home.gio.calorieplanner.grocerieslist.GroceriesListAdapter;
import home.gio.calorieplanner.grocerieslist.GroceriesListPresenter;
import home.gio.calorieplanner.grocerieslist.IGroceriesListView;
import home.gio.calorieplanner.models.Person;
import home.gio.calorieplanner.models.Product;

public class GroceriesListFragment extends Fragment implements AdapterView.OnItemSelectedListener, IGroceriesListView, View.OnClickListener {
    private EditText protein, carbs, fat, calories;
    public static final String ARG_OBJECT = "object";
    public Spinner spinner;
    private List<Person> personList;
    private List<String> namesList;
    private List<Product> productList;
    private GroceriesListPresenter presenter;
    private RecyclerView recyclerView;
    //    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button suggest;
    private FirebaseRecyclerAdapter adapter;
    private DatabaseReference databaseReference;
    public GroceriesListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        App app = (App) getContext().getApplicationContext();
        databaseReference = app.getItemsReference();


        presenter = new GroceriesListPresenter(this);
        presenter.fillProductList(productList,getContext());
        View rootView = inflater.inflate(R.layout.fragment_groceries_list, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.groceriesListRecyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        String[] items = new String[]{"None", "Vegetarian", "Vegan", "Raw Vegan", "Paleo"};
        protein = (EditText) rootView.findViewById(R.id.proteinEditText);
        carbs = (EditText) rootView.findViewById(R.id.carbEditText);
        fat = (EditText) rootView.findViewById(R.id.fatEditText);
        calories = (EditText) rootView.findViewById(R.id.caloriesEditText);
        spinner = (Spinner) rootView.findViewById(R.id.constraintSpinner);
        suggest = (Button) rootView.findViewById(R.id.suggestButton);
        suggest.setOnClickListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner = (Spinner) rootView.findViewById(R.id.nameSpinner);
        namesList = new ArrayList<>();
        namesList.add("None");
        presenter.fillPersonsList(personList, getContext());
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
        this.personList = personList;
        if (personList != null) {
            for (int i = 0; i < personList.size(); i++) {
                namesList.add(personList.get(i).getName());
            }
        }
    }

    @Override
    public void fillProductList(List<Product> productList) {
        this.productList = productList;

    }

    @Override
    public void onClick(View view) {
        adapter = new GroceriesListAdapter(R.layout.groceries_list_custom_row, databaseReference, productList);
        recyclerView.setAdapter(adapter);
    }
}
