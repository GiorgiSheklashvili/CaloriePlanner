package home.gio.calorieplanner.ui.fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import home.gio.calorieplanner.App;
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
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private List<String> listData = new ArrayList<>();
    private static boolean firstOpen = true;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MainPresenter(this);
        if (firstOpen) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                presenter.loadDataFromDatabase(getContext());
            } else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL);
            }
            firstOpen = false;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        presenter.parseGoodwillSakvebiProductebiHTML(getContext());    //parser
        sharedPreferences = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        if (!sharedPreferences.getString("personNameList", "").equals("")) {
            try {
                JSONArray jsonArray = new JSONArray(sharedPreferences.getString(
                        "personNameList", null));
                for (int i = 0; i < jsonArray.length(); i++) {
                    listData.add(jsonArray.getString(i));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (listData.size() == 0) {
            listData.add("");
        }
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mLayout = rootView.findViewById(R.id.main_fragment_layout);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_View);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PersonsAdapter(getContext(), listData);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        editor = sharedPreferences.edit();
        Set<String> personSet = new LinkedHashSet<>();
        if (((PersonsAdapter) mAdapter).personList.size() != 0) {
            for (String item : ((PersonsAdapter) mAdapter).personList) {
                if (!item.equals(""))
                    personSet.add(item);
            }
            personSet.add("");
            editor.putString("personNameList", new JSONArray(personSet).toString());
            listData.clear();
        }
        editor.apply();
    }


    @Override
    public void onStart() {
        super.onStart();
        if (firstOpen) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                presenter.loadDataFromDatabase(getContext());
            } else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL);
            }
            firstOpen = false;
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
                    firstOpen = false;
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
