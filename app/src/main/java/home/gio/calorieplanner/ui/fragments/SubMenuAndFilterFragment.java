package home.gio.calorieplanner.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.thoughtbot.expandablecheckrecyclerview.listeners.OnCheckChildClickListener;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import home.gio.calorieplanner.R;
import home.gio.calorieplanner.models.Category;
import home.gio.calorieplanner.submenuandfilter.CategoryAdapter;
import home.gio.calorieplanner.submenuandfilter.ISubMenuAndFilterView;
import home.gio.calorieplanner.submenuandfilter.SubMenuAndFilterPresenter;


public class SubMenuAndFilterFragment extends Fragment implements ISubMenuAndFilterView {
    @BindView(R.id.constraintSpinner)
    public Spinner spinner;
    @BindView(R.id.recycler_view_menu)
    public RecyclerView recyclerView;
    @BindView(R.id.clear_button)
    public Button clearBtn;
    @BindView(R.id.choose_button)
    public Button chooseBtn;
    @BindView(R.id.search_image)
    public ImageView searchImage;
    @BindView(R.id.search_edittext)
    public TextView searchEditText;
    private RecyclerView.LayoutManager layoutManager;
    private SubMenuAndFilterPresenter presenter;
    private CategoryAdapter categoryAdapter;
    private List<String> subMenuList;

    public SubMenuAndFilterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        presenter = new SubMenuAndFilterPresenter(this);
        View rootView = inflater.inflate(R.layout.fragment_submenu_and_filter, container, false);
        ButterKnife.bind(this, rootView);
        subMenuList = new ArrayList<>();
        final List<Category> categories = presenter.getCategories();
        categoryAdapter = new CategoryAdapter(categories);
        categoryAdapter.setChildClickListener(new OnCheckChildClickListener() {
            @Override
            public void onCheckChildCLick(View v, boolean checked, CheckedExpandableGroup group, int childIndex) {
                CheckedTextView checkedTextView = (CheckedTextView) v.findViewById(R.id.menu_item_CheckedTextView);
                subMenuList.add(checkedTextView.getText().toString());
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
                ProductCatalogFragment catalog = new ProductCatalogFragment();
                Bundle args = new Bundle();
                args.putStringArrayList("catalogList", (ArrayList<String>) subMenuList);
                catalog.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_container, catalog).addToBackStack(null).commit();
            }
        });
        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductCatalogFragment catalog = new ProductCatalogFragment();
                Bundle args = new Bundle();
                args.putString("searchInList", searchEditText.getText().toString());
                catalog.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_container, catalog).addToBackStack(null).commit();
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
