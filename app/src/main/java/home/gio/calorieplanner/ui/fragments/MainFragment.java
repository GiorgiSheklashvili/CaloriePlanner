package home.gio.calorieplanner.ui.fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;


import java.util.HashSet;
import java.util.Set;

import home.gio.calorieplanner.R;
import home.gio.calorieplanner.main.PersonsAdapter;
import home.gio.calorieplanner.main.IMainView;
import home.gio.calorieplanner.main.MainPresenter;

public class MainFragment extends Fragment implements AdapterView.OnItemSelectedListener, IMainView {
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL = 0;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MainPresenter presenter;
    private View mLayout, rootView;
    private int numberOfPeople;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private Set<String> personNamesSet=new HashSet<>();

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        presenter = new MainPresenter(this);
//        presenter.parseGoodwillSakvebiProductebiHTML(getContext());
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        numberOfPeople = sharedPreferences.getInt("numberOfPeople", 1);
        if (sharedPreferences.getStringSet("personNameList", null) != null) {
            personNamesSet = sharedPreferences.getStringSet("personNameList", null);
        }
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mLayout = rootView.findViewById(R.id.main_fragment_layout);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_View);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PersonsAdapter(numberOfPeople, getContext(),personNamesSet);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        editor = sharedPreferences.edit();
        editor.putInt("numberOfPeople", mAdapter.getItemCount());
        editor.putStringSet("personNameList", new HashSet<>(presenter.asList(((PersonsAdapter) mAdapter).personArray)));
        editor.apply();
    }


    @Override
    public void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            presenter.loadDataFromDatabase(getContext());
        } else {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL);
        }
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.loadDataFromDatabase(getContext());
                    // permission was granted
                } else {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Snackbar.make(mLayout, R.string.permission_write_external_rationale,
                                Snackbar.LENGTH_INDEFINITE)
                                .setAction(R.string.ok, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL);
                                    }
                                })
                                .show();
                    } else {
                        new AlertDialog.Builder(getContext())
                                .setTitle("Permission is not granted")
                                .setMessage("Application must be able to access files on your device, so please grant access from app settings")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent();
                                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", getContext().getPackageName(), MainFragment.class.getSimpleName());
                                        intent.setData(uri);
                                        startActivity(intent);
                                    }
                                })
                                .setCancelable(false)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    }

                }
                return;
            }
        }

    }

}
