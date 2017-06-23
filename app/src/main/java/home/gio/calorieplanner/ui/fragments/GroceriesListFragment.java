package home.gio.calorieplanner.ui.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import home.gio.calorieplanner.App;
import home.gio.calorieplanner.R;
import home.gio.calorieplanner.grocerieslist.GroceriesListAdapter;
import home.gio.calorieplanner.grocerieslist.GroceriesListPresenter;
import home.gio.calorieplanner.grocerieslist.IGroceriesListView;
import home.gio.calorieplanner.main.Main;
import home.gio.calorieplanner.models.Person;

public class GroceriesListFragment extends Fragment implements AdapterView.OnItemSelectedListener, IGroceriesListView, View.OnClickListener {
    private int personsProtein = 0, personsCarbs = 0, personsFat = 0;
    private EditText proteinEditText, carbsEditText, fatEditText;
    private TextView calories;
    public static final String ARG_OBJECT = "object";
    public Spinner spinner;
    private List<Person> personList;
    private List<String> namesList;
    private List<String> productList;
    private List<Integer> numbersList;
    private GroceriesListPresenter presenter;
    private RecyclerView.LayoutManager layoutManager;
    private GroceriesListAdapter groceriesListAdapter;
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
    @BindView(R.id.nutrition_facts_textViews)
    public LinearLayout nutritionFactsTextViews;
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
        presenter.fillNumbersList(getActivity(), String.valueOf(position));
        proteinEditText = (EditText) rootView.findViewById(R.id.proteinEditText);
        carbsEditText = (EditText) rootView.findViewById(R.id.carbEditText);
        fatEditText = (EditText) rootView.findViewById(R.id.fatEditText);
        calories = (TextView) rootView.findViewById(R.id.caloriesTextView);
        proteinEditText.addTextChangedListener(proteinTextWatcher);
        fatEditText.addTextChangedListener(fatTextWatcher);
        carbsEditText.addTextChangedListener(carbTextWatcher);
        if (this.productList != null && sharedPrefs.getInt("personRow", -1) != -1 && position != -1) {
            groceriesListAdapter = new GroceriesListAdapter(productList, numbersList, getActivity(), sharedPrefs.getInt("personRow", -1) + String.valueOf(position));
            recyclerView.setAdapter(groceriesListAdapter);
            updateTextViews(groceriesListAdapter.productList);
        }
        recyclerView.setLayoutManager(layoutManager);
        addProduct.setOnClickListener(this);
        removeProduct.setOnClickListener(removeListener);
        spinner = (Spinner) rootView.findViewById(R.id.nameSpinner);
        ArrayAdapter<String> nameAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, namesList);
        spinner.setAdapter(nameAdapter);
        spinner.setOnItemSelectedListener(this);
        if (sharedPrefs.getInt("SpinnerSelectedKey" + sharedPrefs.getInt("personRow", -1) + String.valueOf(position), -1) != -1) {
            spinner.setSelection(sharedPrefs.getInt("SpinnerSelectedKey" + sharedPrefs.getInt("personRow", -1) + String.valueOf(position), -1));
        }
        Bundle args = getArguments();
        return rootView;
    }

    @Override
    public void updateTextViews(List<String> productList) {
        if (groceriesListAdapter.productList.size() == 0) {
            nutritionFactsTextViews.setVisibility(View.INVISIBLE);
        } else {
            nutritionFactsTextViews.setVisibility(View.VISIBLE);
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
            Drawable background = fatTextView.getBackground();
            caloriesTextView.setText("კალორიები:" + String.valueOf(sumOfCalories));
            fatTextView.setText("ცხიმები:" + String.valueOf(sumOfFat));
            carbsTextView.setText("ნახშირწყლები:" + String.valueOf(sumOfCarbs));
            proteinTextView.setText("ცილები:" + String.valueOf(sumOfProtein));
            if (!this.calories.getText().toString().equals("")) {
                if (checkIfExceededCalories(sumOfCalories))
                    caloriesTextView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.red_line));
                else
                    caloriesTextView.setBackground(background);
            } else
                caloriesTextView.setBackground(background);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (productList != null) {
            updateTextViews(productList);
        }
        if (sharedPrefs.getInt("SpinnerSelectedKey" + sharedPrefs.getInt("personRow", -1) + String.valueOf(position), -1) != -1) {
            spinner.setSelection(sharedPrefs.getInt("SpinnerSelectedKey" + sharedPrefs.getInt("personRow", -1) + String.valueOf(position), -1));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (productList != null) {
            App.listToGson(getActivity(), productList, String.valueOf(sharedPrefs.getInt("personRow", -1)) + String.valueOf(position));
        }

    }

    private boolean checkIfExceededCalories(Double calories) {
        return calories > Double.parseDouble(this.calories.getText().toString());
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i != 0) {
            personsProtein = personList.get(i - 1).getProtein();
            personsCarbs = personList.get(i - 1).getCarbs();
            personsFat = personList.get(i - 1).getFat();
            proteinEditText.setText(String.valueOf(personsProtein));
            carbsEditText.setText(String.valueOf(personsCarbs));
            fatEditText.setText(String.valueOf(personsFat));
            calories.setText(String.valueOf(personList.get(i - 1).getCalories()));
        } else {
            proteinEditText.setText("");
            carbsEditText.setText("");
            fatEditText.setText("");
            calories.setText("");
        }
        if (productList != null) {
            updateTextViews(productList);
        }
        editor = sharedPrefs.edit();
        editor.putInt("SpinnerSelectedKey" + String.valueOf(sharedPrefs.getInt("personRow", -1)) + String.valueOf(position), i);
        editor.apply();
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
    public void fillNumbersList(List<Integer> numbersList) {
        this.numbersList = numbersList;
    }


    @Override
    public void onClick(View view) {
        SubMenuAndFilterFragment productsCatalog = new SubMenuAndFilterFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_container, productsCatalog).addToBackStack(null).commit();

    }

    TextWatcher proteinTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (proteinEditText.getText().toString().length() > 0) {
                if (personsProtein != Integer.valueOf(proteinEditText.getText().toString()) && carbsEditText.getText().toString().length() != 0 && fatEditText.getText().toString().length() != 0) {
                    calories.setText(String.valueOf(4 * Integer.valueOf(proteinEditText.getText().toString()) + 4 * Integer.valueOf(carbsEditText.getText().toString()) + 9 * Integer.valueOf(fatEditText.getText().toString())));
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    TextWatcher carbTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (carbsEditText.getText().toString().length() > 0) {
                if (personsCarbs != Integer.valueOf(carbsEditText.getText().toString()) && proteinEditText.getText().toString().length() != 0 && fatEditText.getText().toString().length() != 0) {
                    calories.setText(String.valueOf(4 * Integer.valueOf(proteinEditText.getText().toString()) + 4 * Integer.valueOf(carbsEditText.getText().toString()) + 9 * Integer.valueOf(fatEditText.getText().toString())));
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    TextWatcher fatTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (fatEditText.getText().toString().length() > 0) {
                if (personsFat != Integer.valueOf(fatEditText.getText().toString()) && carbsEditText.getText().toString().length() != 0 && proteinEditText.getText().toString().length() != 0) {
                    calories.setText(String.valueOf(4 * Integer.valueOf(proteinEditText.getText().toString()) + 4 * Integer.valueOf(carbsEditText.getText().toString()) + 9 * Integer.valueOf(fatEditText.getText().toString())));
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    View.OnClickListener removeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (groceriesListAdapter.positionList.size() != 0) {
                List<Integer> recyclerInts = new ArrayList<Integer>();
                List<String> numberListAfterRemove = App.listFromGson(getActivity(), "numberOf" + sharedPrefs.getInt("personRow", -1) + String.valueOf(position));
                for (Iterator<Integer> k = groceriesListAdapter.positionList.iterator(); k.hasNext(); ) {
                    Integer next = k.next();
                    recyclerInts.add(next);
                    k.remove();
                }
                for (Integer ints = recyclerInts.size() - 1; ints >= 0; ints--) {
                    productList.remove((int) recyclerInts.get(ints));
                    groceriesListAdapter.notifyItemRemoved(recyclerInts.get(ints));
                    numberListAfterRemove.remove((int) recyclerInts.get(ints));
                }
                App.listToGson(getActivity(), numberListAfterRemove, "numberOf" + sharedPrefs.getInt("personRow", -1) + String.valueOf(position));
                List<String> shoppingList = App.listFromGson(getActivity(), "shoppinglist");
                int tempIndex;
                for (String product : groceriesListAdapter.productList) {
                    tempIndex = shoppingList.indexOf(product);
                    if (tempIndex != -1)
                        shoppingList.remove(tempIndex);
                }
                updateTextViews(groceriesListAdapter.productList);
                App.listToGson(getActivity(), shoppingList, "shoppinglist");

            } else {
                Toast.makeText(getContext(), "აირჩიეთ პროდუქტები", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
