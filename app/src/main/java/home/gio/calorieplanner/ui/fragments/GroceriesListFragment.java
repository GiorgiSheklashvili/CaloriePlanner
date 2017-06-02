package home.gio.calorieplanner.ui.fragments;


import android.content.Context;
import android.content.SharedPreferences;
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
import java.util.HashSet;
import java.util.List;

import home.gio.calorieplanner.App;
import home.gio.calorieplanner.R;
import home.gio.calorieplanner.grocerieslist.GroceriesListPresenter;
import home.gio.calorieplanner.grocerieslist.IGroceriesListView;
import home.gio.calorieplanner.models.Person;
import home.gio.calorieplanner.shoppinglist.ShoppingAdapter;
import home.gio.calorieplanner.ui.activities.MainActivity;

public class GroceriesListFragment extends Fragment implements AdapterView.OnItemSelectedListener, IGroceriesListView, View.OnClickListener {
    private EditText protein, carbs, fat, calories;
    public static final String ARG_OBJECT = "object";
    public Spinner spinner;
    private List<Person> personList;
    private List<String> namesList;
    private List<String> productList;
    private GroceriesListPresenter presenter;
    private RecyclerView recyclerView;
    //    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button addProduct;
    private FirebaseRecyclerAdapter adapter;
    private DatabaseReference databaseReference;
    private ShoppingAdapter shoppingAdapter;
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor editor;
    private int position;

    public GroceriesListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        position = getArguments().getInt("positionOfViewpager", -1);
        App app = (App) getContext().getApplicationContext();
        databaseReference = app.getItemsReference();
        View rootView = inflater.inflate(R.layout.fragment_groceries_list, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.groceriesListRecyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        presenter = new GroceriesListPresenter(this);
        presenter.fillPersonsList(personList, getContext());
        presenter.fillProductsList(getActivity(), String.valueOf(position));
        if (this.productList != null) {
            shoppingAdapter = new ShoppingAdapter(productList);
            recyclerView.setAdapter(shoppingAdapter);
            shoppingAdapter.notifyDataSetChanged();
        }
        recyclerView.setLayoutManager(layoutManager);
        protein = (EditText) rootView.findViewById(R.id.proteinEditText);
        carbs = (EditText) rootView.findViewById(R.id.carbEditText);
        fat = (EditText) rootView.findViewById(R.id.fatEditText);
        calories = (EditText) rootView.findViewById(R.id.caloriesEditText);
        addProduct = (Button) rootView.findViewById(R.id.addProduct);
        addProduct.setOnClickListener(this);
        spinner = (Spinner) rootView.findViewById(R.id.nameSpinner);
        namesList = new ArrayList<>();
        namesList.add("None");
        ArrayAdapter<String> nameAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, namesList);
        spinner.setAdapter(nameAdapter);
        spinner.setOnItemSelectedListener(this);
        Bundle args = getArguments();
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (productList == null) {
            productList = new ArrayList<>();
        }
        sharedPrefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        if (sharedPrefs.getStringSet("savedList" + String.valueOf(position), null) != null) {
            this.productList.addAll(sharedPrefs.getStringSet("savedList" + String.valueOf(position), null));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
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
    public void fillProductList(List<String> productList) {
        this.productList = productList;

    }


    @Override
    public void onClick(View view) {
        SubMenuAndFilterFragment productsCatalog = new SubMenuAndFilterFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_container, productsCatalog).addToBackStack(null).commit();

    }
}
