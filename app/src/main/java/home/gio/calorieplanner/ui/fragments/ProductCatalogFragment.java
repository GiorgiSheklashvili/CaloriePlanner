package home.gio.calorieplanner.ui.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.thoughtbot.expandablecheckrecyclerview.listeners.OnCheckChildClickListener;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
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


    public ProductCatalogFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product_catalog, container, false);
        ButterKnife.bind(this, rootView);
        productList = new ArrayList<>();
        presenter = new ProductCatalogPresenter(this);
        if (!getArguments().getString("searchInList", "").equals("")) {
            searchLinear.setVisibility(View.VISIBLE);
            filterLinear.setVisibility(View.VISIBLE);
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

                if (productList != null) {
                    Set<String> stringSet = new HashSet<>(productList);
                    Set<String> exactViewPagerStringSet = new HashSet<>(productList);
                    SharedPreferences sharedPreferences = getActivity().getPreferences(getContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (sharedPreferences.getStringSet("shoppinglist", null) != null) {
                        stringSet.addAll(sharedPreferences.getStringSet("shoppinglist", null));
                    }

                    if (sharedPreferences.getStringSet(sharedPreferences.getString("keyOfViewpagerKey", ""), null) != null) {
                        exactViewPagerStringSet.addAll(sharedPreferences.getStringSet(sharedPreferences.getString("keyOfViewpagerKey", ""), null));
                    }
                    editor.putStringSet("shoppinglist", stringSet);
                    editor.putStringSet(sharedPreferences.getString("keyOfViewpagerKey", ""), exactViewPagerStringSet);
                    editor.apply();
                }
                GroceriesViewpagerFragment fragment = new GroceriesViewpagerFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_container, fragment).addToBackStack(null).commit();
            }
        });
        catalogAdapter.setChildClickListener(new OnCheckChildClickListener() {
            @Override
            public void onCheckChildCLick(View v, boolean checked, CheckedExpandableGroup group, int childIndex) {
                CheckedTextView checkedTextView = (CheckedTextView) v.findViewById(R.id.menu_item_CheckedTextView);
                productList.add(checkedTextView.getText().toString());
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
