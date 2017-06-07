package home.gio.calorieplanner.ui.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import home.gio.calorieplanner.R;
import home.gio.calorieplanner.grocerieslist.GroceriesListPresenter;
import home.gio.calorieplanner.grocerieslist.IGroceriesListView;
import home.gio.calorieplanner.main.Main;
import home.gio.calorieplanner.models.Person;
import home.gio.calorieplanner.shoppinglist.ShoppingAdapter;

public class GroceriesListFragment extends Fragment implements AdapterView.OnItemSelectedListener, IGroceriesListView, View.OnClickListener {
    private EditText protein, carbs, fat, calories;
    public static final String ARG_OBJECT = "object";
    public Spinner spinner;
    private List<Person> personList;
    private List<String> namesList;
    private List<String> productList;
    private GroceriesListPresenter presenter;
    private RecyclerView.LayoutManager layoutManager;
    private ShoppingAdapter shoppingAdapter;
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor editor;
    private int position;
    @BindView(R.id.removeFrom)
    public Button removeProduct;
    @BindView(R.id.addProduct)
    public Button addProduct;
    @BindView(R.id.groceriesListRecyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.caloriesTextViewViewpager)
    public TextView caloriesTextView;
    @BindView(R.id.fatTextViewViewpager)
    public TextView fatTextView;
    @BindView(R.id.proteinTextViewViewpager)
    public TextView proteinTextView;
    @BindView(R.id.carbsTextViewViewpager)
    public TextView carbsTextView;
    public Double sumOfPrices = 0.0;
    public Double sumOfFat = 0.0;
    public Double sumOfProtein = 0.0;
    public Double sumOfCarbs = 0.0;
    public Double sumOfCalories = 0.0;

    public GroceriesListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        position = getArguments().getInt("positionOfViewpager", -1);
        View rootView = inflater.inflate(R.layout.fragment_groceries_list, container, false);
        ButterKnife.bind(this, rootView);
        sharedPrefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        layoutManager = new LinearLayoutManager(getContext());
        presenter = new GroceriesListPresenter(this);
        namesList = new ArrayList<>();
        namesList.add("None");
        presenter.fillPersonsList(personList, getContext());
        presenter.fillProductsList(getActivity(), String.valueOf(position));
        protein = (EditText) rootView.findViewById(R.id.proteinEditText);
        carbs = (EditText) rootView.findViewById(R.id.carbEditText);
        fat = (EditText) rootView.findViewById(R.id.fatEditText);
        calories = (EditText) rootView.findViewById(R.id.caloriesEditText);
        if (this.productList != null) {
            shoppingAdapter = new ShoppingAdapter(productList);
            recyclerView.setAdapter(shoppingAdapter);
            updateTextViews(shoppingAdapter.productList);
        }
        recyclerView.setLayoutManager(layoutManager);

        addProduct.setOnClickListener(this);
        removeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Integer> recyclerInts = new ArrayList<Integer>();
                for (Iterator<Integer> k = shoppingAdapter.positionList.iterator(); k.hasNext(); ) {
                    Integer next = k.next();
                    recyclerInts.add(next);
                    k.remove();
                }
                for (Integer ints = recyclerInts.size() - 1; ints >= 0; ints--) {
                    productList.remove((int) recyclerInts.get(ints));
                    shoppingAdapter.notifyItemRemoved(recyclerInts.get(ints));
                }

                Set<String> shoppingSet = sharedPrefs.getStringSet("shoppinglist", null);
                List<String> shoppingList = new ArrayList<String>();
                if (shoppingSet != null) {
                    shoppingList.addAll(shoppingSet);
                }
                int tempIndex;
                for (String product : shoppingAdapter.productList) {
                    tempIndex = shoppingList.indexOf(product);
                    if (tempIndex != -1)
                        shoppingList.remove(tempIndex);
                }
                updateTextViews(shoppingAdapter.productList);
                sharedPrefs.edit().putStringSet("shoppinglist", new HashSet<String>(shoppingList)).apply();
            }
        });
        spinner = (Spinner) rootView.findViewById(R.id.nameSpinner);
        ArrayAdapter<String> nameAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, namesList);
        spinner.setAdapter(nameAdapter);
        spinner.setOnItemSelectedListener(this);
        Bundle args = getArguments();
        return rootView;
    }

    @Override
    public void updateTextViews(List<String> productList) {
        sumOfFat = 0.0;
        sumOfProtein = 0.0;
        sumOfCarbs = 0.0;
        sumOfCalories = 0.0;
        for (String product : productList) {
            sumOfFat += Double.parseDouble(Main.getFat(product));
            sumOfProtein += Double.parseDouble(Main.getProtein(product));
            sumOfCarbs += Double.parseDouble(Main.getCarbs(product));
            sumOfCalories += Double.parseDouble(Main.getMyCalories(product));
        }
        Drawable background = caloriesTextView.getBackground();
        caloriesTextView.setText("კალორიები:" + String.valueOf(sumOfCalories));
        fatTextView.setText("ცხიმები:" + String.valueOf(sumOfFat));
        carbsTextView.setText("ნახშირწყლები:" + String.valueOf(sumOfCarbs));
        proteinTextView.setText("ცილები:" + String.valueOf(sumOfProtein));
        if (!this.calories.getText().toString().equals("")) {
            if (checkIfExceededCalories(sumOfCalories))
                caloriesTextView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.red_line));
            else
                caloriesTextView.setBackground(background);
        }
        if (!this.fat.getText().toString().equals("")) {
            if (checkIfExceededFats(sumOfFat))
                fatTextView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.red_line));
            else
                fatTextView.setBackground(background);
        }
        if (!this.carbs.getText().toString().equals("")) {
            if (checkIfExceededCarbs(sumOfCarbs))
                carbsTextView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.red_line));
            else
                carbsTextView.setBackground(background);
        }
        if (!this.protein.getText().toString().equals("")) {
            if (checkIfExceededProteins(sumOfProtein))
                proteinTextView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.red_line));
            else
                proteinTextView.setBackground(background);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (productList != null) {
            sharedPrefs.edit().putStringSet(String.valueOf(sharedPrefs.getInt("personRow", -1)) + String.valueOf(position), new HashSet<>(productList)).apply();
        }

    }

    private boolean checkIfExceededCalories(Double calories) {
        return calories > Double.parseDouble(this.calories.getText().toString());
    }

    private boolean checkIfExceededFats(Double fats) {
        return fats > Double.parseDouble(this.fat.getText().toString());
    }

    private boolean checkIfExceededCarbs(Double carbs) {
        return carbs > Double.parseDouble(this.carbs.getText().toString());
    }

    private boolean checkIfExceededProteins(Double proteins) {
        return proteins > Double.parseDouble(this.protein.getText().toString());
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
        if (productList!= null) {
            updateTextViews(productList);
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
