package home.gio.calorieplanner.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import home.gio.calorieplanner.R;
import home.gio.calorieplanner.productcatalog.IProductCatalogView;
import home.gio.calorieplanner.productcatalog.MenuAdapter;
import home.gio.calorieplanner.productcatalog.ProductCatalogPresenter;


public class ProductsCatalogFragment extends Fragment implements IProductCatalogView {
    @BindView(R.id.constraintSpinner)
    public Spinner spinner;
    @BindView(R.id.recycler_view_menu)
    public RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProductCatalogPresenter presenter;


    public ProductsCatalogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        presenter = new ProductCatalogPresenter(this);
        View rootView = inflater.inflate(R.layout.fragment_products_catalog, container, false);
        ButterKnife.bind(this, rootView);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MenuAdapter();
        recyclerView.setAdapter(adapter);
        String[] items = new String[]{"None", "Vegetarian", "Vegan", "Raw Vegan"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return rootView;
    }

}
