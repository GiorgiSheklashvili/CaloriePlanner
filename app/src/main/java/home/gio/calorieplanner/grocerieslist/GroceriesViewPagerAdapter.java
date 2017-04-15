package home.gio.calorieplanner.grocerieslist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import home.gio.calorieplanner.ui.fragments.GroceriesListFragment;

public class GroceriesViewPagerAdapter extends FragmentPagerAdapter{
    public GroceriesViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new GroceriesListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getCount() {
        return 3;
    }
}
