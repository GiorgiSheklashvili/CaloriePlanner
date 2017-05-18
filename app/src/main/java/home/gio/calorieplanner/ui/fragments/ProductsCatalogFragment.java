package home.gio.calorieplanner.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.Spinner;

import com.thoughtbot.expandablecheckrecyclerview.listeners.OnCheckChildClickListener;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import home.gio.calorieplanner.R;
import home.gio.calorieplanner.main.Main;
import home.gio.calorieplanner.models.Category;
import home.gio.calorieplanner.models.Product;
import home.gio.calorieplanner.models.RetailChain;
import home.gio.calorieplanner.models.SubMenu;
import home.gio.calorieplanner.productcatalog.CategoryAdapter;
import home.gio.calorieplanner.productcatalog.IProductCatalogView;
import home.gio.calorieplanner.productcatalog.ProductCatalogPresenter;


public class ProductsCatalogFragment extends Fragment implements IProductCatalogView {
    @BindView(R.id.constraintSpinner)
    public Spinner spinner;
    @BindView(R.id.recycler_view_menu)
    public RecyclerView recyclerView;
    @BindView(R.id.clear_button)
    public Button clearBtn;
    @BindView(R.id.choose_button)
    public Button chooseBtn;
    private RecyclerView.LayoutManager layoutManager;
    private ProductCatalogPresenter presenter;
    private CategoryAdapter categoryAdapter;


    public ProductsCatalogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        presenter = new ProductCatalogPresenter(this);
        View rootView = inflater.inflate(R.layout.fragment_products_catalog, container, false);
        ButterKnife.bind(this, rootView);
        final List<Category> categories = presenter.getCategories();
        categoryAdapter = new CategoryAdapter(categories);
        categoryAdapter.setChildClickListener(new OnCheckChildClickListener() {
            @Override
            public void onCheckChildCLick(View v, boolean checked, CheckedExpandableGroup group, int childIndex) {
                int k = childIndex;
            }
        });
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(categoryAdapter);
        String[] items = new String[]{"None", "Vegetarian", "Vegan", "Raw Vegan"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        chooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryAdapter.clearChoices();
            }
        });
        return rootView;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        categoryAdapter.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        categoryAdapter.onRestoreInstanceState(savedInstanceState);
    }


}
