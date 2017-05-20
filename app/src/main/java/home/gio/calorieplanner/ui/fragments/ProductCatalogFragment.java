package home.gio.calorieplanner.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

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

    public ProductCatalogFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        presenter = new ProductCatalogPresenter(this);
        View rootView = inflater.inflate(R.layout.fragment_product_catalog, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.product_catalog_recycler);
        listOfCategories = getArguments().getStringArrayList("catalogList");
        catalogAdapter = new CatalogAdapter(presenter.getCategories(listOfCategories));
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(catalogAdapter);
        return rootView;
    }

}
