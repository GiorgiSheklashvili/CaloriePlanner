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
        productList = App.listFromGson(getActivity(), "shoppinglist");
        if (productList != null) {
            recyclerView = (RecyclerView) rootView.findViewById(R.id.full_list);
            adapter = new ShoppingAdapter(productList, getActivity());
            recyclerView.setAdapter(adapter);
            layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            if (sumOfPrices(adapter.productList, adapter.numbersList) != null) {
                sumPrice.setText(getString(R.string.currency, sumOfPrices(adapter.productList, adapter.numbersList)));
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
                        adapter.productList.remove((int) recyclerInts.get(ints));
                        adapter.numbersList.remove((int) recyclerInts.get(ints));
                        adapter.notifyItemRemoved(recyclerInts.get(ints));
                    }
                    sumPrice.setText(getString(R.string.currency, sumOfPrices(adapter.productList, adapter.numbersList)));
                }
            });
        }
        return rootView;
    }

    public String sumOfPrices(List<String> details, List<String> numbers) {
        Double sum = 0.0;
        for (int i = 0; i < details.size(); i++) {
            sum += Double.parseDouble(numbers.get(i)) * Double.parseDouble(Main.getPrice(details.get(i)));
        }
        return sum.toString();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter.productList != null) {
            App.listToGson(getActivity(), adapter.productList, "shoppinglist");
            App.listToGson(getActivity(), adapter.numbersList, "numbersOfShoppingList");
        }
    }
}