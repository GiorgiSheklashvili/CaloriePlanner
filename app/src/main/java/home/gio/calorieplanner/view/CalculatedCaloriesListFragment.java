package home.gio.calorieplanner.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import home.gio.calorieplanner.R;
import home.gio.calorieplanner.model.Person;
import home.gio.calorieplanner.presenter.CalculatedCaloriesAdapter;

public class CalculatedCaloriesListFragment extends Fragment {
    private List<Person> persons;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public CalculatedCaloriesListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        persons=new ArrayList<>();
        persons.add(new Person("gio",2500,110,30,300));
        persons.add(new Person("genadi",2500,110,30,300));
        persons.add(new Person("gocha",2500,110,30,300));
        View rootView = inflater.inflate(R.layout.fragment_calculated_calories_list, container, false);
        mRecyclerView=(RecyclerView)rootView.findViewById(R.id.calculated_calories_recyclerView);
        mLayoutManager=new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter=new CalculatedCaloriesAdapter(persons);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

}
