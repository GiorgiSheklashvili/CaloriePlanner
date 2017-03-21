package home.gio.calorieplanner.presenter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import home.gio.calorieplanner.view.GroceriesListFragment;
import home.gio.calorieplanner.view.ObjectFragment;

public class ProductDetailsPagerAdapter extends FragmentPagerAdapter{
    public ProductDetailsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new ObjectFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
