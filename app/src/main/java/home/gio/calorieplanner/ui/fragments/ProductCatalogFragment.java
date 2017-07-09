package home.gio.calorieplanner.ui.fragments;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.thoughtbot.expandablecheckrecyclerview.listeners.OnCheckChildClickListener;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import home.gio.calorieplanner.App;
import home.gio.calorieplanner.R;
import home.gio.calorieplanner.productcatalog.CatalogAdapter;
import home.gio.calorieplanner.productcatalog.IProductCatalogView;
import home.gio.calorieplanner.productcatalog.ProductCatalogPresenter;


public class ProductCatalogFragment extends Fragment implements IProductCatalogView {

    RecyclerView recyclerView;
    CatalogAdapter catalogAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<String> listOfCategories;
    ProductCatalogPresenter presenter;
    @BindView(R.id.search_linear_layout_in_catalog)
    public LinearLayout searchLinear;
    @BindView(R.id.filter_linear_layout_in_catalog)
    public LinearLayout filterLinear;
    @BindView(R.id.search_in_product_catalog)
    public ImageView searchImg;
    @BindView(R.id.editText_in_product_catalog)
    public EditText editText;
    @BindView(R.id.clear_button_catalog)
    public Button clear;
    @BindView(R.id.go_to_shopping_product_catalog)
    public Button returnToViewpager;
    private List<String> productList;
    private List<String> tempProductList = new ArrayList<>();

    public ProductCatalogFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        View rootView = inflater.inflate(R.layout.fragment_product_catalog, container, false);
        ButterKnife.bind(this, rootView);
        productList = new ArrayList<>();
        presenter = new ProductCatalogPresenter(this);
        if (!getArguments().getString("searchInList", "").equals("")) {
            searchLinear.setVisibility(View.VISIBLE);
//            filterLinear.setVisibility(View.VISIBLE);
            catalogAdapter = new CatalogAdapter(presenter.searchedResults(getArguments().getString("searchInList")));
        }
        if (getArguments().getStringArrayList("catalogList") != null) {
            listOfCategories = getArguments().getStringArrayList("catalogList");
            catalogAdapter = new CatalogAdapter(presenter.getCategories(listOfCategories));
        }
        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catalogAdapter = new CatalogAdapter(presenter.searchedResults(editText.getText().toString()));
                recyclerView.swapAdapter(catalogAdapter, true);
            }
        });
        returnToViewpager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productList.size() != 0) {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.preference_file_key),getContext().MODE_PRIVATE);
                    List<String> exactViewPagerProductList = new ArrayList<>(productList);
//                    List<String> numberListAfterRemove = App.listFromGson(getActivity(), "numberOf" + sharedPreferences.getString("keyOfViewpagerKey", ""));
//                    if (numberListAfterRemove != null) {
//                        for (int i = 0; i < productList.size(); i++) {
//                            numberListAfterRemove.add(String.valueOf(1));
//                        }
//                        App.listToGson(getActivity(), numberListAfterRemove, "numberOf" + sharedPreferences.getString("keyOfViewpagerKey", ""));
//                    }
                    if (App.listFromGson(getActivity(), "shoppinglist") != null) {
                        tempProductList = App.listFromGson(getActivity(), "shoppinglist");
                        tempProductList.addAll(productList);
                    }
                    if (App.listFromGson(getActivity(), sharedPreferences.getString("keyOfViewpagerKey", "")) != null) {
                        exactViewPagerProductList.addAll(App.listFromGson(getActivity(), sharedPreferences.getString("keyOfViewpagerKey", "")));
                    }
                    if (tempProductList.size() != 0) {
                        App.listToGson(getActivity(), tempProductList, "shoppinglist");
                    } else {
                        App.listToGson(getActivity(), productList, "shoppinglist");
                    }
                    App.listToGson(getActivity(), exactViewPagerProductList, sharedPreferences.getString("keyOfViewpagerKey", ""));
                    GroceriesViewpagerFragment fragment = new GroceriesViewpagerFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit).replace(R.id.fragment_main_container, fragment, "GroceriesViewpager").addToBackStack(fragment.getClass().getName()).commit();
                } else {
                    Toast.makeText(getContext(), "აირჩიეთ პროდუქტები", Toast.LENGTH_SHORT).show();
                }

            }
        });
        catalogAdapter.setChildClickListener(new OnCheckChildClickListener() {
            @Override
            public void onCheckChildCLick(View v, boolean checked, CheckedExpandableGroup group, int childIndex) {
                CheckedTextView checkedTextView = (CheckedTextView) v.findViewById(R.id.menu_item_CheckedTextView);
                if (checked) {
                    productList.add(checkedTextView.getText().toString());
                } else {
                    productList.remove(checkedTextView.getText().toString());
                }

            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catalogAdapter.clearChoices();
                productList.clear();
            }
        });
        recyclerView = (RecyclerView) rootView.findViewById(R.id.product_catalog_recycler);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(catalogAdapter);
        return rootView;
    }

}
