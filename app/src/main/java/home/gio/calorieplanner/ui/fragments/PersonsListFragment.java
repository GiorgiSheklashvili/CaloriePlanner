package home.gio.calorieplanner.ui.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import home.gio.calorieplanner.R;
import home.gio.calorieplanner.models.Person;
import home.gio.calorieplanner.personslist.CalculatedCaloriesAdapter;
import home.gio.calorieplanner.personslist.IPersonsListModel;
import home.gio.calorieplanner.personslist.IPersonsListView;
import home.gio.calorieplanner.personslist.PersonsListPresenter;

public class PersonsListFragment extends Fragment implements IPersonsListView {
    private List<Person> persons;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    PersonsListPresenter presenter;

    public PersonsListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        presenter = new PersonsListPresenter(this);
        presenter.personsListRefill(persons, getActivity());
        View rootView = inflater.inflate(R.layout.fragment_persons_list, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.calculated_calories_recyclerView);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CalculatedCaloriesAdapter(persons, getContext());
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }
    @Override
    public void assignValuePersons(List<Person> personList) {
        persons = personList;
    }

}
