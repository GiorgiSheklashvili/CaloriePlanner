package home.gio.calorieplanner.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import home.gio.calorieplanner.App;
import home.gio.calorieplanner.R;
import home.gio.calorieplanner.main.Main;
import home.gio.calorieplanner.shoppinglist.IShoppingListView;
import home.gio.calorieplanner.shoppinglist.ShoppingAdapter;

public class ShoppingListFragment extends Fragment implements IShoppingListView {

    List<String> productList;
    ShoppingAdapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    @BindView(R.id.clear_shopping_list)
    Button remove;
    @BindView(R.id.price)
    TextView sumPrice;

    public ShoppingListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        ButterKnife.bind(this, rootView);
        if (getArguments() != null) {
            productList = getArguments().getStringArrayList("productList");
        } else {
            productList = App.listFromGson(getActivity(), "shoppinglist");
        }
        if (productList != null) {
            recyclerView = (RecyclerView) rootView.findViewById(R.id.full_list);
            adapter = new ShoppingAdapter(productList, getActivity());
            recyclerView.setAdapter(adapter);
            layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            if (sumOfPrices(productList) != null) {
                sumPrice.setText(getString(R.string.currency, sumOfPrices(productList)));
            }
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<Integer> recyclerInts = new ArrayList<Integer>();
                    for (Iterator<Integer> k = adapter.positionList.iterator(); k.hasNext(); ) {
                        Integer next = k.next();
                        recyclerInts.add(next);
                        k.remove();
                    }
                    for (Integer ints = recyclerInts.size() - 1; ints >= 0; ints--) {
                        productList.remove((int) recyclerInts.get(ints));
                        adapter.notifyItemRemoved(recyclerInts.get(ints));
                    }
                    sumPrice.setText(getString(R.string.currency, sumOfPrices(productList)));
                }
            });
        }
        return rootView;
    }

    public String sumOfPrices(List<String> details) {
        Double sum = 0.0;
        for (String detail : details) {
            sum += Double.parseDouble(Main.getPrice(detail));
        }
        return sum.toString();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (productList != null) {
            App.listToGson(getActivity(), productList, "shoppinglist");

        }
    }
}