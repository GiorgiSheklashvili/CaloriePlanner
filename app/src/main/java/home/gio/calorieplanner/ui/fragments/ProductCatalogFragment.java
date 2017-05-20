package home.gio.calorieplanner.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

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
    @BindView(R.id.filter_lineaer_layout_in_catalog)
    public LinearLayout filterLinear;
    @BindView(R.id.search_in_product_catalog)
    public ImageView searchImg;
    @BindView(R.id.edittext_in_product_catalog)
    public EditText editText;

    public ProductCatalogFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product_catalog, container, false);
        ButterKnife.bind(this, rootView);
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
            }
        });
        recyclerView = (RecyclerView) rootView.findViewById(R.id.product_catalog_recycler);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(catalogAdapter);
        return rootView;
    }

}
